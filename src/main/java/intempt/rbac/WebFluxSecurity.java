package intempt.rbac;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebFluxSecurity {

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                // Demonstrate that method security works
                // Best practice to use both for defense in depth
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/greet").access(greetAccessManager())
                        .anyExchange().authenticated()
                        .and()
                )
                .formLogin(Customizer.withDefaults())
                .anonymous().principal("guest")
                .a
                .and()
                .oauth2ResourceServer(c -> c.)
                .build();
    }

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails rob = userBuilder.username("bob")
                .password("bob")
                .roles("USER")
                .build();
        UserDetails admin = userBuilder.username("admin")
                .password("admin")
                .roles("USER","ADMIN")
                .build();
        return new MapReactiveUserDetailsService(rob, admin);
    }


    public MyDefaultMethodSecurityExpressionHandler myMethodSecurityExpressionHandler() {
        return new MyDefaultMethodSecurityExpressionHandler();
    }

    @Bean
    ReactiveAuthorizationManager<AuthorizationContext> greetAccessManager() {
        return new GreetAccessManager();
    }
}
