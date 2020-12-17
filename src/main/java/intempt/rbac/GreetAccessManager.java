package intempt.rbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.RequestPath;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

@Slf4j
public class GreetAccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext ctx) {
        return authentication.flatMap(auth -> {
            log.info("{}", ctx.getVariables());
            final RequestPath path = ctx.getExchange().getRequest().getPath();
            log.info("Path {}", path);
            return Mono.just(new AuthorizationDecision(true));
        });
    }
}
