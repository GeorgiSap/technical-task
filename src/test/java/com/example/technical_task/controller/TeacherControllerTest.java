package com.example.technical_task.controller;

import com.example.technical_task.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.technical_task.controller.TeacherController.STUDY_GROUP_ID_AND_COURSE_ID_MUST_BE_EITHER_PRESENT_OR_NULL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeacherService teacherService;

    @Test
    void getAllTeachersByCriteria_noParameters() throws Exception {
        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTeachersByCriteria_allParametersPresent() throws Exception {
        mockMvc.perform(get("/api/teachers")
                        .param("studyGroupId", "1")
                        .param("courseId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTeachersByCriteria_onlyCourseIdPresent() throws Exception {
        mockMvc.perform(get("/api/teachers")
                        .param("courseId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(STUDY_GROUP_ID_AND_COURSE_ID_MUST_BE_EITHER_PRESENT_OR_NULL));
    }

    @Test
    void getAllTeachersByCriteria_onlyStudyGroupIdPresent() throws Exception {
        mockMvc.perform(get("/api/teachers")
                        .param("studyGroupId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(STUDY_GROUP_ID_AND_COURSE_ID_MUST_BE_EITHER_PRESENT_OR_NULL));
    }

}
