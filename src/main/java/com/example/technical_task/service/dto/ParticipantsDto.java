package com.example.technical_task.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantsDto {
    private List<StudentDto> students;

    private List<TeacherDto> teachers;
}
