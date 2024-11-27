package pl.edu.agh.to.school.student

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping(path = arrayOf("students"))
class StudentController(
    private final val studentService: StudentService
) {

    @GetMapping
    fun getStudents(): List<Student> {
        return studentService.getStudents()
    }

}