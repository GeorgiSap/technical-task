package com.example.technical_task.service.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCriteria {

    private Long studyGroupId;

    private Long courseId;

    private Integer age;
}
