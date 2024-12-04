package com.example.technical_task.service;

import com.example.technical_task.entity.Student;
import com.example.technical_task.repository.StudentRepository;
import com.example.technical_task.service.dto.StudentDto;
import com.example.technical_task.service.filter.StudentCriteria;
import com.example.technical_task.service.filter.StudentSpecifications;
import com.example.technical_task.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    @Transactional(readOnly = true)
    public long count() {
        return studentRepository.count();
    }

    @Transactional(readOnly = true)
    public StudentDto getById(Long id) {
        return studentMapper.toDto(studentRepository.findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<StudentDto> getAllByCriteria(StudentCriteria studentCriteria) {
        Specification<Student> specification = Specification.where(null);

        if (studentCriteria.getStudyGroupId() != null) {
            specification = specification.and(StudentSpecifications.hasStudyGroupId(studentCriteria.getStudyGroupId()));
        }
        if (studentCriteria.getCourseId() != null) {
            specification = specification.and(StudentSpecifications.hasCourseId(studentCriteria.getCourseId()));
        }
        if (studentCriteria.getAge() != null) {
            specification = specification.and(StudentSpecifications.hasAgeGreaterThan(studentCriteria.getAge()));
        }

        return studentRepository.findAll(specification).stream()
                .map(studentMapper::toDto)
                .toList();
    }

    @Transactional
    public StudentDto save(StudentDto studentDto) {
        return studentMapper.toDto(studentRepository.save(studentMapper.toEntity(studentDto)));
    }

    @Transactional
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
