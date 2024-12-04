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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                        .param("age", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].id").value(
                        containsInAnyOrder(testData.getStudent3().getId().intValue())
                ));
    }
}
