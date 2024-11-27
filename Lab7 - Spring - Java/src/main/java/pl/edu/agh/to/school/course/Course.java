package pl.edu.agh.to.school.course;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.student.Student;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @OneToMany
    private List<Student> students;

    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private List<Grade> gradesList = new ArrayList<>();

    public Course(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void assignStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Grade> getGradesList() {
        return gradesList;
    }

    public void addGrade(Grade grade) {
        gradesList.add(grade);
    }
}
