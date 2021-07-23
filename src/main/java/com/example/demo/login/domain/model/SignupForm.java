package com.example.demo.login.domain.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupForm {
    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[0-9]+$", groups = ValidGroup2.class)
    @Length(min = 6, max = 6, groups = ValidGroup3.class)
    private String userId;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[a-zA-Z0-9]+$", groups = ValidGroup2.class)
    @Length(min = 8, max = 32, groups = ValidGroup3.class)
    private String password;

    @NotBlank(groups = ValidGroup1.class)
    private String userName;

    @NotBlank(groups = ValidGroup1.class)
    private String department;
}
