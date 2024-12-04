package com.example.technical_task.controller;

import com.example.technical_task.testdata.TestDataConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import(TestDataConfiguration.class)
class TeacherControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataConfiguration testData;

    @Test
    void getAllTeachersByCriteria_noParameters() throws Exception {
        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].id").value(
                        containsInAnyOrder(
                                testData.getTeacher1().getId().intValue(),
                                testData.getTeacher2().getId().intValue(),
                                testData.getTeacher3().getId().intValue()
                        )
                ))
                .andExpect(jsonPath("$[*].age").exists())
                .andExpect(jsonPath("$[*].teacherId").exists())
                .andExpect(jsonPath("$[*].name").exists());
    }

    @Test
    void getAllTeachersByCriteria_withParameters() throws Exception {
        mockMvc.perform(get("/api/teachers")
                        .param("courseId", testData.getCourse1().getId().toString())
                        .param("studyGroupId", testData.getStudyGroup1().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(testData.getTeacher1().getId().intValue()));
    }

    @Test
    void createTeacher() throws Exception {
        mockMvc.perform(post("/api/teachers")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "New Student",
                                  "age": 20,
                                  "teacherId": "123456"
                                }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("New Student"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.teacherId").value("123456"));
    }

    @Test
    void updateTeacher() throws Exception {
        mockMvc.perform(put("/api/teachers")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": %d,
                                  "name": "New Teacher",
                                  "age": 20,
                                  "teacherId": "123456"
                                }""".formatted(testData.getStudyGroup1().getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("New Teacher"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.teacherId").value("123456"));
    }

    @Test
    void updateTeacher_nonExistingId() throws Exception {
        mockMvc.perform(put("/api/teachers")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": %d,
                                  "name": "New Teacher",
                                  "age": 20,
                                  "teacherId": "123456"
                                }""".formatted(Integer.MAX_VALUE)))
                .andExpect(status().isBadRequest());
    }
}
