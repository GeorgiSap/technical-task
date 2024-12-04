package com.example.technical_task.controller;

import com.example.technical_task.service.ParticipantService;
import com.example.technical_task.service.dto.ParticipantsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @GetMapping
    public ResponseEntity<ParticipantsDto> getAllParticipantsByStudyGroupAndCourse(@RequestParam Long studyGroupId,
                                                                                   @RequestParam Long courseId) {
        ParticipantsDto teachers = participantService.getByStudyGroupAndCourse(studyGroupId, courseId);
        return ResponseEntity.ok(teachers);
    }

}
