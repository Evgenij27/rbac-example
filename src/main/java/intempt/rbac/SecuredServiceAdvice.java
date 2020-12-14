package intempt.rbac;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Aspect
@Slf4j
public class SecuredServiceAdvice {

    @Pointcut("@annotation(preSecuredByName)")
    public void preSecuredServicePointcut(PreSecuredByName preSecuredByName) {}


    @Around("preSecuredServicePointcut(preSecuredByName)")
    public Object preSecured(ProceedingJoinPoint pjp, PreSecuredByName preSecuredByName) throws Throwable {
        log.info("Before advice");

        MethodSignature method = (MethodSignature) pjp.getSignature();
        final Class<?> returnType = method.getReturnType();

        Mono<Authentication> toInvoke = ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth.getName().equalsIgnoreCase("bob"))
                .switchIfEmpty(Mono.error(new AccessDeniedException("Not Bob!")));

        if (Flux.class.isAssignableFrom(returnType)) {
            return toInvoke.flatMapMany(auth -> SecuredServiceAdvice.<Flux<?>>proceed(pjp));
        }

        if (Mono.class.isAssignableFrom(returnType)) {
            return toInvoke.flatMap(auth -> SecuredServiceAdvice.<Mono<?>>proceed(pjp));
        }

        return toInvoke.flatMapMany(auth -> Flux.from(SecuredServiceAdvice.<Publisher<?>>proceed(pjp)));
    }

    private static <T extends Publisher<?>> T proceed(ProceedingJoinPoint pjp) {
        try {
            return (T) pjp.proceed();
        } catch (Throwable t) {
            throw Exceptions.propagate(t);
        }
    }


}
