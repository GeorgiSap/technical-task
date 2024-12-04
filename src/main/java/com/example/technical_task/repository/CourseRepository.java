package com.example.technical_task.repository;

import com.example.technical_task.entity.Course;
import com.example.technical_task.entity.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Long countByType(CourseType type);

}
