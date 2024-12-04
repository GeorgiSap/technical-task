package com.example.technical_task.testdata;

import com.example.technical_task.entity.Course;
import com.example.technical_task.entity.Student;
import com.example.technical_task.entity.StudyGroup;
import com.example.technical_task.entity.Teacher;
import com.example.technical_task.repository.CourseRepository;
import com.example.technical_task.repository.StudentRepository;
import com.example.technical_task.repository.StudyGroupRepository;
import com.example.technical_task.repository.TeacherRepository;
import com.example.technical_task.testdata.builder.TestCourseBuilder;
import com.example.technical_task.testdata.builder.TestStudentBuilder;
import com.example.technical_task.testdata.builder.TestStudyGroupBuilder;
import com.example.technical_task.testdata.builder.TestTeacherBuilder;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.Set;

@TestConfiguration
@Getter
public class TestDataConfiguration {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private Course course1;
    private Course course2;
    private Course course3;

    private StudyGroup studyGroup1;
    private StudyGroup studyGroup2;
    private StudyGroup studyGroup3;

    private Teacher teacher1;
    private Teacher teacher2;
    private Teacher teacher3;

    private Student student1;
    private Student student2;
    private Student student3;

    @PostConstruct
    void setUp() {
        course1 = courseRepository.save(TestCourseBuilder.builder().build());
        course2 = courseRepository.save(TestCourseBuilder.builder().build());
        course3 = courseRepository.save(TestCourseBuilder.builder().build());

        studyGroup1 = studyGroupRepository.save(TestStudyGroupBuilder.builder().build());
        studyGroup2 = studyGroupRepository.save(TestStudyGroupBuilder.builder().build());
        studyGroup3 = studyGroupRepository.save(TestStudyGroupBuilder.builder().build());

        teacher1 = teacherRepository.save(
                TestTeacherBuilder.builder()
                        .studyGroups(Set.of(studyGroup1, studyGroup2))
                        .courses(Set.of(course1))
                        .build()
        );

        teacher2 = teacherRepository.save(
                TestTeacherBuilder.builder()
                        .studyGroups(Set.of(studyGroup2))
                        .courses(Set.of(course1, course2))
                        .build()
        );

        teacher3 = teacherRepository.save(
                TestTeacherBuilder.builder()
                        .studyGroups(Set.of(studyGroup1, studyGroup2))
                        .courses(Set.of(course2))
                        .build()
        );

        student1 = studentRepository.save(
                TestStudentBuilder.builder()
                        .studyGroup(studyGroup1)
                        .courses(Set.of(course1, course2))
                        .age(20)
                        .build()
        );

        student2 = studentRepository.save(
                TestStudentBuilder.builder()
                        .studyGroup(studyGroup2)
                        .courses(Set.of(course1))
                        .age(25)
                        .build()
        );

        student3 = studentRepository.save(
                TestStudentBuilder.builder()
                        .studyGroup(studyGroup1)
                        .courses(Set.of(course2))
                        .age(30)
                        .build()
        );
    }
}
