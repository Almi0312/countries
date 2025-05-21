package com.example.countries;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@ActiveProfiles("test")
@Transactional
class CountryControllerTest {

    private static final String countryRequest = "/api/country";

    @Autowired
    MockMvc mockMvc;

    @Test
    void addCountryTest() throws Exception {
        String json =
                """
                {
                    "title" : "Belarus",
                    "code" : "BS"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post(countryRequest + "/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Belarus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BS"));
    }

    @Test
    void editCountryTest() throws Exception {
        String json =
                """
                {
                    "id" : "c039d2e8-32ee-11f0-8968-0242ac110002", 
                    "title" : "Belarus",
                    "code" : "BS"
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.patch(countryRequest + "/edit")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Belarus"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BS"));
    }

    @Test
    void returnAllEndpointTest() throws Exception {
        String countries = mockMvc.perform(MockMvcRequestBuilders.get(countryRequest + "/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andReturn().getResponse().getContentAsString();
        assertTrue(countries.contains("\"title\":\"Fiji\",\"code\":\"FJ\""));
        assertTrue(countries.split("},\\{").length > 150);
    }
}
