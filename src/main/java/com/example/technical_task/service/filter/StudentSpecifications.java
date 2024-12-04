package com.example.technical_task.service.filter;

import com.example.technical_task.entity.Student;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {
    public static Specification<Student> hasStudyGroupId(Long studyGroupId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("studyGroup", JoinType.LEFT).get("id"), studyGroupId);
    }

    public static Specification<Student> hasCourseId(Long courseId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("courses", JoinType.LEFT).get("id"), courseId);
    }

    public static Specification<Student> hasAgeGreaterThan(Integer age) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("age"), age);
    }
}
