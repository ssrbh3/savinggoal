package com.starling.interview.savingsgoal.service;

import com.starling.interview.savingsgoal.dto.Account;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;
import com.starling.interview.savingsgoal.dto.RoundUpResponse;
import org.springframework.stereotype.Service;

/**
 * Service class to build response , there is not a lot of service logic here. But it makes more
 * sense to keep a separate service class rather then cluttering existing service class, This way we can keep one class one responsibility.
 */
@Service
public class RoundUpResponseServiceImpl implements RoundUpResponseService {
    @Override
    public RoundUpResponse createResponsePerAccount(Account account, String savingGoalName, SavingsGoalTopUp savingsGoalTopUpPayload, String savingsGoalTransferId) {
        return new RoundUpResponse(account.getAccountUid(), savingGoalName, savingsGoalTopUpPayload.getAmount(), savingsGoalTransferId);
    }
}
