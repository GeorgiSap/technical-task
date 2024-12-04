package com.example.technical_task.testdata.builder;

import com.example.technical_task.entity.Student;

import java.util.UUID;

import static com.example.technical_task.testdata.TestDataUtil.*;


public class TestStudentBuilder {
    public static Student.StudentBuilder builder() {
        return Student.builder()
                .age(randomAge())
                .studentId(UUID.randomUUID().toString())
                .name(randomName());
    }


}
