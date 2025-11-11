package com.publicsapient.publicsapient;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.publicsapient.publicsapient.Controller.UserController;
import com.publicsapient.publicsapient.Payload.APIUserDTO;
import com.publicsapient.publicsapient.Payload.ResponseDTO;
import com.publicsapient.publicsapient.Service.UserServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
class PublicsapientApplicationTests {

	@Autowired
	private UserController userController;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
    private UserServiceImpl userService;

	@Test
    void testLoadDataSuccess() throws Exception {
        Mockito.when(userService.loadData())
                .thenReturn("Loaded Successfully");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/loadData"))
                .andExpect(status().isOk())
                .andExpect(content().string("Loaded Successfully"));
    }

    @Test
    public void testGetUserByIdSuccess() throws Exception {

        APIUserDTO userDTO = new APIUserDTO();
        userDTO.setId(1L);

        when(userService.findUserById(1L)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/id")
                .param("id", "1"))
                .andExpect(status().isFound());
    }


    @Test
    void testGetUserByEmailSuccess() throws Exception {
        APIUserDTO userDTO = new APIUserDTO();
        userDTO.setFirstName("Emily");
        userDTO.setEmail("emily.johnson@x.dummyjson.com");

        Mockito.when(userService.findUserByEmail("emily.johnson@x.dummyjson.com"))
                .thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/email")
                        .param("email", "emily.johnson@x.dummyjson.com"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.email").value("emily.johnson@x.dummyjson.com"));
    }

    @Test
    void testGetUserByEmailValidationFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/email")
                        .param("email", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
void testGetUsersByKeywordSuccess() throws Exception {
    ResponseDTO userDTO = new ResponseDTO();
    List<APIUserDTO> apiUserDTO = new ArrayList<>();

    apiUserDTO.add(new APIUserDTO(
            1, "Emily", "Johnson", "emily.johnson@x.dummyjson.com",
            "+81 965-431-3024", 28, "admin", "female"));

    userDTO.setContent(apiUserDTO);

    Mockito.when(userService.findByKeyword("Emi"))
            .thenReturn(userDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/user/keyword")
                    .param("keyword", "Emi"))
            .andExpect(status().isFound());
}



    @Test
    void testGetUsersByKeywordValidationFailure_Blank() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/keyword")
                        .param("keyword", ""))
                .andExpect(status().isBadRequest());
    }

}
