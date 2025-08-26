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

    /**
     * 获得指定中文对应的等级
     * @param name level对映的中文, 例如“优秀”
     * @return 返回得分对应的等级
     */
    public static ScoreLevel fromChineseName(String name) {
        for (ScoreLevel level : ScoreLevel.values()) {
            if (level.chineseName.equals(name)) {
                return level;
            }
        }
        return null;
    }

    /**
     * 获得指定得分对应的等级
     * @param score 指定的得分
     * @return 返回得分对应的等级
     */
    public static ScoreLevel judgeLevel(Double score) {
        if (score >= Double.valueOf(90) && score <= Double.valueOf(100)) {
            return EXCELLENT;
        }
        if (score >= Double.valueOf(80) && score < Double.valueOf(90)) {
            return GOOD;
        }
        if (score >= Double.valueOf(60) && score < Double.valueOf(80)) {
            return PASS;
        }
        if (score >= Double.valueOf(0) && score < Double.valueOf(60)) {
            return FAIL;
        }
        return null;
    }
}
