package com.starling.interview.savingsgoal;

import com.starling.interview.savingsgoal.util.TestData;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Disabling to avoid test failure in case of token expiry")
@DisplayName("IT. ApplicationTest")
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationIT {

    @Autowired
    private MockMvc mvc;


    @DisplayName("Integration Test for Application")
    @Test
    void testRoundUp() throws Exception {

        mvc.perform(get("/round-up").header("Authorization", TestData.token))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].accountUid", Matchers.is("498ee14e-f826-4cfd-95b4-947566e76c5b")))
                .andExpect(jsonPath("$[0].savingGoalName", Matchers.is("Disneyland Paris")))
                .andExpect(jsonPath("$[1].accountUid", Matchers.is("f1de956e-2aae-4d39-890e-b000f9fbf583")))
                .andExpect(jsonPath("$[1].savingGoalName", Matchers.is("Disneyland Cali")))
                .andExpect(status().is2xxSuccessful());
    }

}
