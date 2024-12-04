package com.example.technical_task.service;

import com.example.technical_task.entity.CourseType;
import com.example.technical_task.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public Long countByType(CourseType type) {
        return courseRepository.countByType(type);
    }
}
