package org.example.model.score;

public enum ScoreLevel {
    EXCELLENT("优秀"),
    GOOD("良好"),
    PASS("及格"),
    FAIL("不及格");

    private final String chineseName;

    ScoreLevel(String chineseName) {
        this.chineseName = chineseName;
    }

    public static ScoreLevel fromChineseName(String name) {
        for (ScoreLevel level : ScoreLevel.values()) {
            if (level.chineseName.equals(name)) {
                return level;
            }
        }
        return null;
    }
}
