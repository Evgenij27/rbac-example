package intempt.rbac;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class GreetingService {

    @PreSecuredByName("bob")
    public Mono<String> greet(String name) {
        return Mono.just("Hi!" + name);
    }

    @PreAuthorize("@securityBean.security('filterObject')")
    public Flux<List<String>> greetings(String greet) {
        return Flux.just(Arrays.asList("hello", "world"));
    }
}
