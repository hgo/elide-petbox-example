package app;

import domain.Role;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.Subject;
import java.security.Principal;

@Value
@Slf4j
public class UserAccount implements Principal {


    String name;
    Role role;

    @Override
    public boolean implies(Subject subject) {
        log.info("implies" + subject);
        return false;
    }
}
