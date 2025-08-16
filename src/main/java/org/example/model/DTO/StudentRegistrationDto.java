package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegistrationDto {
    @NotBlank(message = "学号不能为空")
    @Size(min = 4, max = 20, message = "学号长度必须在4到20个字符之间")
    private String studentId;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码长度至少为6个字符")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$", message = "密码必须包含数字和字母")
    private String password;

    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 50, message = "姓名长度必须在2到50个字符之间")
    private String name;

    @NotBlank(message = "身份证号不能为空")
    @Size(min = 18, max = 18, message = "身份证号必须是18位")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$",
            message = "身份证号格式不正确")
    private String idCardNumber;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^(male | female)$", message = "性别只能是'男'或'女'")
    private String gender;

    @NotBlank(message = "年级不能为空")
    private String grade;

    @NotBlank(message = "专业不能为空")
    private String major;
}
