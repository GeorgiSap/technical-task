package com.example.technical_task.controller;

import com.example.technical_task.service.StudentService;
import com.example.technical_task.service.dto.StudentDto;
import com.example.technical_task.service.filter.StudentCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    public static final String INVALID_STUDENT_CRITERIA = "Invalid student criteria";

    private final StudentService studentService;

    @GetMapping("/count")
    public ResponseEntity<Long> countStudents() {
        Long count = studentService.count();
        return ResponseEntity.ok(count);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudentsByCriteria(StudentCriteria studentCriteria) {
        if (!(noParametersProvided(studentCriteria) ||
                studentsParticipateInSpecificStudyGroup(studentCriteria) ||
                studentsParticipateInSpecificCourse(studentCriteria) ||
                studentsAreOlderThanSpecificAgeAndParticipateInSpecificCourse(studentCriteria))) {
            throw new IllegalStateException(INVALID_STUDENT_CRITERIA);
        }

        List<StudentDto> students = studentService.getAllByCriteria(studentCriteria);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        StudentDto student = studentService.getById(id);
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public ResponseEntity<StudentDto> saveStudent(@RequestBody StudentDto studentDto) {
        if (studentDto.getId() != null) {
            throw new IllegalArgumentException("Id should be null");
        }
        StudentDto student = studentService.save(studentDto);
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto) {
        if (studentDto.getId() == null) {
            throw new IllegalArgumentException("Id is required");
        }
        StudentDto student = studentService.save(studentDto);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private boolean noParametersProvided(StudentCriteria criteria) {
        return criteria.getStudyGroupId() == null &&
                criteria.getCourseId() == null &&
                criteria.getAge() == null;
    }

    private boolean studentsParticipateInSpecificStudyGroup(StudentCriteria criteria) {
        return criteria.getStudyGroupId() != null &&
                criteria.getCourseId() == null &&
                criteria.getAge() == null;
    }

    private boolean studentsParticipateInSpecificCourse(StudentCriteria criteria) {
        return criteria.getCourseId() != null &&
                criteria.getStudyGroupId() == null &&
                criteria.getAge() == null;
    }

    private boolean studentsAreOlderThanSpecificAgeAndParticipateInSpecificCourse(StudentCriteria criteria) {
        return criteria.getCourseId() != null &&
                criteria.getStudyGroupId() == null &&
                criteria.getAge() != null;
    }

}
