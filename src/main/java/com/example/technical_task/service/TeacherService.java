package com.example.technical_task.service;

import com.example.technical_task.entity.Student;
import com.example.technical_task.entity.Teacher;
import com.example.technical_task.repository.TeacherRepository;
import com.example.technical_task.service.dto.StudentDto;
import com.example.technical_task.service.dto.TeacherDto;
import com.example.technical_task.service.filter.TeacherCriteria;
import com.example.technical_task.service.filter.TeacherSpecifications;
import com.example.technical_task.service.mapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;

    @Transactional(readOnly = true)
    public long count() {
        return teacherRepository.count();
    }

    @Transactional(readOnly = true)
    public TeacherDto getById(Long id) {
        return teacherMapper.toDto(teacherRepository.findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<TeacherDto> getAllByCriteria(TeacherCriteria teacherCriteria) {
        Specification<Teacher> specification = Specification.where(null);

        if (teacherCriteria.getStudyGroupId() != null) {
            specification = specification.and(TeacherSpecifications.hasStudyGroupId(teacherCriteria.getStudyGroupId()));
        }
        if (teacherCriteria.getCourseId() != null) {
            specification = specification.and(TeacherSpecifications.hasCourseId(teacherCriteria.getCourseId()));
        }

        return teacherRepository.findAll(specification).stream()
                .map(teacherMapper::toDto)
                .toList();
    }

    @Transactional
    public TeacherDto create(TeacherDto teacherDto) {
        return teacherMapper.toDto(teacherRepository.save(teacherMapper.toEntity(teacherDto)));
    }

    @Transactional
    public TeacherDto update(TeacherDto teacherDto) {
        if (!teacherRepository.existsById(teacherDto.getId())) {
            throw new IllegalStateException("Teacher with id " + teacherDto.getId() + " not found");
        }

        return teacherMapper.toDto(teacherRepository.save(teacherMapper.toEntity(teacherDto)));
    }

    @Transactional
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
}
