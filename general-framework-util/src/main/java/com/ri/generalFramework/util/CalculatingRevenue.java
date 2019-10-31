package com.ri.generalFramework.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatingRevenue {

    public void calculateAnnualizedRate() {

    }

    static void calculateIncome(String regEx, String value) {
//        System.out.println(regEx);
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(value);
        System.out.println(matcher.matches() + " 参数  " + value);
    }

    public static void main(String[] args) {
        String regEx1 = "([0-9]\\d*|0)(\\.\\d+)?,([0-9]\\d*|0)(\\.\\d+)?";
        String regEx2 = "([0-9]\\d*|0)(\\.\\d+)?(,([0-9]\\d*|0)(\\.\\d+)?){0,32}";
        String regEx3 = "[a-zA-Z\\d\\u4E00-\\u9FA5_\\.\\-%@\\*]+,[a-zA-Z\\d\\u4E00-\\u9FA5_\\.\\-%@\\*]+";
        String regEx4 = "[a-zA-Z\\d\\u4E00-\\u9FA5_\\.\\-%@\\*]+(,[a-zA-Z\\d\\u4E00-\\u9FA5_\\.\\-%@\\*]+[^,]){0,32}";
        String regEx5 = "\\d{4}(-[0-1]?\\d?){2}\\s[0-1]?\\d?(:[0-5]?\\d?){2}";
        String regEx6 = "\\d{4}(-[0-1]?\\d?){2}\\s[0-1]?\\d?(:[0-5]?\\d?){2},\\d{4}(-[0-1]?\\d?){2}\\s[0-1]?\\d?(:[0-5]?\\d?){2}";
        String regEx7 = "([0-9]\\d*|0)(\\.\\d+)?";
        String regEx8 = "[a-zA-Z\\d\\u4E00-\\u9FA5_\\.\\-%@\\*]+";
        String regEx9 = "[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*";
        String regEx10 = "[a-zA-Z\\d\\u4E00-\\u9FA5]{1,5}";
//        String value = "2019-3-6 11:11:11";
        String[] value = {
                "",
                "d",
                "1234",
                "asdfg",
                "123456",
                "",
        };
        for (String s : value) {
            calculateIncome(regEx10, s);
        }
    }
}
