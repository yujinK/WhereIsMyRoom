package com.example.yujin.whereismyroom.common;

import com.example.yujin.whereismyroom.R;

public class Util {
    /**
     * 금액 단위 String 처리
     * @param deposit
     * @return
     */
    public static String calDeposit(String deposit) {
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

    /**
     * String 배열을 String 으로 변환
     * @param array
     * @return
     */
    public static String convertArrayToString(String[] array) {
        String str = "";

        for (int i=0; i<array.length; i++) {
            str += array[i];

            if (i < array.length-1) {
                str += ",";
            }
        }

        return str;
    }


    /**
     * String 을 String 배열로 변환
     * @param str
     * @return
     */
    public static String[] convertStringToArray(String str) {
        String[] array = str.split(",");
        return array;
    }


    /**
     * 지하철 노선 ImageResource 가져오기
     * @param routeName
     * @return
     */
    public static int findRouteImageResource(String routeName) {
        switch (routeName) {
            //수도권
            case "서울 1호선":
                return R.drawable.seoul1;
            case "서울 2호선":
                return R.drawable.seoul2;
            case "서울 3호선":
                return R.drawable.seoul3;
            case "서울 4호선":
                return R.drawable.seoul4;
            case "서울 5호선":
                return R.drawable.seoul5;
            case "서울 6호선":
                return R.drawable.seoul6;
            case "서울 7호선":
                return R.drawable.seoul7;
            case "서울 8호선":
                return R.drawable.seoul8;
            case "서울 9호선":
                return R.drawable.seoul9;
            case "인천 1호선":
                return R.drawable.incheon1;
            case "인천 2호선":
                return R.drawable.incheon2;
            case "공항철도":
                return R.drawable.airport;
            case "자기부상열차":
                return R.drawable.maglev;
            case "경춘선":
                return R.drawable.kyeongchun;
            case "분당선":
                return R.drawable.bundang;
            case "에버라인":
                return R.drawable.yongin_ever;
            case "신분당선":
                return R.drawable.shinbundang;
            case "수인선":
                return R.drawable.suin;
            case "경강선":
                return R.drawable.gyeonggang;
            case "우이신설선":
                return R.drawable.uisinseol;

            //부산
            case "부산 1호선":
                return R.drawable.busan1;
            case "부산 2호선":
                return R.drawable.busan2;
            case "부산 3호선":
                return R.drawable.busan3;
            case "부산 4호선":
                return R.drawable.busan4;
            case "부산김해경전철":
                return R.drawable.busan_ginhae;

            //대구
            case "대구 1호선":
                return R.drawable.daegu1;
            case "대구 2호선":
                return R.drawable.daegu2;
            case "대구 3호선":
                return R.drawable.daegu3;

            //광주
            case "광주 1호선":
                return R.drawable.gwangju1;

            //대전
            case "대전 1호선":
                return R.drawable.daejeon1;

            default:
                return 0;
        }
    }
}
