package ru.transport.rent.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.transport.rent.AbstractMainTest;
import ru.transport.rent.CommonUtils;
import ru.transport.rent.entity.User;
import ru.transport.rent.repository.UserRepository;


public class UserControllerTest extends AbstractMainTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("registration user")
    public void testShouldRegistrationUser() throws Exception {
        final String jsonFromResource = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser.json");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFromResource)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());


        final List<User> all = userRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    @DisplayName("sign in user")
    public void testShouldGiveJwtTokenUser() throws Exception {
        final String registrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Account/SignUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(registrationJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        final String authJson = CommonUtils
                .getJsonFromResource("user-controller/RequestSignInUser.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Account/SignIn")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    @DisplayName("get account details")
    public void testShouldReturnUserDetails() throws Exception {
        String registrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Account/SignUp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(registrationJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        String signInJson = CommonUtils
                .getJsonFromResource("user-controller/RequestSignInUser.json");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Account/SignIn")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signInJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jwt = result.getResponse().getContentAsString();

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/Account/Me")
                                .header("Authorization", "Bearer " + jwt)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("username"));
    }

    @Test
    @DisplayName("change account login and password")
    public void testShouldChangeAccountLoginAndPassword() throws Exception {
        final String registrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser.json");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registrationJson)
        );

        final String authJson = CommonUtils
                .getJsonFromResource("user-controller/RequestSignInUser.json");
        MvcResult authResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authJson)
                )
                .andReturn();

        String jwt = authResult.getResponse().getContentAsString();

        User userBefore = userRepository.findByUserName(
                CommonUtils.getFieldFromJson(registrationJson, "username")
                )
                .orElseThrow(() -> new RuntimeException("User not found after registration"));
        String oldUsername = userBefore.getUserName();
        String oldPasswordHash = userBefore.getPassword();

        final String logAndPassJson = CommonUtils
                .getJsonFromResource("user-controller/RequestChangeLoginAndPassword.json");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/Account/Update")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(logAndPassJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

        User userAfter = userRepository.findByUserName(
                CommonUtils.getFieldFromJson(logAndPassJson, "username")
                )
                .orElseThrow(() -> new RuntimeException("User not found after update"));
        String newUsername = userAfter.getUserName();
        String newPasswordHash = userAfter.getPassword();

        Assertions.assertNotEquals(oldUsername, newUsername);
        Assertions.assertNotEquals(oldPasswordHash, newPasswordHash);

    }
}
