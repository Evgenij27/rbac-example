package intempt.rbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityBean {

    public boolean security(String msg) {
        log.info("Hello {}", msg);
        return msg != null;
    }
}
