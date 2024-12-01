package pl.edu.agh.to.school;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.course.CourseRepository;
import pl.edu.agh.to.school.student.Student;
import pl.edu.agh.to.school.grade.*;
import pl.edu.agh.to.school.student.StudentRepository;

import java.time.LocalDate;

@SpringBootTest(classes = SchoolApplication.class)
@AutoConfigureTestDatabase
@Transactional
public class GradeTest {

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;


    @Test
    public void addGradeTest() {
        // Clear all repositories
        gradeRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();


        Student student = new Student("Jan", "Kowalski", LocalDate.now(), "123457");
        Course course = new Course("Technologie obiektowe");
        Grade grade = new Grade(5, course);
        System.out.println(grade);

        student.giveGrade(grade);
        course.assignStudent(student);


        gradeRepository.save(grade);
        studentRepository.save(student);
        courseRepository.save(course);
        assert gradeRepository.count() == 1;

    }
}
