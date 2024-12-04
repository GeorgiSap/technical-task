package com.example.technical_task.controller;

import com.example.technical_task.testdata.builder.TestCourseBuilder;
import com.example.technical_task.entity.CourseType;
import com.example.technical_task.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CourseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void countCoursesByType_primaryCourses() throws Exception {
        courseRepository.save(TestCourseBuilder.builder().type(CourseType.PRIMARY).build());
        courseRepository.save(TestCourseBuilder.builder().type(CourseType.PRIMARY).build());
        courseRepository.save(TestCourseBuilder.builder().type(CourseType.SECONDARY).build());

        mockMvc.perform(get("/api/courses/count")
                        .param("type", "PRIMARY"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void countCoursesByType_noCoursesOfType() throws Exception {
        courseRepository.save(TestCourseBuilder.builder().type(CourseType.PRIMARY).build());
        courseRepository.save(TestCourseBuilder.builder().type(CourseType.PRIMARY).build());

        mockMvc.perform(get("/api/courses/count")
                        .param("type", "SECONDARY"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

}
