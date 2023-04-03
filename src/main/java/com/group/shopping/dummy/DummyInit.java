package com.group.shopping.dummy;

import com.group.shopping.domain.member.Member;
import com.group.shopping.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
public class DummyInit extends DummyObject{

    @Profile("dev")
    @Bean
    CommandLineRunner init(MemberRepository memberRepository) {
        return (args -> {
            Member hong = memberRepository.save(newMember("홍길동", "hong@naver.com"));
            Member armin = memberRepository.save(newAdmin("아르민", "armin@naver.com"));
        });
    }
}
