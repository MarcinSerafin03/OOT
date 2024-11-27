package pl.edu.agh.to.school.course

import pl.edu.agh.to.school.student.Student

class Course(
    val name: String,
    val students: ArrayList<Student>
) {
    fun getStudents(): List<Student> {
        return students
    }

    fun assignStudents(student: Student) {
        students.add(student)
    }

    fun removeStudent(student: Student) {
        students.remove(student)
    }
}