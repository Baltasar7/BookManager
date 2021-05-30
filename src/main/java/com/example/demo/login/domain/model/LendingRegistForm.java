package com.example.demo.login.domain.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class LendingRegistForm {
    private String lendingId;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[0-9]+$", groups = ValidGroup2.class)
    @Min(value = 1 , groups = ValidGroup3.class)
    private String stockId;

    @NotBlank(groups = ValidGroup1.class)
    @Pattern(regexp = "^[0-9]+$", groups = ValidGroup2.class)
    @Length(min = 6, max = 6, groups = ValidGroup3.class)
    private String userId;
}
