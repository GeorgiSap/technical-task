package com.example.technical_task.testdata.builder;

import com.example.technical_task.entity.Student;

import java.util.UUID;

import static com.example.technical_task.testdata.TestDataUtil.randomAge;
import static com.example.technical_task.testdata.TestDataUtil.randomName;


public class TestStudentBuilder {
    public static Student defaultStudent() {
        return Student.builder()
                .age(randomAge())
                .studentId(UUID.randomUUID().toString())
                .name(randomName())
                .build();
    }


}
