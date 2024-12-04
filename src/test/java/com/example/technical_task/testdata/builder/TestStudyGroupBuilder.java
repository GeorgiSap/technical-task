package com.example.technical_task.testdata.builder;

import com.example.technical_task.entity.StudyGroup;

import java.util.UUID;


public class TestStudyGroupBuilder {
    public static StudyGroup.StudyGroupBuilder builder() {
        return StudyGroup
                .builder()
                .name(UUID.randomUUID().toString());
    }

}
