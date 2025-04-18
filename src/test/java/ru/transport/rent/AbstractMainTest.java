package ru.transport.rent;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.transport.rent.testservices.DropDbTestService;

import lombok.Getter;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractMainTest {

    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresqlContainer;


    /**
     * Web application context.
     */
    @Autowired
    protected WebApplicationContext ctx;

    @Autowired
    protected DropDbTestService dropDbTestService;

    /**
     * Mock mvc.
     */
    protected MockMvc mockMvc;

    /**
     * Object mapper.
     */
    @Autowired
    @Getter
    private ObjectMapper mapper;


    /**
     * Create mock mvc.
     */
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(ctx)
                .build();
    }


    @AfterEach
    public void tearDown() {
        dropDbTestService.dropAll();
    }


    static {
        postgresqlContainer = new PostgreSQLContainer("postgres:16-bookworm");
        postgresqlContainer.start();
    }
}
