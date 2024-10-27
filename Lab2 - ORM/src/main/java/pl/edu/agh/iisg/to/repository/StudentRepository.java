package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

public class StudentRepository implements Repository<Student> {
    private final StudentDao studentDao;
    private final GradeDao gradeDao;
    private final CourseDao courseDao;
    private final TransactionService transactionService;

    public StudentRepository(StudentDao studentDao, GradeDao gradeDao, CourseDao courseDao, TransactionService transactionService) {
        this.studentDao = studentDao;
        this.gradeDao = gradeDao;
        this.courseDao = courseDao;
        this.transactionService = transactionService;
    }

    public TransactionService transactionService() {
        return transactionService;
    }

    @Override
    public Optional<Student> add(Student student) {
        return studentDao.save(student);
    }

    public Optional<Grade> addGrade(Grade grade){
        return gradeDao.save(grade);
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findById(id);
    }

    public Optional<Student> findByIndexNumber(int indexNumber) {
        return studentDao.findByIndexNumber(indexNumber);
    }

    public Optional<Course> getCourseByName(String name){
        return courseDao.findByName(name);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
        student.courseSet().forEach(course -> course.studentSet().remove(student));
        student.gradeSet().forEach(gradeDao::remove);
        studentDao.remove(student);
    }

    public List<Student> findAllByCourseName(String courseName) {
        try {
            Optional <Course> course = courseDao.findByName(courseName);
            if (course.isPresent()) {
                List<Student> students = new ArrayList<>();
                Set<Student> studentSet = course.get().studentSet();
                for (Student student : studentSet) {
                    if (!students.contains(student)) {
                        students.add(student);
                    }
                }
                return students;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
