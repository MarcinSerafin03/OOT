package pl.edu.agh.to.school.student

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import pl.edu.agh.to.school.grade.Grade
import java.time.LocalDate

@Entity
class Student (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Int,
    private val firstName: String,
    private val lastName: String,
    private val birthDate: LocalDate,
    private val indexNumber: String,
    private val grades: ArrayList<Grade>
    ){
    constructor() : this(0, "", "", LocalDate.now(), "", ArrayList())

    constructor(firstName: String, lastName: String, birthDate: LocalDate, indexNumber: String,grades: ArrayList<Grade>) : this(0, firstName, lastName, birthDate, indexNumber, grades)

    fun getId(): Int = id

    fun getFirstName(): String = firstName

    fun getLastName(): String = lastName

    fun getBirthDate(): LocalDate = birthDate

    fun getIndexNumber(): String = indexNumber

    fun getGrades(): List<Grade> = grades

    fun giveGrade(grade: Grade) {
        grades.add(grade)
    }

}