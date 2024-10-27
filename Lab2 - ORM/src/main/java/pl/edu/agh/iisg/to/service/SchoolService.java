package pl.edu.agh.iisg.to.service;

import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.repository.StudentRepository;

import java.util.*;

public class SchoolService {

//    private final TransactionService transactionService;
//
//    private final StudentDao studentDao;
//
//    private final CourseDao courseDao;
//
//    private final GradeDao gradeDao;

    private final StudentRepository studentRepository;

    public SchoolService(StudentRepository studentRepository) {
//        this.transactionService = transactionService;
//        this.studentDao = studentDao;
//        this.courseDao = courseDao;
//        this.gradeDao = gradeDao;
        this.studentRepository = studentRepository;
    }

    public boolean enrollStudent(final Course course, final Student student) {
            return studentRepository.transactionService().doAsTransaction(() -> {
                if (!course.studentSet().contains(student) & !student.courseSet().contains(course)) {
                    course.studentSet().add(student);
                    student.courseSet().add(course);
                    return true;
                }
                return false;
                }).orElse(false);
    }


    public boolean removeStudent(int indexNumber) {
        studentRepository.transactionService().doAsTransaction(() -> {
            Optional<Student> optionalStudent = studentRepository.findByIndexNumber(indexNumber);
            if(optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                studentRepository.remove(student);
                return true;
            }
            return false;
        });
        return false;
    }

    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        return studentRepository.transactionService().doAsTransaction(() -> {
            try{
                Grade grade = new Grade(student, course, gradeValue);
                student.gradeSet().add(grade);
                course.gradeSet().add(grade);
                studentRepository.addGrade(grade);
                return true;
            }catch (Exception e){
                return false;
            }
        }).orElse(false);
    }

    public Map<String, List<Float>> getStudentGrades(String courseName) {
        Optional<Course> course = studentRepository.getCourseByName(courseName);
        Map<String, List<Float>> grades = new HashMap<>();
        for (Student s : course.get().studentSet()) {
            List<Float> gradesList = course.get().gradeSet().stream()
                    .filter(grade -> grade.student().equals(s))
                    .map(Grade::grade)
                    .sorted()
                    .toList();
            grades.put(s.fullName(), gradesList);
        }
        return grades;
    }

}
