package intempt.rbac;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final GreetingService service;

    @GetMapping("/greet")
    public Mono<String> greet() {
        return service.greet("Bob");
    }

    @GetMapping("/greetings/{greet}")
    public Flux<List<String>> greetings(@PathVariable("greet") String greet) {
        return service.greetings(greet);
    }
}
