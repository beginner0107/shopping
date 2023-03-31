package com.group.shopping.web;

import com.group.shopping.domain.member.Member;
import com.group.shopping.dto.member.MemberFormDto;
import com.group.shopping.service.member.MemberService;
import com.group.shopping.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("회원 관련 컨트롤러")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@Transactional
class MemberControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private FormDataEncoder formDataEncoder;

    @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired private MemberService memberService;

    @DisplayName("[view][GET] 회원가입 페이지")
    @Test
    void givenNothing_whenRequestingMemberForm_thenReturnsMemberForm() throws Exception {
        // Given
        // When & Then
        mvc.perform(get("/members/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("member/memberForm"))
                .andExpect(model().attributeExists("memberFormDto"));
    }

    @DisplayName("[view][POST] 회원 등록")
    @Test
    void givenNewMember_whenRequesting_thenSavesNewMember() throws  Exception {
        // Given
        MemberFormDto memberFormDto = MemberFormDto
                                        .of("홍길동", "hong@naver.com", "12345678", "홍길동");

        // When & Then
        mvc.perform(
                post("/members/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(formDataEncoder.encode(memberFormDto))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("[view][POST] 로그인 성공")
    @Test
    void givenEmailPassword_whenRequesting_thenLogin() throws Exception {
        // Given
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);

        // When & Then
        mvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @DisplayName("[view][POST] 로그인 실패")
    @Test
    void givenEmailPassword_whenRequesting_thenFailLogin() throws Exception {
        // Given
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);

        // When & Then
        mvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email).password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }

    public Member createMember(String email, String password) {
        MemberFormDto memberFormDto = MemberFormDto.of("홍길동", email, password, "서울시 마포구 합정동");
        Member member = Member.createMember(memberFormDto, bCryptPasswordEncoder);
        return memberService.saveMember(member);
    }
}