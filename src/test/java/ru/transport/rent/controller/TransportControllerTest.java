package ru.transport.rent.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.hamcrest.Matchers;
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
import ru.transport.rent.entity.Transport;
import ru.transport.rent.repository.TransportRepository;
import static org.assertj.core.api.Assertions.assertThat;

class TransportControllerTest extends AbstractMainTest {

    @Autowired
    private TransportRepository transportRepository;

    @Test
    @DisplayName("registration transport")
    void testShouldRegisterTransport() throws Exception{
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

        final String jsonFromResource = CommonUtils
                .getJsonFromResource("transport-controller/RequestRegisterTransport.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Transport")
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFromResource)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        final List<Transport> all = transportRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    @DisplayName("registration transport with invalid type should return BadRequest")
    void testShouldFailOnInvalidTransportType() throws Exception {
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

        final String invalidTransportJson = CommonUtils
                .getJsonFromResource("transport-controller/RequestRegisterTransportWithInvalidType.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Transport")
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(invalidTransportJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        final List<Transport> all = transportRepository.findAll();
        Assertions.assertTrue(all.isEmpty(), "Transport should not be saved with invalid type");
    }

    @Test
    @DisplayName("get transport details by id")
    public void testShouldReturnTransportDetailsById() throws Exception {
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

        final String jsonFromResource = CommonUtils
                .getJsonFromResource("transport-controller/RequestRegisterTransport.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/Transport")
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFromResource)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());





        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/Transport/1")
                                .header("Authorization", "Bearer " + jwt))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ownerUsername").value("username"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transportType").value("Car"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Toyota"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value("White"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.identifier").value("om777j"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("A white toyota with license plate number om777j"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.latitude").value(Matchers.closeTo(53.225775091771084, 0.0000001)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.longitude").value(Matchers.closeTo(50.19476734475567, 0.0000001)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.minutePrice").value(15.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dayPrice").value(3500.0));
    }

    @Test
    @DisplayName("change transport by id")
    public void testShouldChangeTransportById() throws Exception {
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

        final String transportRegistrationJson = CommonUtils
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
//endregion

        final String newTransportDetailsJson = CommonUtils
                .getJsonFromResource("transport-controller/RequestChangeTransport.json");

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/Transport/1")
                                .header("Authorization", "Bearer " + jwt)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newTransportDetailsJson)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());

        Transport transportAfter = transportRepository.findById(1L).orElseThrow(() -> new NoSuchElementException());

        assertThat(transportAfter.getCanBeRented()).isFalse();
        assertThat(transportAfter.getModel()).isEqualTo("Nissan");
        assertThat(transportAfter.getColor()).isEqualTo("Black");
        assertThat(transportAfter.getIdentifier()).isEqualTo("om555j");
        assertThat(transportAfter.getDescription()).isEqualTo("Black nissan that was toyota");
        assertThat(transportAfter.getLatitude()).isEqualTo(55.753352512463);
        assertThat(transportAfter.getLongitude()).isEqualTo(37.582597690419);
        assertThat(transportAfter.getMinutePrice()).isEqualTo(16.0);
        assertThat(transportAfter.getDayPrice()).isEqualTo(4000.0);

    }
}