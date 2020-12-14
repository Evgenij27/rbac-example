package intempt.rbac;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class GreetingController {

    private final GreetingService service;

    @GetMapping("/greet")
    public Mono<String> greet() {
        return service.greet();
    }
}
