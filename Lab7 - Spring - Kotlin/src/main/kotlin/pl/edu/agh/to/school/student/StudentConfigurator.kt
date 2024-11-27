package pl.edu.agh.to.school.student

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate


@Configuration
class StudentConfigurator {
    @Bean
    fun commandLineRunner(studentRepository: StudentRepository): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            if (studentRepository.count() == 0L) {
                val kowalski = Student("Jan", "Kowalski", LocalDate.now(), "123456")
                studentRepository.save(kowalski)
            }
        }
    }
}