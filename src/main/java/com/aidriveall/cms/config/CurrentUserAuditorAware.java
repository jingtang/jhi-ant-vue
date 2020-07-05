package com.aidriveall.cms.config;

import com.aidriveall.cms.domain.User;
import com.aidriveall.cms.security.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUserAuditorAware implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {
        // 这个admin是查出来的, 我只是写死了
        User currentUser = new User();
        currentUser.setLogin(SecurityUtils.getCurrentUserLogin().get());
        currentUser.setId(SecurityUtils.getCurrentUserId().get());
        return Optional.ofNullable(currentUser);
    }
}
