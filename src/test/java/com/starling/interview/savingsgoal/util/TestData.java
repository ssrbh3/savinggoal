package com.starling.interview.savingsgoal.util;

import com.starling.interview.savingsgoal.dto.Account;
import com.starling.interview.savingsgoal.dto.Accounts;
import com.starling.interview.savingsgoal.dto.CurrencyAndAmount;
import com.starling.interview.savingsgoal.dto.FeedItem;
import com.starling.interview.savingsgoal.dto.FeedItems;
import com.starling.interview.savingsgoal.dto.SavingsGoal;
import com.starling.interview.savingsgoal.dto.SavingsGoalTopUp;
import com.starling.interview.savingsgoal.dto.SavingsGoalsList;
import com.starling.interview.savingsgoal.dto.RoundUpResponse;

import java.util.ArrayList;
import java.util.List;


public class TestData {

    //will fail if the token expires
    public static String token = "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAH1Ty46cMBD8lRXn9YoF87zllh_IBzTu9ow1YCPbbLKK8u8x2AzDZJUbVdXdrrKb35lyLuszmBVDmsyb82BHpS8D6NubMFP2mrllCBUldEXVyZzJDgfGZTEwaIuGDS0njnVeDw0PxfRrzvr3-r1qOc_z7jVT4CPRVE29EiCEWbT_bkYk-0NhmI2Ut3UnOON5VTBedwMb8pZYhUiABSCCCLO9uZGOHdS22AjgLA-doWOoQkcDrJR1XsiiFB3K0BFifROCnItdUg5IpShZ21Ul44iSAXHJhvcGO-S8LKlcAwsz03op0Sm7blaZhol6Gwy9PAn-c34SFJL2SiqyZ35Uzp-YBBBtMNkTKn8HUfEexHWie-WBf1rl6QUWfzVWufBkTGlUHwoXGGPxACNokawJsMiE0d6aMR60MkkzWio7gVdGMyOZXDS6u-Tup-8gHi0W5820R6QJVBo8UjCiLz3M8_h5R1vVBBrBU480Uhixw6TZG_k1yGxJkqXg3f1PijaiNo8gKNyAp4vdcjw2_iumVrLiCnu6iTwEN9CLADc14S3UDJ9EuxRBChHBUcTUBJeUKWrHJ_MWtANxOAw0G5bx1u8vSQd1nBbxcWDE-4B1H8JuTcofM0cjwh48TNgIZtaFeGZTlzVSjbv9mOdEbVWWBKnZn4A7S_FyHXyEB3PsYg4fJy6lOXHbnEcmXlh48q9GHOIXsw4xDhVXwmUkZOnCEk3eh4DLnOAM-w8TP1MWD9sSM2PxwcqZ3T2c2TjLh_rtlYT7eKZmlIlaBidsuMF1OfZpj9xW9bhB2yM9r1T25y-koXZe1gUAAA.QnoKcbgDjLNk2ALVyUH27WGtGloRKlkl3DKm6-SNl3K96mU9xiz-RodFE0lsZMZKP8U9R8soRrG56C5ikC-lEyOx5vzkvVn500vmgnO4QWhl1t6cZ2GmowcbrFRyVS4hCxQFThtzqY8IWzZ-bCo8mocg-7NVbKk-WkBcLYoLphvPgFcibdUlL-WEabG8Y7EmcDH8rwd60F_KprZOZqOCyKHdjJZxqDWiQgPUrNWEVFefNtG2R44RY00UilmNsiEti1zjUO9CybIXLMqlAlLSCFGIp6yijCPkdz_ZediBrM_gFresyIxr1Axcpz-tg-FGRulZEa9vsIfOzATfoGyo9OzOxnmwNhYPeC-NdkIu2IfU2mP-WTyJELKNgwEbP37xWqPhCugIRio_01qdaWjn9MHwo7hm1ewcZFlQuP7WaX3PXLGM2mjlZKLAMPdhOqZ_2UERX_DpZrR3CXe0iYdKVuIPnIJ4rH-GbngxO2-6SFMUacS0zKUli33OoA_I_ZuzxAgouAw_o-DyuzyYbH7PCc2mAQ72FECJAJs2A9C6R4UMLEiMuuVe5qTqIVYpa6XtR98hbVToaCpX8wrqzEXH_pWjDuqtEk_h_sw1taOzULEh55VLtiYahI9hvyE3jYVzUW_q4mxC0XloGUxmk0nk-usgg4qzaSL3HX9LYLHJgJ8";

    public static RoundUpResponse getRoundUpResponse() {
        RoundUpResponse response = new RoundUpResponse();
        response.setSaving(getCurrencyAndAmount());
        response.setAccountUid("8b057e16-6c1f-4099-9547-c35b8dac3c93");
        response.setSavingGoalName("Trip To India");
        response.setTransferUid("8d822cce-9c37-4daf-94c1-66b959bbf646");
        return response;
    }

    public static List<RoundUpResponse> getRoundUpResponseList() {
        List<RoundUpResponse> roundUpResponseList = new ArrayList<>();
        roundUpResponseList.add(getRoundUpResponse());
        return roundUpResponseList;
    }

    public static CurrencyAndAmount getCurrencyAndAmount() {
        CurrencyAndAmount currencyAndAmount = new CurrencyAndAmount();
        currencyAndAmount.setCurrency("GBP");
        currencyAndAmount.setMinorUnits(120L);
        return currencyAndAmount;
    }

    public static Account getAccount() {

        Account account = new Account();
        account.setAccountUid("8b057e16-6c1f-4099-9547-c35b8dac3c93");
        account.setAccountType("PRIMARY");
        account.setCurrency("GBP");
        account.setDefaultCategory("23a6f510-2e9a-4e59-9c95-4ec5824f4bf4");
        return account;
    }

    public static Accounts getAccounts() {

        Accounts accounts = new Accounts();
        List<Account> accountList = new ArrayList<>();
        accountList.add(getAccount());
        accounts.setAccounts(accountList);
        return accounts;

    }

    public static Accounts getEmptyAccounts() {

        Accounts accounts = new Accounts();
        List<Account> accountList = new ArrayList<>();
        accounts.setAccounts(accountList);
        return accounts;

    }

    public static FeedItem getFeedItem() {
        FeedItem feedItem = new FeedItem();
        feedItem.setAmount(getCurrencyAndAmount());
        feedItem.setCategoryUid("23a6f510-2e9a-4e59-9c95-4ec5824f4bf4");
        feedItem.setCounterPartyUid("ed8fbce6-1141-4418-b1f5-4451f9fced4c");
        feedItem.setDirection("OUT");
        return feedItem;
    }

    public static FeedItems getFeedItems() {
        FeedItems feedItems = new FeedItems();
        List<FeedItem> feedItemList = new ArrayList<>();
        feedItemList.add(getFeedItem());
        feedItems.setFeedItems(feedItemList);
        return feedItems;
    }

    public static FeedItems getEmptyFeedItems() {
        FeedItems feedItems = new FeedItems();
        List<FeedItem> feedItemList = new ArrayList<>();
        feedItems.setFeedItems(feedItemList);
        return feedItems;
    }

    public static SavingsGoal getSavingsGoal() {
        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setSavingsGoalUid("ed8fbce6-1141-4418-b1f5-4451f9fced4c");
        savingsGoal.setName("Trip To India");
        savingsGoal.setTarget(getCurrencyAndAmount());
        savingsGoal.setTotalSaved(getCurrencyAndAmount());
        return savingsGoal;
    }

    public static SavingsGoalsList getSavingsGoalList() {
        SavingsGoalsList savingsGoalsList = new SavingsGoalsList();
        List<SavingsGoal> savingsGoals = new ArrayList<>();
        savingsGoals.add(getSavingsGoal());
        savingsGoalsList.setSavingsGoalList(savingsGoals);
        return savingsGoalsList;
    }

    public static SavingsGoalsList getEmptySavingsGoalList() {
        SavingsGoalsList savingsGoalsList = new SavingsGoalsList();
        List<SavingsGoal> savingsGoals = new ArrayList<>();
        savingsGoalsList.setSavingsGoalList(savingsGoals);
        return savingsGoalsList;
    }

    public static SavingsGoalTopUp getSavingsGoalTopUp() {
        return new SavingsGoalTopUp(getCurrencyAndAmount());
    }
}
