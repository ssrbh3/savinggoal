package com.starling.interview.savingsgoal.adapter;

import com.starling.interview.savingsgoal.dto.Accounts;
import com.starling.interview.savingsgoal.dto.FeedItems;
import com.starling.interview.savingsgoal.dto.SavingsGoal;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;

public interface StarlingAdapter {
    Accounts getAccount(String token);

    FeedItems getTransactionFeed(String token, String accountID, String category);

    SavingsGoal getSavingsGoal(String token, String accountID);

    String topUpSavingsGoal(String token, String accountID, String savingsGoalId, SavingsGoalTopUp savingsGoalTopUp);
}
