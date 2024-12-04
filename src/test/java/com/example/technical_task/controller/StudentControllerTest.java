package com.example.technical_task.controller;

import com.example.technical_task.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.technical_task.controller.StudentController.INVALID_STUDENT_CRITERIA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void getAllStudentsByCriteria_noParameters() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStudentsByCriteria_studentsParticipateInSpecificStudyGroup() throws Exception {
        mockMvc.perform(get("/api/students?studyGroupId=1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStudentsByCriteria_studentsParticipateInSpecificCourse() throws Exception {
        mockMvc.perform(get("/api/students?courseId=1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStudentsByCriteria_studentsAreOlderThanSpecificAgeAndParticipateInSpecificCourse() throws Exception {
        mockMvc.perform(get("/api/students?ageGreaterThan=20&courseId=1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllStudentsByCriteria_invalidStudentCriteria() throws Exception {
        mockMvc.perform(get("/api/students?ageGreaterThan=20"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(INVALID_STUDENT_CRITERIA));
    }

    @Test
    void getAllStudentsByCriteria_allParametersProvided() throws Exception {
        mockMvc.perform(get("/api/students?age=20&courseId=1&studyGroupId=1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(INVALID_STUDENT_CRITERIA));
    }
}
