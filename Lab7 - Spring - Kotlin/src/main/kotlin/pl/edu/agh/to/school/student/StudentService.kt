package pl.edu.agh.to.school.student

import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class StudentService(
    private final val studentRepository: StudentRepository
) {

    fun getStudents(): List<Student> {
        return studentRepository.findAll();
    }
}