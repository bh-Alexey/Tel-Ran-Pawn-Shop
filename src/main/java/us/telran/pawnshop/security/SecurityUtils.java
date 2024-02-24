package us.telran.pawnshop.security;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@UtilityClass
public class SecurityUtils {

    public Long getCurrentManagerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.ofNullable(authentication)
                .filter(auth -> auth.getPrincipal() instanceof ManagerDetails)
                .map(auth -> ((ManagerDetails) auth.getPrincipal()).getManagerId())
                .orElse(null);
    }
}

