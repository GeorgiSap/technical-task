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
class ParticipantControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataConfiguration testData;

    @Test
    void getAllParticipantsByStudyGroupAndCourse() throws Exception {
        mockMvc.perform(get("/api/participants")
                        .param("courseId", testData.getCourse1().getId().toString())
                        .param("studyGroupId", testData.getStudyGroup1().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(1)))
                .andExpect(jsonPath("$.students[0].id").value(testData.getStudent1().getId()))
                .andExpect(jsonPath("$.students[0].name").value(testData.getStudent1().getName()))
                .andExpect(jsonPath("$.students[0].age").value(testData.getStudent1().getAge()))
                .andExpect(jsonPath("$.students[0].studentId").value(testData.getStudent1().getStudentId()))
                .andExpect(jsonPath("$.teachers", hasSize(1)))
                .andExpect(jsonPath("$.teachers[0].id").value(testData.getTeacher1().getId()))
                .andExpect(jsonPath("$.teachers[0].name").value(testData.getTeacher1().getName()))
                .andExpect(jsonPath("$.teachers[0].age").value(testData.getTeacher1().getAge()))
                .andExpect(jsonPath("$.teachers[0].teacherId").value(testData.getTeacher1().getTeacherId()));
    }

    @Test
    void getAllParticipantsByStudyGroupAndCourse_multipleStudents() throws Exception {
        mockMvc.perform(get("/api/participants")
                        .param("courseId", testData.getCourse2().getId().toString())
                        .param("studyGroupId", testData.getStudyGroup1().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(2)))
                .andExpect(jsonPath("$.students[*].id").value(containsInAnyOrder(
                        testData.getStudent1().getId().intValue(),
                        testData.getStudent3().getId().intValue()
                )))
                .andExpect(jsonPath("$.teachers", hasSize(1)))
                .andExpect(jsonPath("$.teachers[0].id").value(testData.getTeacher3().getId()));
    }

    @Test
    void getAllParticipantsByStudyGroupAndCourse_multipleTeachers() throws Exception {
        mockMvc.perform(get("/api/participants")
                        .param("courseId", testData.getCourse1().getId().toString())
                        .param("studyGroupId", testData.getStudyGroup2().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(1)))
                .andExpect(jsonPath("$.students[0].id").value(testData.getStudent2().getId().intValue()))
                .andExpect(jsonPath("$.teachers", hasSize(2)))
                .andExpect(jsonPath("$.teachers[*].id").value(containsInAnyOrder(
                        testData.getTeacher1().getId().intValue(),
                        testData.getTeacher2().getId().intValue()
                )));
    }

    @Test
    public void getAllParticipantsByStudyGroupAndCourse_noParticipants() throws Exception {
        mockMvc.perform(get("/api/participants")
                        .param("courseId", testData.getCourse3().getId().toString())
                        .param("studyGroupId", testData.getStudyGroup3().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(0)))
                .andExpect(jsonPath("$.teachers", hasSize(0)));
    }

    @Test
    public void getAllParticipantsByStudyGroupAndCourse_nonExistingIds() throws Exception {
        mockMvc.perform(get("/api/participants")
                        .param("courseId", String.valueOf(Integer.MAX_VALUE))
                        .param("studyGroupId", String.valueOf(Integer.MAX_VALUE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(0)))
                .andExpect(jsonPath("$.teachers", hasSize(0)));
    }

    @Test
    public void getAllParticipantsByStudyGroupAndCourse_nonExistingCourseId() throws Exception {
        mockMvc.perform(get("/api/participants")
                        .param("courseId", String.valueOf(Integer.MAX_VALUE))
                        .param("studyGroupId", testData.getStudyGroup1().getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(0)))
                .andExpect(jsonPath("$.teachers", hasSize(0)));
    }

    @Test
    public void getAllParticipantsByStudyGroupAndCourse_nonExistingStudyGroupId() throws Exception {
        mockMvc.perform(get("/api/participants")
                        .param("courseId", testData.getCourse1().getId().toString())
                        .param("studyGroupId", String.valueOf(Integer.MAX_VALUE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(0)))
                .andExpect(jsonPath("$.teachers", hasSize(0)));
    }
}
