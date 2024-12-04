package com.example.technical_task.service.mapper;

import com.example.technical_task.entity.Teacher;
import com.example.technical_task.service.dto.TeacherDto;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper implements EntityMapper<Teacher, TeacherDto> {

    @Override
    public TeacherDto toDto(Teacher entity) {
        if (entity == null) {
            return null;
        }
        TeacherDto dto = new TeacherDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setTeacherId(entity.getTeacherId());
        return dto;
    }

    @Override
    public Teacher toEntity(TeacherDto dto) {
        if (dto == null) {
            return null;
        }
        Teacher entity = new Teacher();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setTeacherId(dto.getTeacherId());
        return entity;
    }
}
