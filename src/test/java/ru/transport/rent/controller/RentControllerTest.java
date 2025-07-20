package ru.transport.rent.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.transport.rent.AbstractMainTest;
import ru.transport.rent.CommonUtils;

public class RentControllerTest extends AbstractMainTest {

    @Test
    @DisplayName("finding all transport around")
    void testShouldFindAllTransportAround() throws Exception {

        //region register, sign in, get jwt and register transport
        final String userRegistrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser.json");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationJson)
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

        String transportRegistrationJson = CommonUtils
                .getJsonFromResource("transport-controller/RequestRegisterTransport.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Transport")
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transportRegistrationJson)
                )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        transportRegistrationJson = CommonUtils
                .getJsonFromResource("transport-controller/RequestRegisterTransport2.json");
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Transport")
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transportRegistrationJson)
                )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

//endregion


        final String getAllTransportAroundJson = CommonUtils
                .getJsonFromResource("rent-controller/RequestGetAllTransportAround.json");
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/Rent/Transport")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getAllTransportAroundJson)
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());



    }
}
