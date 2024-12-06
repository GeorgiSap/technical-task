package com.example.technical_task.testdata.builder;

import com.example.technical_task.entity.Course;
import com.example.technical_task.entity.CourseType;

import static com.example.technical_task.testdata.TestDataUtil.randomUniqueCourseName;


public class TestCourseBuilder {
    public static Course defaultCourse() {
        return Course
                .builder()
                .name(randomUniqueCourseName())
                .type(CourseType.PRIMARY)
                .build();
    }

}
