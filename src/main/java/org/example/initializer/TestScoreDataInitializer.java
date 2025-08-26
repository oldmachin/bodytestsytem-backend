package org.example.initializer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.ScoreMappingCsvDto;
import org.example.model.score.ScoreLevel;
import org.example.model.score.ScoreMapping;
import org.example.model.score.ScoreStandard;
import org.example.model.score.TestItem;
import org.example.model.user.Grade;
import org.example.repository.ScoreMappingRepository;
import org.example.repository.ScoreStandardRepository;
import org.example.repository.TestItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
@Order(2)
public class TestScoreDataInitializer implements CommandLineRunner {
    @Autowired
    private ScoreMappingRepository scoreMappingRepository;

    @Autowired
    private ScoreStandardRepository scoreStandardRepository;

    @Autowired
    private TestItemRepository testItemRepository;

    public void loadTestItem() {
        TestItem lungCapacity = new TestItem("lungCapacity", "ml");
        TestItem run50m = new TestItem("run50m", "m:s");
        TestItem sitAndReach = new TestItem("sitAndReach", "cm");
        TestItem standingJump = new TestItem("standingJump", "cm");
        TestItem pullUps = new TestItem("pullUps", "times");
        TestItem sitUps = new TestItem("sitUps", "times");
        TestItem run1000m = new TestItem("run1000m", "m:s");
        TestItem run800m = new TestItem("run800m", "m:s");
        TestItem height = new TestItem("height", "m");
        TestItem weight = new TestItem("weight", "kg");
        TestItem bmi = new TestItem("BMI", "kg/m^2");

        testItemRepository.save(lungCapacity);
        testItemRepository.save(run50m);
        testItemRepository.save(sitAndReach);
        testItemRepository.save(standingJump);
        testItemRepository.save(pullUps);
        testItemRepository.save(sitUps);
        testItemRepository.save(run1000m);
        testItemRepository.save(run800m);
        testItemRepository.save(height);
        testItemRepository.save(weight);
        testItemRepository.save(bmi);
    }

    public void loadAllScoreStandard() {
        List<TestItem> allTestItems = testItemRepository.findAll();
        if (allTestItems.isEmpty()) {
            throw new EntityNotFoundException("TestItem entities not found. Please initialize TestItem first.");
        }

        List<Grade> grades = Arrays.asList(Grade.FRESHMAN, Grade.SOPHOMORE, Grade.JUNIOR, Grade.SENIOR);
        List<String> genders = Arrays.asList("male", "female");

        for (String gender : genders) {
            for (Grade grade : grades) {
                for (TestItem testItem : allTestItems) {
                    if (("run1000m".equals(testItem.getName()) || "pullUps".equals(testItem.getName())) && "female".equals(gender)) {
                        continue;
                    }
                    if (("run800m".equals(testItem.getName())  || "sitUps".equals(testItem.getName())) && "male".equals(gender)) {
                        continue;
                    }
                    ScoreStandard standard = new ScoreStandard(testItem, grade, gender);
                    scoreStandardRepository.save(standard);
                }
            }
        }
    }

    public void initializeScoreMappingFromCsv(List<ScoreMappingCsvDto> dtoList) {
        for (ScoreMappingCsvDto dto : dtoList) {
            TestItem testItem = testItemRepository.findByName(dto.testItemName())
                    .orElseThrow(() -> new EntityNotFoundException("TestItem not found: " + dto.testItemName()));

            ScoreStandard standard = scoreStandardRepository.findByTestItemAndGenderAndGrade(
                    testItem, dto.gender(), dto.grade()
            ).orElseGet(() -> {
                ScoreStandard newStandard = new ScoreStandard(testItem, dto.grade(), dto.gender());
                return scoreStandardRepository.save(newStandard);
            });

            ScoreMapping newMapping = new ScoreMapping(
                    dto.scoreLevel(),
                    dto.score(),
                    dto.grade(),
                    dto.threshold(),
                    standard
            );
            scoreMappingRepository.save(newMapping);
        }
    }

    public void loadMaleScoreStandard() {
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream("/male_score_standards.csv")))) {
            reader.skip(2);
            String[] nextLine;
            ScoreLevel currentScoreLevel = null;
            List<ScoreMappingCsvDto> dtoList = new ArrayList<>();
            List<String> testItemNameList = Arrays.asList("lungCapacity", "run50m", "sitAndReach", "standingJump", "pullUps", "run1000m");
            List<Grade> gradeList = Arrays.asList(Grade.FRESHMAN, Grade.SOPHOMORE, Grade.JUNIOR, Grade.SENIOR);

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 14) continue;

                String levelString = nextLine[0].trim();
                if (!levelString.isBlank()) {
                    currentScoreLevel = ScoreLevel.fromChineseName(levelString);
                }

                int currentScore = Integer.parseInt(nextLine[1]);
                int dataColumn = 2;

                for (String testItemName : testItemNameList) {
                    if (nextLine[dataColumn].isBlank()) {
                        dataColumn += 2;
                        continue;
                    }
                    for (Grade grade : gradeList) {
                        if (grade == Grade.FRESHMAN) {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "male", grade, Double.parseDouble(nextLine[dataColumn])));
                        } else if (grade == Grade.SOPHOMORE) {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "male", grade, Double.parseDouble(nextLine[dataColumn++])));
                        } else if (grade == Grade.JUNIOR) {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "male", grade, Double.parseDouble(nextLine[dataColumn])));
                        } else {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "male", grade, Double.parseDouble(nextLine[dataColumn++])));
                        }
                    }
                }

            }
            initializeScoreMappingFromCsv(dtoList);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void loadFemaleScoreStandard() {
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream("/female_score_standards.csv")))) {
            reader.skip(2);
            String[] nextLine;
            ScoreLevel currentScoreLevel = null;
            List<ScoreMappingCsvDto> dtoList = new ArrayList<>();
            List<String> testItemNameList = Arrays.asList("lungCapacity", "run50m", "sitAndReach", "standingJump", "pullUps", "run800m");
            List<Grade> gradeList = Arrays.asList(Grade.FRESHMAN, Grade.SOPHOMORE, Grade.JUNIOR, Grade.SENIOR);

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 14) continue;

                String levelString = nextLine[0].trim();
                if (!levelString.isBlank()) {
                    currentScoreLevel = ScoreLevel.fromChineseName(levelString);
                }

                int currentScore = Integer.parseInt(nextLine[1]);
                int dataColumn = 2;

                for (String testItemName : testItemNameList) {
                    if (nextLine[dataColumn].isBlank()) {
                        dataColumn += 2;
                        continue;
                    }
                    for (Grade grade : gradeList) {
                        if (grade == Grade.FRESHMAN) {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "female", grade, Double.parseDouble(nextLine[dataColumn])));
                        } else if (grade == Grade.SOPHOMORE) {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "female", grade, Double.parseDouble(nextLine[dataColumn++])));
                        } else if (grade == Grade.JUNIOR) {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "female", grade, Double.parseDouble(nextLine[dataColumn])));
                        } else {
                            dtoList.add(new ScoreMappingCsvDto(currentScoreLevel, currentScore, testItemName, "female", grade, Double.parseDouble(nextLine[dataColumn++])));
                        }
                    }
                }

            }
            initializeScoreMappingFromCsv(dtoList);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    public void loadTestItemWeight() {
        try (CSVReader reader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream("/score_weight.csv")))) {
            String[] nextLine;
            reader.skip(1);
            Map<String, String> maps = new HashMap<>();
            maps.put("体重指数（BMI）", "BMI");
            maps.put("肺活量", "lungCapacity");
            maps.put("坐位体前屈", "sitAndReach");
            maps.put("立定跳远", "standingJump");
            maps.put("引体向上", "pullUps");
            maps.put("仰卧起坐", "sitUps");
            maps.put("1000米跑", "run1000m");
            maps.put("800米跑", "run800m");

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 2)    continue;
                String testItemName = nextLine[0];
                if (maps.containsKey(nextLine[0])) {
                    TestItem testItem = testItemRepository.findByName(nextLine[0])
                            .orElseThrow(() -> new EntityNotFoundException("TestItem not found: " + testItemName));
                    testItem.setScoreWeight(Double.parseDouble(nextLine[1]));
                } else if (testItemName == "引体向上（男）/1分钟仰卧起坐（女）") {
                    TestItem testItem1 = testItemRepository.findByName("pullUps")
                            .orElseThrow(() -> new EntityNotFoundException("TestItem not found: " + testItemName));
                    TestItem testItem2 = testItemRepository.findByName("sitUps")
                            .orElseThrow(() -> new EntityNotFoundException("TestItem not found: " + testItemName));
                    testItem1.setScoreWeight(Double.parseDouble(nextLine[1]));
                    testItem2.setScoreWeight(Double.parseDouble(nextLine[1]));
                } else if(testItemName == "1000米跑（男）/800米跑（女）") {
                    TestItem testItem1 = testItemRepository.findByName("run1000m")
                            .orElseThrow(() -> new EntityNotFoundException("TestItem not found: " + testItemName));
                    TestItem testItem2 = testItemRepository.findByName("run800m")
                            .orElseThrow(() -> new EntityNotFoundException("TestItem not found: " + testItemName));
                    testItem1.setScoreWeight(Double.parseDouble(nextLine[1]));
                    testItem2.setScoreWeight(Double.parseDouble(nextLine[1]));
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (scoreMappingRepository.count() == 0 && scoreStandardRepository.count() == 0 && testItemRepository.count() == 0) {
            System.out.println("No data found. Starting initialization.");
            loadTestItem();
            loadAllScoreStandard();
            loadMaleScoreStandard();
            loadFemaleScoreStandard();
        } else {
            System.out.println("Data already exists. Skipping initialization.");
        }
    }
}
