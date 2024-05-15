package com.example.authservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.example.authservice.controller.AuthController;
import com.example.authservice.dto.AuthRequest;
import com.example.authservice.entity.UserCredential;
import com.example.authservice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testAddUserWithoutInviteCode() throws Exception {
        UserCredential userCredential = new UserCredential();
        String userCredentialJson = "{\"email\":\"test@example.com\",\"password\":\"password\"}";

        when(authService.saveOwner(any(UserCredential.class))).thenReturn("User added as owner");

        mockMvc.perform(post("/auth/register")
                        .content(userCredentialJson)
                        .param("inviteCode", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User added as owner"));
    }

    @Test
    public void testAddUserWithInviteCode() throws Exception {
        UserCredential userCredential = new UserCredential();
        String userCredentialJson = "{\"email\":\"test@example.com\",\"password\":\"password\"}";

        when(authService.saveStaff(any(UserCredential.class), eq("inviteCode123"))).thenReturn("User added as staff");

        mockMvc.perform(post("/auth/register")
                        .content(userCredentialJson)
                        .param("inviteCode", "inviteCode123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User added as staff"));
    }

    @Test
    public void testGetToken() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("password");
        String authRequestJson = "{\"email\":\"test@example.com\",\"password\":\"password\"}";

        // Создаем макет для Authentication
        Authentication authentication = mock(Authentication.class);

        // Убеждаемся, что authenticate() возвращает аутентифицированный объект
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authService.generateToken(authRequest.getEmail())).thenReturn("generatedToken");

        mockMvc.perform(post("/auth/token")
                        .content(authRequestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("generatedToken"));
    }

}
