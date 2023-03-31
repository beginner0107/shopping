package com.group.shopping.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest {

    @Autowired MockMvc mvc;

    @Test
    @DisplayName("[view][GET] 상품 등록 페이지 권한 테스트 - 관리자")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void givenNothing_whenRequestingItemForm_thenReturnAdminItemForm() throws Exception {
        // Given
        // When & Then
        mvc.perform(get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("/item/itemForm"));
    }

    @Test
    @DisplayName("[view][GET] 상품 등록 페이지 권한 테스트 - 일반 회원 실패")
    @WithMockUser(username = "user", roles = "USER")
    void givenNothing_whenRequestingItemForm_thenReturnForbidden() throws Exception {
        // Given
        // When & Then
        mvc.perform(get("/admin/item/new"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}