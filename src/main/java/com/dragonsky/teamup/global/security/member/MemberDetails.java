package com.dragonsky.teamup.global.security.member;

import com.dragonsky.teamup.member.model.Member;
import com.dragonsky.teamup.member.model.Role;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class MemberDetails implements UserDetails {
    private final Member member;

    public MemberDetails(Claims claims){
        Long id = claims.get("id",Long.class);
        String username = claims.get("username",String.class);
        Role role =Role.fromKey((String) claims.get("role"));

        this.member = Member.builder()
                .id(id)
                .email(username)
                .role(role)
                .password("")
                .nickname("")
                .build();
    }
    public Member getMember() {
        return member;
    }

    public Long getId(){
        return member.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole().getKey();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
