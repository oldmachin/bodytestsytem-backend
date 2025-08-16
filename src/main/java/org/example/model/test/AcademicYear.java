package org.example.model.test;

public enum AcdemicYear {
    FRESHMAN("第一学年"),
    SOPHOMORE("第二学年"),
    JUNIOR("第三学年"),
    SENIOR("第四学年");

    private final String description;

    AcdemicYear(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
