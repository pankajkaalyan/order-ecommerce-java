package com.order.ecommerce.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.order.ecommerce.entity.AuthenticationToken;
import com.order.ecommerce.repository.ITokenRepository;
import com.order.ecommerce.service.impl.AuthenticationService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@AutoConfigureEmbeddedDatabase
@AutoConfigureMockMvc
@Sql("/product/insert_prerequisite_records.sql")
public class ProductIntegrationTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private IAuthenticationService authenticationService;
    private static final String TOKEN = "abc3c9f7-f42a-4119-a974-6b341b00dae4";

    //@Test
    void testGetProduct() throws Exception {
        Mockito.doNothing().when(authenticationService).authenticateToken(anyString());
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products/106")
                                .param("token", TOKEN)
                )
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json("{\n" +
                        "  \"productId\": \"106\",\n" +
                        "  \"sku\": \"1006\",\n" +
                        "  \"title\": \"SoftDrink\",\n" +
                        "  \"description\": \"Coke\",\n" +
                        "  \"price\": 5.99,\n" +
                        "  \"productCategory\": \"PC1007\"\n" +
                        "}")).andReturn();
    }
}
