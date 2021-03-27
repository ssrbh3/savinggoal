package com.starling.interview.savingsgoal.service;

import com.starling.interview.savingsgoal.dto.Account;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;
import com.starling.interview.savingsgoal.dto.RoundUpResponse;

public interface RoundUpResponseService {
    RoundUpResponse createResponsePerAccount(Account account, String savingGoalName, SavingsGoalTopUp savingsGoalTopUpPayload, String savingsGoalTransferId);
}
