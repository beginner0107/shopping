package com.group.shopping.web;

import com.group.shopping.dto.member.MemberFormDto;
import com.group.shopping.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("회원 관련 컨트롤러")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MemberControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private FormDataEncoder formDataEncoder;

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
}