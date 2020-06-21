package com.aidriveall.cms.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyUserDetails extends org.springframework.security.core.userdetails.User {
    private Long id;

    public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public MyUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
