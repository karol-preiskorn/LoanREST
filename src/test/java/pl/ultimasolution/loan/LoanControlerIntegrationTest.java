package pl.ultimasolution.loan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.ultimasolution.loan.controller.LoanRestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanControlerIntegrationTest {

    @Autowired
    private LoanRestController loanController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenUserControllerInjected_thenNotNull() throws Exception {
        assertThat(loanController).isNotNull();
    }

    @Test
    public void whenGetRequestToLoansEndPointWithId_thenCorrectResponse() throws Exception {
        mockMvc.perform(get("/api/loans/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.*", Matchers.hasSize(9)))
                .andExpect(jsonPath("$.id")
                        .value("1"));
    }


    @Test
    public void whenGetRequestToLoansSizeEndPoint_thenCorrectResponse() throws Exception {
        mockMvc.perform(get("/api/loans").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void whenGetRequestToLoanEndPointWithSelectedLoanRequestParameter_thenCorrectResponse() throws Exception {
        mockMvc.perform(get("/api/loans/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.historyLog", is(not(emptyString()))))
                .andExpect(jsonPath("$.termAt", is("2020-03-06T22:19:44.000+0000")))
                .andExpect(jsonPath("$.amount", is(999)))
                .andExpect(jsonPath("$.prolongedTerm", is(false)))
                .andExpect(jsonPath("$.createdAt", is("2020-02-06T22:19:44.000+0000")))
                .andExpect(jsonPath("$.loanName", is("Cabacki")));
    }

    @Test
    public void whenPutRequestLoanToProlongEndPoint_thenCorrectResponse() throws Exception {
        mockMvc.perform(put("/api/loans/prolong/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/loans/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prolongedTerm", is(true)));
    }

    @Test
    public void whenPutRequestLoanWithRiskyTime_thenBadResponse() throws Exception {
        String req = "{\"prolongedTerm\": false, \"loanName\": \"IBM\", \"ip\": \"127.0.0.1\", \"loanId\": 1, \"createdAt\": \"2020-02-06T01:19:44.000+0000\", \"termAt\": \"2020-03-06T22:19:44.000+0000\", \"amount\": 999 }";

        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(req))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanName").value("IBM"));
    }
}