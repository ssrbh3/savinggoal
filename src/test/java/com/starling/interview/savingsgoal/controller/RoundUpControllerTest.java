package com.starling.interview.savingsgoal.controller;

import com.starling.interview.savingsgoal.exception.RoundUpException;
import com.starling.interview.savingsgoal.dto.RoundUpResponse;
import com.starling.interview.savingsgoal.service.RoundUpServiceImpl;
import com.starling.interview.savingsgoal.util.TestData;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("UT. RoundUpControllerTest.")
@WebMvcTest(RoundUpController.class)
class RoundUpControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private RoundUpServiceImpl roundUpServiceImpl;

    @DisplayName("GIVEN Bearer token , account , feed and savings goals exist WHEN roundUp THEN return roundup response list for all associated accounts")
    @Test
    void testRoundUp_200() throws Exception {

        //given
        List<RoundUpResponse> expectedResponse = TestData.getRoundUpResponseList();
        given(roundUpServiceImpl.roundUp(anyString())).willReturn(expectedResponse);

        //when

        mvc.perform(get("/round-up").header("Authorization", "Bearer eycding...."))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].accountUid", Matchers.is("8b057e16-6c1f-4099-9547-c35b8dac3c93")))
                .andExpect(jsonPath("$[0].transferUid", Matchers.is("8d822cce-9c37-4daf-94c1-66b959bbf646")));
        //then
        then(roundUpServiceImpl).should().roundUp(anyString());

    }

    @DisplayName("GIVEN Bearer token and missing account WHEN roundUp THEN status 400")
    @Test
    void testRoundUp_400() throws Exception {

        //given
        given(roundUpServiceImpl.roundUp(anyString())).willThrow(new RoundUpException("No account available for this customer"));

        //when

        mvc.perform(get("/round-up").header("Authorization", "Bearer eycding...."))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
        //then
        then(roundUpServiceImpl).should().roundUp(anyString());

    }

    @DisplayName("GIVEN empty Token WHEN roundUp THEN status 500")
    @Test
    void testRoundUp_500() throws Exception {

        //when //then
        mvc.perform(get("/round-up"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError());
    }

}
