package com.edcarlitos.conctactapi;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.edcarlitos.conctactapi.dto.UserRegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    @RepeatedTest(5)
    @Transactional
    @Rollback
    public void successRegisterTest() throws Exception {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("Juanito","juan@tilin.com","1234");

        String dtoToJson = objectMapper.writeValueAsString(userRegisterDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoToJson))
                .andExpect(status().isCreated())
                .andReturn();
    }


    @RepeatedTest(5)
    @Transactional
    @Rollback
    public void userAlreadyExistByEmailTest() throws Exception {

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO("Juanito","juan@tilin.com","1234");

        String dtoToJson = objectMapper.writeValueAsString(userRegisterDTO);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson))
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoToJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"message\": \"El usuario ya existe\"}"))
                .andReturn();

    }

}
