package com.group.shopping.dummy;

import com.group.shopping.domain.constant.Role;
import com.group.shopping.domain.member.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class DummyObject {

    protected Member newMember(String name, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("asdf1234");
        return Member.builder()
                .name(name)
                .password(encPassword)
                .email(email)
                .address("경기도 용인시 기흥구")
                .role(Role.USER)
                .build();
    }

    protected Member newAdmin(String name, String email) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("asdf1234");
        return Member.builder()
                .name(name)
                .password(encPassword)
                .email(email)
                .address("경기도 용인시 기흥구")
                .role(Role.ADMIN)
                .build();
    }
}
