package com.starling.interview.savingsgoal.service;

import com.starling.interview.savingsgoal.dto.Account;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;
import com.starling.interview.savingsgoal.dto.RoundUpResponse;
import com.starling.interview.savingsgoal.util.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UT. RoundUpResponseServiceImplTest.")
@ExtendWith(MockitoExtension.class)
class RoundUpResponseServiceImplTest {

    @InjectMocks
    private RoundUpResponseServiceImpl target;

    @DisplayName("GIVEN account, savingsGoalTopUp, savingGoalName, savingsGoalTransferId WHEN createResponsePerAccount THEN create RoundUpResponse ")
    @Test
    void testCreateResponsePerAccount() {

        //given
        Account account = TestData.getAccount();
        SavingsGoalTopUp savingsGoalTopUp = TestData.getSavingsGoalTopUp();
        String savingGoalName = "Trip To India";
        String savingsGoalTransferId = "8d822cce-9c37-4daf-94c1-66b959bbf646";

        //when
        RoundUpResponse responsePerAccount = target.createResponsePerAccount(account, savingGoalName, savingsGoalTopUp, savingsGoalTransferId);

        //then
        assertEquals(savingGoalName, responsePerAccount.getSavingGoalName());
        assertEquals(savingsGoalTransferId, responsePerAccount.getTransferUid());
        assertEquals(account.getAccountUid(), responsePerAccount.getAccountUid());
    }


}
