package com.ri.generalFramework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;

public abstract class MathUtil {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 4;

    /**
     * 提供精确的加法运算。
     *
     * @param value_1 被加数
     * @param value_2 加数
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0， 则格式为"0.000",如100.123，保留4为小数的结果为100.123保留0为小数，格式为"#"
     * @return 两个参数的和
     */
    public static String add(double value_1, double value_2, String pattern) {
        BigDecimal b1 = new BigDecimal(Double.toString(value_1));
        BigDecimal b2 = new BigDecimal(Double.toString(value_2));
        return parseDoubleToStr(b1.add(b2).doubleValue(), pattern);
    }

    /**
     * 提供多个数的精确的加法运算。
     *
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0， 则格式为"0.000",如100.123，保留4为小数的结果为100.123保留0为小数，格式为"#"
     * @param values  多值
     * @return 多个参数的和
     */
    public static String addMultiNums(String pattern, double... values) {
        double value_1 = 0;
        for (double value : values) {
            BigDecimal b1 = new BigDecimal(Double.toString(value_1));
            BigDecimal b2 = new BigDecimal(Double.toString(value));
            value_1 = b1.add(b2).doubleValue();
        }
        return parseDoubleToStr(value_1, pattern);
    }

    /**
     * 多个double类型的数据想加不丢失精度
     *
     * @param values 多个double类型的数据
     * @return 结果
     */
    public static double addMultiNums(double... values) {
        double value_1 = 0;
        for (double value : values) {
            BigDecimal b1 = new BigDecimal(Double.toString(value_1));
            BigDecimal b2 = new BigDecimal(Double.toString(value));
            value_1 = b1.add(b2).doubleValue();
        }
        return value_1;
    }

    /**
     * 多个float类型的数据相加不丢失精度
     *
     * @param values 多个float类型的数据
     * @return 结果
     */
    public static float addMultiNums(float... values) {
        float value_1 = 0;
        for (float value : values) {
            BigDecimal b1 = new BigDecimal(Float.toString(value_1));
            BigDecimal b2 = new BigDecimal(Float.toString(value));
            value_1 = b1.add(b2).floatValue();
        }
        return value_1;
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1      被减数
     * @param v2      减数
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0， 则格式为"0.000",如100.123，保留4为小数的结果为100.123保留0为小数，格式为"#"
     * @return 两个参数的差
     */

    public static String sub(double v1, double v2, String pattern) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return parseDoubleToStr(b1.subtract(b2).doubleValue(), pattern);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1      被乘数
     * @param v2      乘数
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0， 则格式为"0.000",如100.123，保留4为小数的结果为100.123保留0为小数，格式为"#"
     * @return 两个参数的积
     */

    public static String mul(double v1, double v2, String pattern) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return parseDoubleToStr(b1.multiply(b2).doubleValue(), pattern);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后4位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */

    public static double div(double v1, double v2, int scale) {
        if (v2 == 0) {
            return 0;
        }
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a  positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 格式化数字
     *
     * @param number  操作数
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0， 则格式为"0.000",如100.123，保留4为小数的结果为100.123保留0为小数，格式为"#"
     * @return 返回字符串类型
     */
    public static String parseDoubleToStr(double number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    /**
     * 将小数转为百分数,如果number值为0 则返回 0 number不为0 则
     *
     * @param number  操作数
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0， 则格式为"0.000",如100.123，保留4为小数的结果为100.123保留0为小数，格式为"#"
     * @return String
     */
    public static String parseDoubleToPercent(double number, String pattern) {
        if (number == 0) {
            return "0";
        }
        return parseDoubleToStr(100 * number, pattern) + "%";
    }

    /**
     * 除法并转换为百分数
     * number1/number2，如果number2为0，则返回0；
     *
     * @param number1:被除数
     * @param number2：除数
     * @param pattern     保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                    保留4位小数，不够位不补0， 则格式为"0.000",如100.123，保留4为小数的结果为100.123保留0为小数，格式为"#"
     * @return 返回字符串类型
     */
    public static String divAndToPercent(double number1, double number2, String pattern) {
        if (number2 == 0) {
            return "0";
        }
        double quotient = div(number1, number2);
        return MathUtil.parseDoubleToPercent(quotient, pattern);
    }

    /**
     * Object 2 BigDecimal
     *
     * @param dou Object
     * @return BigDecimal
     */
    public static BigDecimal getBigDecimal(Object dou) {
        if (dou == null) {
            return new BigDecimal(0);
        }
        if (isNumber(dou)) {
            return new BigDecimal(dou.toString());
        } else {
            return new BigDecimal(0);
        }
    }

    /**
     * 判断某字符串是否是纯数字,如“12345”或“100.222”
     *
     * @param string 字符串
     * @return boolean
     */
    public static boolean isNumber(Object string) {
        String regex = "-?\\d+\\.?\\d*";
        String str = String.valueOf(string);
        if (str.endsWith(".")) {
            return false;
        }
        return str.matches(regex);
    }

    /**
     * 验证是否是“数字，数字，数字”格式，如“100,200” 或 “100.11,200” 等
     *
     * @param string 字符串
     * @return boolean
     */
    public static boolean isNumberSplitByComma(Object string) {

        String regex = "(-?\\d+\\.?\\d*)+(,-?\\d+\\.?\\d*)*";
        String str = String.valueOf(string);
        if (str.endsWith(".")) {
            return false;
        }
        return str.matches(regex);
    }

    /**
     * 判断某数字是否在范围之内
     *
     * @param current 判断数
     * @param min     下界
     * @param max     上界
     * @return boolean
     */
    public static boolean isBetween(double current, int min, int max) {
        return Math.max(min, current) == Math.min(current, max);
    }

    /**
     * 计算一个 list 中数据的平均值
     *
     * @param list Collection
     * @return 平均值
     */
    public static double average(Collection<Double> list) {
        int count = list.size();
        if (count == 0) {
            return 0;
        }
        double sum = 0;
        for (Double num : list) {
            sum += num;
        }
        return sum / count;
    }
}
