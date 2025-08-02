package ru.transport.rent.controller;

import java.time.LocalDateTime;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import ru.transport.rent.AbstractMainTest;
import ru.transport.rent.CommonUtils;
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.repository.UserRepository;


public class RentControllerTest extends AbstractMainTest {

    @Autowired
    RentRepository rentRepository;
    @Autowired
    TransportRepository transportRepository;
    @Autowired
    UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    }

    private void endRent(final String jwt, final Long rentId) throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Rent/End/" + rentId)
                                .header("Authorization", "Bearer " + jwt)
                                .param("lat", "55.7539939")
                                .param("long", "37.6220930")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("finding all transport around")
    void testShouldFindAllTransportAround() throws Exception {

        //region register, sign in, get jwt and register 2 transports
        String jwt = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwt, "transport-controller/RequestRegisterTransport.json");
        registerTransport(jwt, "transport-controller/RequestRegisterTransport2.json");
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
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport.json");

        final List<Transport> allTransport = transportRepository.findAll();
        Assertions.assertEquals(1, allTransport.size());
        Long transportId = allTransport.get(0).getId();

        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        //endregion

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Rent/New/" + transportId)
                        .header("Authorization", "Bearer " + jwtUser2)
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
        String jwt = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwt, "transport-controller/RequestRegisterTransport.json");

        List<Transport> allTransport = transportRepository.findAll();
        Assertions.assertEquals(1, allTransport.size());
        Long transportId = allTransport.get(0).getId();
        //endregion

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Rent/New/" + transportId)
                                .header("Authorization", "Bearer " + jwt)
                                .param("rentType", "Days")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(0, allRents.size());

    }

    @Test
    @DisplayName("ending rent")
    void testShouldEndRent() throws Exception {
        //region register 1'st user, register 1'st user's car, register 2'nd user and create rent
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport.json");

        final List<Transport> allTransport = transportRepository.findAll();
        Assertions.assertEquals(1, allTransport.size());

        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        createRent(jwtUser2, "1", "Days");

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
        Long rentId = allRents.get(0).getId();
        //endregion

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Rent/End/" + rentId)
                        .header("Authorization", "Bearer " + jwtUser2)
                        .param("lat", "55.7539939")
                        .param("long", "37.6220930")
        ). andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("making sure that user can't end other's rent")
    void testShouldNotLetEndOthersRent() throws Exception {
        //region register 1'st user, register 1'st user's car, register 2'nd user and create rent
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport.json");

        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        createRent(jwtUser2, "1", "Days");

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
        Long rentId = allRents.get(0).getId();
        //endregion

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/Rent/End/" + rentId)
                .header("Authorization", "Bearer " + jwtUser1)
                .param("lat", "55.7539939")
                .param("long", "37.6220930")
        ).andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("getting rent info by owner")
    void testShouldGetRentInfoToOwner() throws Exception {
        //region register 1'st user & his car, register 2nd user and create rent
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport.json");
        final List<Transport> allTransport = transportRepository.findAll();
        Transport transport = allTransport.get(0);
        Long transportId = transport.getId();
        Double transportDayPrice = transport.getDayPrice();

        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        createRent(jwtUser2, "1", "Days");
        final List<User> allUsers = userRepository.findAll();
        Long userId2 = allUsers.get(1).getId();

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
        Long rentId = allRents.get(0).getId();
        //endregion


        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/Rent/" + rentId)
                .header("Authorization", "Bearer " + jwtUser1)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportId").value(transportId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId2))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    JsonNode root = objectMapper.readTree(json);
                    LocalDateTime startingTime = LocalDateTime.parse(root.get("timeStart").asText());

                    Assertions.assertTrue(startingTime.isAfter(LocalDateTime.now().minusSeconds(5)));
                    Assertions.assertTrue(startingTime.isBefore(LocalDateTime.now().plusSeconds(5)));
                })
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeEnd").value((Object) null))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceOfUnit").value(transportDayPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceType").value("Days"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.finalPrice").value((Object) null));
    }

    @Test
    @DisplayName("getting rent info by renter")
    void testShouldGetRentInfoToRenter() throws Exception {
        //region register 1'st user & his car, register 2nd user and create rent
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport2.json");
        final List<Transport> allTransport = transportRepository.findAll();
        Transport transport = allTransport.get(0);
        Long transportId = transport.getId();
        Double transportMinutePrice = transport.getMinutePrice();

        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        createRent(jwtUser2, "1", "Minutes");
        final List<User> allUsers = userRepository.findAll();
        Long userId2 = allUsers.get(1).getId();

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
        Long rentId = allRents.get(0).getId();
        //endregion


        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/Rent/" + rentId)
                                .header("Authorization", "Bearer " + jwtUser2)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportId").value(transportId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(userId2))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    JsonNode root = objectMapper.readTree(json);
                    LocalDateTime startingTime = LocalDateTime.parse(root.get("timeStart").asText());

                    Assertions.assertTrue(startingTime.isAfter(LocalDateTime.now().minusSeconds(5)));
                    Assertions.assertTrue(startingTime.isBefore(LocalDateTime.now().plusSeconds(5)));
                })
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeEnd").value((Object) null))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceOfUnit").value(transportMinutePrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceType").value("Minutes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.finalPrice").value((Object) null));
    }

    @Test
    @DisplayName("making sure that others can't get rent info")
    void testShouldNotLetGetRentInfoToOthers() throws Exception {
        //region register 1'st user & his car, register 2nd user and create rent
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport.json");

        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        createRent(jwtUser2, "1", "Days");

        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(1, allRents.size());
        Long rentId = allRents.get(0).getId();
        //endregion

        String jwtUser3 = signUpAndSignInUser("user-controller/RequestRegistrationUser3.json", "user-controller/RequestSignInUser3.json");
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/Rent/" + rentId)
                                .header("Authorization", "Bearer " + jwtUser3)
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("making sure that only authorized user can get rent history")
    void testShouldNotLetGetRentHistoryToUnauthorized() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/Rent/MyHistory")
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("getting user rent history")
    void testShouldGetRentHistory() throws Exception {
          //region register 1'st user & his car, register 2nd user and create+end 2 rents
        String jwtUser1 = signUpAndSignInUser("user-controller/RequestRegistrationUser.json", "user-controller/RequestSignInUser.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport.json");
        registerTransport(jwtUser1, "transport-controller/RequestRegisterTransport2.json");

        String jwtUser2 = signUpAndSignInUser("user-controller/RequestRegistrationUser2.json", "user-controller/RequestSignInUser2.json");
        createRent(jwtUser2, "1", "Days");
        createRent(jwtUser2, "2", "Minutes");
        final List<Rent> allRents = rentRepository.findAll();
        Assertions.assertEquals(2, allRents.size());
        allRents.stream()
                .forEach(rent -> {
                    try {
                        endRent(jwtUser2, rent.getId());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        //endregion

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/Rent/MyHistory")
                        .header("Authorization", "Bearer " + jwtUser2)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andDo(result -> {
                   String json = result.getResponse().getContentAsString();
                   CommonUtils.printPrettyJson(json);
                });
    }
}
