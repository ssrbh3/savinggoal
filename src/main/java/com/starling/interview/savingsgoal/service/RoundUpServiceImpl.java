package com.starling.interview.savingsgoal.service;

import com.starling.interview.savingsgoal.adapter.StarlingAdapter;
import com.starling.interview.savingsgoal.dto.Account;
import com.starling.interview.savingsgoal.dto.Accounts;
import com.starling.interview.savingsgoal.dto.CurrencyAndAmount;
import com.starling.interview.savingsgoal.dto.FeedItem;
import com.starling.interview.savingsgoal.dto.FeedItems;
import com.starling.interview.savingsgoal.dto.SavingsGoal;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;
import com.starling.interview.savingsgoal.dto.RoundUpResponse;
import com.starling.interview.savingsgoal.util.RoundUpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoundUpServiceImpl implements RoundUpService {

    private final StarlingAdapter starlingAdapter;
    private final RoundUpResponseService roundUpResponseService;

    /**
     * Calculations will be done per account in case of multiple accounts. All results will then be added to response list.
     */

    @Override
    public List<RoundUpResponse> roundUp(String token) {

        final Accounts accounts = getAccounts(token);
        final List<Account> accountList = accounts.getAccounts();
        return accountList.stream().map(account -> roundUpHelper(token, account)).collect(Collectors.toList());

    }

    /*
         1. Get Txn feed per account
         2. Get Saving goal per account, Assumption - In case of multiple goals per account the first one would be taken,
         as there might be ambiguity in picking up goal and there is no customer interaction.
         3. Calculate Spare Change
         4. Create Payload for top up
         5. Top Up saving goal
         6. Create Response and Add response to the response list.
        */

    private RoundUpResponse roundUpHelper(String token, Account account) {
        final FeedItems transactionFeed = getTransactionFeed(token, account);
        final SavingsGoal savingsGoal = getSavingsGoal(token, account);
        final Long spareChange = calculateSpareChange(transactionFeed, savingsGoal); // ASSUMPTION :-  passing savingsGoal to calculateSpareChange to put a logic in place to exclude goal savings transfers (Direction OUT) from being calculated and round up in case multiple invocations are made subsequently. As they are not spending but a txn towards goal. Otherwise they will be calculated as an outgoing txnresulting in new roundup.
        final SavingsGoalTopUp savingsGoalTopUpPayload = createTopUpPayload(account, spareChange);
        final String savingsGoalTransferId = topUpSavingsGoal(token, account, savingsGoal, savingsGoalTopUpPayload);
        return roundUpResponseService.createResponsePerAccount(account, savingsGoal.getName(), savingsGoalTopUpPayload, savingsGoalTransferId);
    }


    /**
     * Get all the accounts associated with user token by calling Starling Api
     */

    private Accounts getAccounts(String token) {
        return starlingAdapter.getAccount(token);
    }

    /**
     * Get Transaction feed per account by calling Starling Api
     */

    private FeedItems getTransactionFeed(String token, Account account) {
        return starlingAdapter.getTransactionFeed(token, account.getAccountUid(), account.getDefaultCategory());
    }

    /**
     * Calculate spare change for outgoing transactions.
     * Only transactions with direction "OUT" are considered as specified in assignment (Spending).
     * NOTE/ASSUMPTION :- !feedItem.getCounterPartyUid().equals(savingsGoal.savingsGoalUid) logic in place to exclude goal savings transfers (Direction 'OUT') from being considered
     * as transaction feeds in case multiple invocations are made over and over again. As they are not spending but a txn towards saving goal.
     */

    private Long calculateSpareChange(FeedItems feedItems, SavingsGoal savingsGoal) {

        List<Long> spendingList = feedItems.getFeedItems()
                .stream().filter(feedItem -> feedItem.getDirection().equals("OUT") && !feedItem.getCounterPartyUid().equals(savingsGoal.getSavingsGoalUid()))
                .map(FeedItem::getAmount)
                .map(CurrencyAndAmount::getMinorUnits).collect(Collectors.toList());
        return RoundUpUtil.getSpareChangeSum(spendingList);
    }

    /**
     * find savings goal for an account by calling Starling Api.
     */

    private SavingsGoal getSavingsGoal(String token, Account account) {
        return starlingAdapter.getSavingsGoal(token, account.getAccountUid());
    }

    /**
     * Create Top Up Payload.
     */

    private SavingsGoalTopUp createTopUpPayload(Account account, Long spareChange) {
        final CurrencyAndAmount currencyAndAmount = new CurrencyAndAmount(account.getCurrency(), spareChange);
        return new SavingsGoalTopUp(currencyAndAmount);
    }

    /**
     * Create Top up payload for Savings Goal by calling Starling Api
     */

    private String topUpSavingsGoal(String token, Account account, SavingsGoal savingsGoal, SavingsGoalTopUp savingsGoalTopUp) {
        return starlingAdapter.topUpSavingsGoal(token, account.getAccountUid(), savingsGoal.getSavingsGoalUid(), savingsGoalTopUp);
    }

}
