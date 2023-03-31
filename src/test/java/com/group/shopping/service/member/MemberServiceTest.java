package com.group.shopping.service.member;

import com.group.shopping.domain.member.Member;
import com.group.shopping.dto.member.MemberFormDto;
import com.group.shopping.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("회원가입 테스트")
    @Test
    void givenMember_whenJoinMember_thenReturnMember() {
        // Given
        Member member = createMember();
        given(memberRepository.save(any())).willReturn(member);

        // When
        Member savedMember = memberService.saveMember(member);

        // Then
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @DisplayName("중복 회원 가입 테스트")
    @Test
    void givenMember_whenJoinMember_thenThrowIllegalStateException() {
        // Given
        Member member = createMember();
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

        // When
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member);
        });

        // Then
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

    private Member createMember() {
        return Member.createMember(new MemberFormDto("홍길동",
                        "test@email.com",
                        "1234",
                        "서울시 마포구 합정동"), passwordEncoder);
    }
}