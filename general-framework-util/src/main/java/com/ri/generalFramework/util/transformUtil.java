package com.ri.generalFramework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * 工具类
 *
 * @author cjx
 */
public class transformUtil {

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
     * 将对象转化为boolean，如果对象为null，则返回false
     *
     * @param obj 对象
     * @return boolean
     */
    public static boolean getBooleanOfObj(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return Boolean.parseBoolean(obj.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将对象转化为short，如果不能转化，则返回0
     *
     * @param obj 对象
     * @return int
     */
    public static Short getShortOfObj(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        try {
            return (short) Math.round(Float.parseFloat(obj.toString().trim()));
        } catch (Exception e) {
            //logger.error("正确数据类型应为java.lang.Integer_实际接收数据为_"+e.getMessage());
        }
        return 0;
    }

    /**
     * 将对象转化为int，如果不能转化，则返回0
     * 原有方法getIntOfObj只能返回返回最接近的 int数，当参数超过7位（及超过float的精度时）超过的位数的值是不可控的（如：老方法会将20170705转为20170704）
     * 新方法采用四舍五入来确保值的准确（因为java中浮点数或是双精度浮点数无法用十进制来精确表示，如2.4表示是2.3999999999999999
     *
     * @param obj 对象
     * @return int
     * ）
     */
    public static int getIntOfObj(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        }
        try {
            BigDecimal bigDecimal = new BigDecimal(Double.valueOf(obj.toString().trim()).toString());
            return bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        } catch (Exception e) {
            //logger.error("正确数据类型应为java.lang.Integer_实际接收数据为_"+e.getMessage());
        }
        return 0;
    }

    public static double getDoubleOfObj(Object dou) {
        if (dou == null || dou.equals("")) {
            return 0;
        }
        if (dou instanceof Double) {
            return (Double) dou;
        }
        if (isNumber(dou)) {
            return Double.parseDouble(dou.toString().trim());
        }
        return 0;
    }

    public static float getFloatOfObj(Object flo) {
        if (flo == null || flo.equals("")) {
            return 0f;
        }
        if (flo instanceof Float) {
            return (Float) flo;
        }
        if (isNumber(flo)) {
            return Float.parseFloat(flo.toString());
        }
        return 0f;
    }

    public static BigDecimal getBigDecimal(Object dou) {
        if (dou == null) {
            return new BigDecimal(0);
        }
        if (isNumber(dou)) {
            return new BigDecimal(dou.toString().trim());
        } else {
            return new BigDecimal(0);
        }
    }

    public static BigDecimal getBigDecimal(double param) {
        return new BigDecimal(String.valueOf(param));
    }

    /**
     * 将对象转化为long，如果转化不了则返回0
     *
     * @param obj 对象
     * @return Long
     */
    public static Long getLongOfObj(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0L;
        }
        try {
            return Math.round(Double.parseDouble(obj.toString()));
        } catch (Exception e) {
            //logger.error("正确数据类型应为java.lang.Long_实际接收数据为_"+e.getMessage());
        }
        return 0L;
    }

    /**
     * 将big转化为String，当double为0.0时返回""
     *
     * @param dou double
     * @return String
     */
    public static String getStrOfZeroDouble(double dou) {
        if (dou == 0.0) {
            return "";
        } else {
            return Double.toString(dou);
        }
    }

    /**
     * 获取百分比，并按指定精度返回值
     */
    public static double getPercentOfNumber(double son, double father, String precision) {
        if (father == 0) {
            return 0;
        }
        return getDoubleOfObj(new DecimalFormat(precision).format(son / father));
    }


    /**
     * 格式化数字
     *
     * @param number  数字
     * @param pattern 保留小数点格式
     * @return 返回double类型
     */
    public static double parseDoubleToDouble(double number, String pattern) {

        DecimalFormat df = new DecimalFormat(pattern);
        return Double.parseDouble(df.format(number));
    }

    /**
     * 格式化数字
     *
     * @param number  数字
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0，则格式为"0.000",如100.123，保留4为小数的结果为100.123
     *                保留0为小数，格式为"#"
     * @return 返回字符串类型
     */
    public static String parseDoubleToStr(double number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    /**
     * 先将dou类型转化为BigDecimal类型，然后按pattern再格式化
     *
     * @param dou     double
     * @param pattern 保留小数点格式，如保留4位小数，不够位则补0，则格式为"#.####",如100.123，保留4为小数的结果为100.1230
     *                保留4位小数，不够位不补0，则格式为"0.000",如100.123，保留4为小数的结果为100.123
     *                保留0为小数，格式为"#"
     * @return 返回字符串类型
     * @author jc
     */
    public static String parseBigDecimalFromDouToStr(double dou, String pattern) {
        BigDecimal bd = (new BigDecimal(dou));
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(bd);
    }

    /**
     * 将百分数转化为小数，例如：0.12%转化为0.0012。
     *
     * @param s 百分数
     * @return double
     */
    public static double parsePercentToDouble(String s) {
        int index = s.indexOf("%");
        if (index == s.length() - 1) {
            return transformUtil.getDoubleOfObj(s.substring(0, index)) / 100;
        }
        return transformUtil.getDoubleOfObj(s);
    }


    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}


