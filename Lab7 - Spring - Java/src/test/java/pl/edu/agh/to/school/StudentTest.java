package pl.edu.agh.to.school;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.course.CourseRepository;
import pl.edu.agh.to.school.grade.GradeRepository;
import pl.edu.agh.to.school.student.Student;
import pl.edu.agh.to.school.student.StudentRepository;
import pl.edu.agh.to.school.student.StudentService;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class StudentTest {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentService studentService;

    @Test
    public void addDeleteStudentTest() {
        // Clear all repositories
        gradeRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        Student student = new Student("Jan", "Kowalski", LocalDate.now(), "123457");
        studentRepository.save(student);
        assert studentRepository.count() == 1;

        studentRepository.deleteAll();
        assert studentRepository.count() == 0;
    }

    @Test
    public void enrollStudentTest(){
        // Clear all repositories
        gradeRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        Student student = new Student("Jan", "Kowalski", LocalDate.now(), "123457");
        Course course = new Course("Technologie obiektowe");
        studentRepository.save(student);
        courseRepository.save(course);
        assert studentRepository.count() == 1;
        assert courseRepository.count() == 1;

        course.assignStudent(student);
        courseRepository.save(course);
        assert course.getStudents().size() == 1;
        assert student.getCourse().getName().equals("Technologie obiektowe");

    }

    @Test
    public void calculateMeanTest(){
        // Clear all repositories
        gradeRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();

        Student student = new Student("Jan", "Kowalski", LocalDate.now(), "123457");

        studentRepository.save(student);
        assert studentRepository.count() == 1;

        studentService.giveGrade(student.getId(), 5, 1);
        studentService.giveGrade(student.getId(), 4, 1);
        assert studentService.calculateGradesMean(student.getId()) == 4.5;
    }
}
