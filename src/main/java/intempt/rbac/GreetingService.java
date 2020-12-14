package intempt.rbac;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GreetingService {

    @PreSecuredByName("bob")
    public Mono<String> greet() {
        return Mono.just("Hi!");
    }
}
