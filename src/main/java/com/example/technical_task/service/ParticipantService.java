package com.example.technical_task.service;

import com.example.technical_task.service.dto.ParticipantsDto;
import com.example.technical_task.service.filter.StudentCriteria;
import com.example.technical_task.service.filter.TeacherCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final TeacherService teacherService;

    private final StudentService studentService;

    @Transactional(readOnly = true)
    public ParticipantsDto getByStudyGroupAndCourse(Long studyGroupId, Long courseId) {
        return ParticipantsDto.builder()
                .teachers(teacherService.getAllByCriteria(
                        TeacherCriteria.builder()
                                .studyGroupId(studyGroupId)
                                .courseId(courseId)
                                .build()
                ))
                .students(studentService.getAllByCriteria(
                        StudentCriteria.builder()
                                .studyGroupId(studyGroupId)
                                .courseId(courseId)
                                .build()
                ))
                .build();
    }
}
