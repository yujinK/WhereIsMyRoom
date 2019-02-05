package com.example.yujin.whereismyroom.common;

public class Util {
    /**
     * 금액 단위 String 처리
     * @param deposit
     * @return
     */
    public String calDeposit(String deposit) {
        String result = "";

        if (deposit.length() > 4) {
            result += deposit.substring(0, deposit.length()-4) + "억 ";

            if (!deposit.substring(deposit.length()-4).equals("0000")) {
                result += deposit.substring(deposit.length()-4);
            }
        } else {
            result = deposit;
        }

        return result;
    }
}
