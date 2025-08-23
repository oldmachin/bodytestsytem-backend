package org.example.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import org.example.model.score.ScoreLevel;
import org.example.model.score.ScoreMapping;
import org.example.model.score.ScoreStandard;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MalesScoreService {
    private Map<String, ScoreStandard> standards = new LinkedHashMap<>();

    @PostConstruct
    public void loadData() {
        standards.put("lungCapacity", new ScoreStandard("肺活量"));
        standards.put("run50m", new ScoreStandard("50米跑"));
        standards.put("sitAndReach", new ScoreStandard("坐位体前屈"));
        standards.put("standingJump", new ScoreStandard("立定跳远"));
        standards.put("pullUps", new ScoreStandard("引体向上"));
        standards.put("run1000m", new ScoreStandard("1000米跑"));

        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream("/male_score_standards.csv")))) {
            reader.skip(2); // 跳过前两行表头
            String[] nextLine;
            ScoreLevel currentScoreLevel = ScoreLevel.EXCELLENT;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 14) break;

                String levelString = nextLine[0].trim();
                if (!levelString.isBlank()) {
                    currentScoreLevel = ScoreLevel.fromChineseName(levelString);
                }

                int currentScore = Integer.parseInt(nextLine[1]);

                ScoreMapping lungCapacityMapping = new ScoreMapping(currentScoreLevel, currentScore, Double.parseDouble(nextLine[2]), Double.parseDouble(nextLine[3]));
                standards.get("lungCapacity").addScoreMapping(lungCapacityMapping);

                ScoreMapping run50mMapping = new ScoreMapping(currentScoreLevel, currentScore, Double.parseDouble(nextLine[4]), Double.parseDouble(nextLine[5]));
                standards.get("run50m").addScoreMapping(run50mMapping);

                ScoreMapping sitAndReachMapping = new ScoreMapping(currentScoreLevel, currentScore, Double.parseDouble(nextLine[6]), Double.parseDouble(nextLine[7]));
                standards.get("sitAndReach").addScoreMapping(sitAndReachMapping);

                ScoreMapping standingJumpMapping = new ScoreMapping(currentScoreLevel, currentScore, Double.parseDouble(nextLine[8]), Double.parseDouble(nextLine[9]));
                standards.get("standingJump").addScoreMapping(standingJumpMapping);

                ScoreMapping pullUpsMapping;
                if (nextLine[10].isBlank()) {
                    pullUpsMapping = new ScoreMapping(currentScoreLevel, currentScore, standards.get("pullUps").getScoreMappingList().getLast().getGrade_1_2(), standards.get("pullUps").getScoreMappingList().getLast().getGrade_3_4());
                } else {
                    pullUpsMapping = new ScoreMapping(currentScoreLevel, currentScore, Double.parseDouble(nextLine[10]), Double.parseDouble(nextLine[11]));
                }
                standards.get("pullUps").addScoreMapping(pullUpsMapping);

                ScoreMapping run1000mMapping = new ScoreMapping(currentScoreLevel, currentScore, Double.parseDouble(nextLine[12]), Double.parseDouble(nextLine[13]));
                standards.get("run1000m").addScoreMapping(run1000mMapping);

                System.out.println(standards.toString());
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
