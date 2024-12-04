package com.example.technical_task.controller;

import com.example.technical_task.entity.CourseType;
import com.example.technical_task.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/count")
    public ResponseEntity<Long> countCoursesByType(@RequestParam CourseType type) {
        Long count = courseService.countByType(type);
        return ResponseEntity.ok(count);
    }

}
