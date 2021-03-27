package com.starling.interview.savingsgoal.service;

import com.starling.interview.savingsgoal.adapter.StarlingAdapterImpl;
import com.starling.interview.savingsgoal.dto.RoundUpResponse;
import com.starling.interview.savingsgoal.util.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("UT. RoundUpServiceImplTest.")
@ExtendWith(MockitoExtension.class)
class RoundUpServiceImplTest {

    @InjectMocks
    private RoundUpServiceImpl target;

    @Mock
    private StarlingAdapterImpl starlingAdapterImpl;
    @Mock
    private RoundUpResponseService roundUpResponseService;

    @DisplayName("GIVEN StarlingApiService and token WHEN roundUp THEN return roundup response list for all associated accounts")
    @Test
    void testRoundUp() {

        //given
        List<RoundUpResponse> expectedResponse = TestData.getRoundUpResponseList();
        String token = "TestToken";

        given(starlingAdapterImpl.getAccount(anyString())).willReturn(TestData.getAccounts());
        given(starlingAdapterImpl.getTransactionFeed(anyString(), anyString(), anyString())).willReturn(TestData.getFeedItems());
        given(starlingAdapterImpl.getSavingsGoal(anyString(), anyString())).willReturn(TestData.getSavingsGoal());
        given(starlingAdapterImpl.topUpSavingsGoal(anyString(), anyString(), anyString(), any())).willReturn("6f0bd7c5-8b85-4d61-8a40-ac413c46e0b3");
        given(roundUpResponseService.createResponsePerAccount(any(), anyString(), any(), anyString())).willReturn(TestData.getRoundUpResponse());
        //when

        List<RoundUpResponse> actualRoundUpResponses = target.roundUp(token);
        //then

        assertEquals(expectedResponse.get(0).getAccountUid(), actualRoundUpResponses.get(0).getAccountUid());
        assertEquals(expectedResponse.get(0).getSavingGoalName(), actualRoundUpResponses.get(0).getSavingGoalName());
        assertEquals(expectedResponse.get(0).getSaving().getMinorUnits(), actualRoundUpResponses.get(0).getSaving().getMinorUnits());
        then(starlingAdapterImpl).should().getAccount(anyString());

    }


}
