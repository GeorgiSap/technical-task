package com.example.technical_task.service.mapper;

import com.example.technical_task.entity.StudyGroup;
import com.example.technical_task.service.dto.StudentDto;
import com.example.technical_task.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper implements EntityMapper<Student, StudentDto> {

    @Override
    public StudentDto toDto(Student entity) {
        if (entity == null) {
            return null;
        }
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setStudentId(entity.getStudentId());
        dto.setStudyGroupId(entity.getStudyGroup().getId());
        return dto;
    }

    @Override
    public Student toEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setId(dto.getStudyGroupId());

        Student entity = new Student();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setStudentId(dto.getStudentId());
        entity.setStudyGroup(studyGroup);
        return entity;
    }
}
