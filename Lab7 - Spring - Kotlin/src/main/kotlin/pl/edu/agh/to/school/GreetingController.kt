package pl.edu.agh.to.school

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    @GetMapping
    fun greeting(): List<String>{
        return listOf("Technologie", "obiektowe");
    }
}