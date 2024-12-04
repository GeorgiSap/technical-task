# Technical Task

## Overview

A **Spring Boot** application for managing Students and Teachers information, such as name, age, group, and courses. The system supports two types of courses: **Main** and **Secondary**. It provides a public API to manage data and generate reports.

---

## Features

- Add, remove, or modify students and teachers.
- Generate reports:
  - Total number of students.
  - Total number of teachers.
  - Total courses by type (Main/Secondary).
  - Students in a specific course.
  - Students in a specific group.
  - Teachers and students in a specific group and course.
  - Students older than a specific age in a course.

---

## Run Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/GeorgiSap/technical-task.git
   cd technical-task
   ```
2. Build and run the application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. Access API: `http://localhost:8080/api`

---

## Tests

Run the tests with:
```bash
mvn test
```

---

## Future Improvements

- Introduce logging.
- Add authentication and authorization.
- Enable enrollment of students and teachers into courses and study groups
- Create tests for CRUD operations.
- Introduce caching for total number of courses by type.
- Add a mapping framework for DTOs.
