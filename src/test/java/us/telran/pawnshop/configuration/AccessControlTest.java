package us.telran.pawnshop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import us.telran.pawnshop.dto.ClientRealCreationRequest;
import us.telran.pawnshop.dto.ManagerCreationRequest;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.repository.ClientRepository;
import us.telran.pawnshop.service.impl.ClientServiceImpl;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static us.telran.pawnshop.entity.enums.ManagerStatus.EXPERT_APPRAISER;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccessControlTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BranchConfig branchConfig;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private ClientServiceImpl clientService;

    @BeforeEach
    public void setUp() {

        Client client = new Client();
        client.setClientId(2L);
        client.setFirstName("FirstName");
        client.setLastName("LastName");
        client.setDateOfBirth(LocalDate.of(1990, 1, 1));
        client.setEmail("email@email.com");
        client.setAddress("Address");

        clientRepository.save(client);
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldBeGrantedForManagerToGet() throws Exception {
        mockMvc.perform(get("/pawn-shop/managers/show"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldBeDeniedForManagerDoSomePost() throws Exception {
        mockMvc.perform(post("/pawn-shop/managers/new"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldBeGrantedManagerToModify() throws Exception {
        mockMvc.perform(put("/pawn-shop/clients/update/2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "DIRECTOR")
    void shouldBeDeniedForManagerToAllResources() throws Exception {
        mockMvc.perform(get("/pawn-shop/cash-operations/show"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "DIRECTOR")
    void shouldBeGrantedForDirectorMakePost() throws Exception {
        ManagerCreationRequest managerCreationRequest = new ManagerCreationRequest();

        managerCreationRequest.setFirstName("Joe");
        managerCreationRequest.setLastName("Alvarez");
        managerCreationRequest.setEmail("joe.alvarez@example.com");
        managerCreationRequest.setPassword("Joe.Alvarez");
        managerCreationRequest.setManagerStatus(EXPERT_APPRAISER);

        mockMvc.perform(post("/pawn-shop/managers/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(managerCreationRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void shouldBeGrantedForManagerToPost() throws Exception {
        ClientRealCreationRequest request = new ClientRealCreationRequest();

        request.setFirstName("John");
        request.setLastName("Smith");
        request.setDateOfBirth(LocalDate.of(1993, Month.JULY, 13));
        request.setEmail("email@email.com");
        request.setAddress("Address");

        mockMvc.perform(post("/pawn-shop/clients/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isOk());
    }

}

