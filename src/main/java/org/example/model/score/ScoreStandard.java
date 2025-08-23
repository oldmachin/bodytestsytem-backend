package org.example.model.score;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreStandard {
    private String name;

    private List<ScoreMapping> scoreMappingList;

    public ScoreStandard (String name) {
        this.name = name;
        this.scoreMappingList = new ArrayList<>();
    }

    public void addScoreMapping(ScoreMapping scoreMapping) {
        this.scoreMappingList.add(scoreMapping);
    }

    public String getName() {
        return name;
    }

    public List<ScoreMapping> getScoreMappingList() {
        return scoreMappingList;
    }


    @Override
    public String toString() {
        String mappings = scoreMappingList.stream()
                .map(ScoreMapping::toString)
                .collect(Collectors.joining("\n  ", "\n  ", ""));

        return "ScoreStandard{" +
                "name='" + name + '\'' +
                ", scoreMappingList=[" + mappings + "\n" +
                "]}";
    }
}
