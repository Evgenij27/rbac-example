package intempt.rbac;

import lombok.Data;
import reactor.core.publisher.Flux;

@Data
public class Greetings {

    private final Flux<String> greetings;
}
