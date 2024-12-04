package com.example.technical_task.testdata;

import com.github.javafaker.Faker;

import java.util.UUID;

public class TestDataUtil {
    private static final Faker FAKER = new Faker();

    public static String randomUniqueCourseName() {
        return FAKER.educator().course() + " " + UUID.randomUUID();
    }

    public static int randomAge() {
        return FAKER.number().numberBetween(1, 100);
    }

    public static String randomName() {
        return FAKER.name().fullName();
    }
}
