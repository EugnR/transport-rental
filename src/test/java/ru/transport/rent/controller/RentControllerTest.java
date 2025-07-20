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

        //region register, sign in, get jwt and register 2 transports
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


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/Rent/Transport")
                                .param("lat", "53.2257244")
                                .param("lon", "50.1945633")
                                .param("radius", "250")
                                .param("type", "All")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].model").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].identifier").value("om777j"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].model").value("Whoosh"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].identifier").value("aj17h"));

    }
}
