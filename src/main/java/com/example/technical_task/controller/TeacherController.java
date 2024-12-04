package com.example.technical_task.controller;

import com.example.technical_task.service.TeacherService;
import com.example.technical_task.service.dto.TeacherDto;
import com.example.technical_task.service.filter.TeacherCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    public static final String STUDY_GROUP_ID_AND_COURSE_ID_MUST_BE_EITHER_PRESENT_OR_NULL = "Both studyGroupId and courseId must be either present or null.";

    private final TeacherService teacherService;

    @GetMapping("/count")
    public ResponseEntity<Long> countTeachers() {
        Long count = teacherService.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAllTeachersByCriteria(TeacherCriteria teacherCriteria) {
        if ((teacherCriteria.getStudyGroupId() == null) != (teacherCriteria.getCourseId() == null)) {
            throw new IllegalStateException(STUDY_GROUP_ID_AND_COURSE_ID_MUST_BE_EITHER_PRESENT_OR_NULL);
        }
        List<TeacherDto> teachers = teacherService.getAllByCriteria(teacherCriteria);
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getTeacher(Long id) {
        TeacherDto teacher = teacherService.getById(id);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(TeacherDto teacherDto) {
        if (teacherDto.getId() != null) {
            throw new IllegalArgumentException("Id should be null");
        }
        TeacherDto teacher = teacherService.save(teacherDto);
        return ResponseEntity.ok(teacher);
    }

    @PutMapping
    public ResponseEntity<TeacherDto> updateTeacher(TeacherDto teacherDto) {
        if (teacherDto.getId() == null) {
            throw new IllegalArgumentException("Id is required");
        }
        TeacherDto teacher = teacherService.save(teacherDto);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
