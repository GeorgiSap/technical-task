package com.example.technical_task.testdata.builder;

import com.example.technical_task.entity.Teacher;

import java.util.UUID;

import static com.example.technical_task.testdata.TestDataUtil.*;


public class TestTeacherBuilder {
    public static Teacher.TeacherBuilder builder() {
        return Teacher.builder()
                .age(randomAge())
                .teacherId(UUID.randomUUID().toString())
                .name(randomName());
    }

}
