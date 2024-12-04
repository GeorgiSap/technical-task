package com.example.technical_task.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto {
    private Long id;

    @NotNull
    private String name;

    @Size(min = 1, max = 200)
    private Integer age;

    @NotNull
    private String studentId;

    @NotNull
    private Long studyGroupId;
}
