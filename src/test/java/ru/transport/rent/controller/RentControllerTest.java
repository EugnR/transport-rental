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
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.repository.TransportRepository;

public class RentControllerTest extends AbstractMainTest {

    @Autowired
    RentRepository rentRepository;
    @Autowired
    TransportRepository transportRepository;

    private String signUpAndSignInUser(final String regJsonPath, final String authJsonPath) throws Exception {
        final String userRegistrationJson = CommonUtils
                .getJsonFromResource(regJsonPath);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationJson)
        );

        final String authJson = CommonUtils
                .getJsonFromResource(authJsonPath);
        MvcResult authResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Account/SignIn")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authJson)
                )
                .andReturn();

        return authResult.getResponse().getContentAsString();
    }

    private void registerTransport(final String jwt, final String transportDetailsJsonPath) throws Exception {
        String transportRegistrationJson = CommonUtils
                .getJsonFromResource(transportDetailsJsonPath);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Transport")
                        .header("Authorization", "Bearer " + jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transportRegistrationJson)
        );
    }

    private void createRent(final String jwt, final String transportId, final String rentType) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Rent/New/" + transportId)
                                .header("Authorization", "Bearer " + jwt)
                                .param("rentType", rentType)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
    }

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
                                .param("long", "50.1945633")
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

    @Test
    @DisplayName("creating a rent")
    void testShouldCreateRent() throws Exception {
        //region register 1'st user, register 1'st user's car, register 2'nd user
        String userRegistrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser.json");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationJson)
        );

        String authJson = CommonUtils
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
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        final List<Transport> allTransport = transportRepository.findAll();
        Assertions.assertEquals(1, allTransport.size());


        userRegistrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser2.json");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationJson)
        );

        authJson = CommonUtils
                .getJsonFromResource("user-controller/RequestSignInUser2.json");
        authResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Account/SignIn")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authJson)
                )
                .andReturn();

        jwt = authResult.getResponse().getContentAsString();
        //endregion

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Rent/New/1")
                        .header("Authorization", "Bearer " + jwt)
                        .param("rentType", "Days")
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final List<Rent> allRents =rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
    }

    @Test
    @DisplayName("making sure that owner can't rent his own car")
    void testShouldNotLetOwnerRentHisOwnCar() throws Exception {
        //region register, sign up and register a car
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

        final String jwt = authResult.getResponse().getContentAsString();

        final String transportRegistrationJson = CommonUtils
                .getJsonFromResource("transport-controller/RequestRegisterTransport.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Transport")
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(transportRegistrationJson)
                )
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
        List<Transport> allTransport = transportRepository.findAll();
        Assertions.assertEquals(1, allTransport.size());
        //endregion

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Rent/New/1")
                                .header("Authorization", "Bearer " + jwt)
                                .param("rentType", "Days")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        final List<Rent> allRents =rentRepository.findAll();
        Assertions.assertEquals(0, allRents.size());

    }

    @Test
    @DisplayName("ending rent")
    void testShouldEndRent() throws Exception {
        //region register 1'st user, register 1'st user's car, register 2'nd user and create rent
        String userRegistrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser.json");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationJson)
        );

        String authJson = CommonUtils
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
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        final List<Transport> allTransport = transportRepository.findAll();
        Assertions.assertEquals(1, allTransport.size());


        userRegistrationJson = CommonUtils
                .getJsonFromResource("user-controller/RequestRegistrationUser2.json");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Account/SignUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationJson)
        );

        authJson = CommonUtils
                .getJsonFromResource("user-controller/RequestSignInUser2.json");
        authResult = mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Account/SignIn")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authJson)
                )
                .andReturn();

        jwt = authResult.getResponse().getContentAsString();

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Rent/New/1")
                                .header("Authorization", "Bearer " + jwt)
                                .param("rentType", "Days")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
        //endregion

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Rent/End/1")
                        .header("Authorization", "Bearer " + jwt)
                        .param("lat", "55.7539939")
                        .param("long", "37.6220930")
        ). andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("making sure that user can't end other's rent")
    void testShouldNotLetEndOthersRent() throws Exception {
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport.json");
        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        createRent(jwtUser2, "1", "Days");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Rent/End/1")
                .header("Authorization", "Bearer " + jwtUser1)
                .param("lat", "55.7539939")
                .param("long", "37.6220930")
        ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
