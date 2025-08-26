package org.example.model.DTO;

import org.example.model.user.Grade;

public record StudentRegisterRequest(
    String studentId,
    String idCardNumber,
    String name,
    String gender,
    Long ethnicCode,
    Grade grade,
    String major
) {

}
