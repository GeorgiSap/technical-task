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
class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataConfiguration testData;


    @Test
    void getAllStudentsByCriteria() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].id").value(
                        containsInAnyOrder(
                                testData.getStudent1().getId().intValue(),
                                testData.getStudent2().getId().intValue(),
                                testData.getStudent3().getId().intValue()
                        )
                ))
                .andExpect(jsonPath("$[*].age").exists())
                .andExpect(jsonPath("$[*].studentId").exists())
                .andExpect(jsonPath("$[*].studyGroupId").exists())
                .andExpect(jsonPath("$[*].name").exists());
    }

    @Test
    void getAllStudentsByCriteria_participateInSpecificStudyGroup() throws Exception {
        mockMvc.perform(get("/api/students")
                        .param("studyGroupId", testData.getStudyGroup1().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id").value(
                        containsInAnyOrder(
                                testData.getStudent1().getId().intValue(),
                                testData.getStudent3().getId().intValue()
                        )
                ));
    }

    @Test
    void getAllStudentsByCriteria_participateInSpecificCourse() throws Exception {
        mockMvc.perform(get("/api/students")
                        .param("courseId", testData.getCourse2().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].id").value(
                        containsInAnyOrder(
                                testData.getStudent1().getId().intValue(),
                                testData.getStudent3().getId().intValue()
                        )
                ));
    }

    @Test
    void getAllStudentsByCriteria_olderThanSpecificAgeAndParticipateInSpecificCourse() throws Exception {
        mockMvc.perform(get("/api/students")
                        .param("courseId", testData.getCourse2().getId().toString())
                        .param("ageGreaterThan", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(
                        containsInAnyOrder(testData.getStudent3().getId().intValue())
                ));
    }

    @Test
    void createStudent() throws Exception {
        mockMvc.perform(post("/api/students")
                        .contentType("application/json")
                        .content("""
                                {
                                  "name": "New Student",
                                  "age": 20,
                                  "studentId": "123456",
                                  "studyGroupId": %d
                                }
                                """.formatted(testData.getStudyGroup1().getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("New Student"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.studentId").value("123456"))
                .andExpect(jsonPath("$.studyGroupId").value(testData.getStudyGroup1().getId().intValue()));
    }

    @Test
    void updateStudent() throws Exception {
        mockMvc.perform(put("/api/students")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": %d,
                                  "name": "Updated Student",
                                  "age": 20,
                                  "studentId": "123456",
                                  "studyGroupId": %d
                                }
                                """.formatted(testData.getStudent1().getId(), testData.getStudyGroup1().getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testData.getStudent1().getId().intValue()))
                .andExpect(jsonPath("$.name").value("Updated Student"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.studentId").value("123456"))
                .andExpect(jsonPath("$.studyGroupId").value(testData.getStudyGroup1().getId().intValue()));
    }

    @Test
    void updateStudent_NonExistingId() throws Exception {
        mockMvc.perform(put("/api/students")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": %d,
                                  "name": "Updated Student",
                                  "age": 20,
                                  "studentId": "123456",
                                  "studyGroupId": %d
                                }
                                """.formatted(Integer.MAX_VALUE, testData.getStudyGroup1().getId())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudent_NonExistingStudyGroup() throws Exception {
        mockMvc.perform(put("/api/students")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": " + testData.getStudent1().getId() + ",\n" +
                                "  \"name\": \"Updated Student\",\n" +
                                "  \"age\": 20,\n" +
                                "  \"studentId\": \"123456\",\n" +
                                "  \"studyGroupId\": " + Integer.MAX_VALUE + "\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }
}
