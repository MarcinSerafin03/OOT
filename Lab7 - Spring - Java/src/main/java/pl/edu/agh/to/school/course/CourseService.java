package pl.edu.agh.to.school.course;

import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.student.Student;


import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public List<Student> getEnrolledStudentsWithId(int courseId) {
        if(courseRepository.findById(courseId).isEmpty()) {
            return new ArrayList<>();
        }
        return courseRepository.findById(courseId).get().getStudents();
    }

    public int calculateCourseGradesMean(int courseId) {
        if (courseRepository.findById(courseId).isEmpty()) {
            return 0;
        }
        Course course = courseRepository.findById(courseId).get();
        List<Student> students = course.getStudents();
        int sum = 0;
        for (Grade grade : course.getGradesList()) {
            sum += grade.getGradeValue();
        }
        return sum / students.size();
    }
}
