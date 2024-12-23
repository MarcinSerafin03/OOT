package pl.edu.agh.to.school.student;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudent(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void giveGrade(int studentId, int gradeValue, int courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalArgumentException("Student not found"));

        Course course = student.getCourse();
        Grade finalGrade = new Grade(gradeValue, course);
        student.giveGrade(finalGrade);
        course.addGrade(finalGrade);

        gradeRepository.save(finalGrade);
        studentRepository.save(student);

    }

    public double calculateGradesMean(int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new IllegalArgumentException("Student not found"));

        List<Grade> grades = student.getGrades();
        if (grades.isEmpty()) {
            throw new IllegalStateException("Student has no grades");
        }

        return grades.stream()
                .mapToInt(Grade::getGradeValue)
                .average()
                .orElseThrow(() -> new IllegalStateException("Error calculating grades mean"));

    }
}