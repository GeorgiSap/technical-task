package com.example.technical_task.service.filter;

import com.example.technical_task.entity.Teacher;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class TeacherSpecifications {
    public static Specification<Teacher> hasStudyGroupId(Long studyGroupId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("studyGroups", JoinType.LEFT).get("id"), studyGroupId);
    }

    public static Specification<Teacher> hasCourseId(Long courseId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("courses", JoinType.LEFT).get("id"), courseId);
    }

}
