package com.ri.generalFramework.handle;

import com.ri.generalFramework.annotation.CustomizeCheck;

import java.lang.reflect.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 自定义检查注解处理器
 * 自定义检查注解 {@link CustomizeCheck}
 */
public final class CustomizeCheckHandle {

    public static boolean checkHasError(Object t) {
        return !check(t);
    }

    public static boolean checkHasError(Object t, List<String> errorInfo) {
        return !check(t, errorInfo);
    }

    public static boolean check(Object t) {
        return check(t, null);
    }

    public static boolean check(Object t, List<String> errorInfo) {
        Class inspection = t.getClass();
        Field[] fields = inspection.getDeclaredFields();
        boolean checkResult = true;
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.getType() == List.class) {
                    Type genericType = field.getGenericType();
                    if (genericType == null)
                        continue;
                    if (genericType instanceof ParameterizedType) {
                        //                    ParameterizedType type = (ParameterizedType) genericType;
                        //                    Class<?> genericClass = (Class<?>) type.getActualTypeArguments()[0];
                        if (field.get(t) == null)
                            continue;
                        Class clazz = field.get(t).getClass();
                        //获取到属性的值的Class对象
                        Method m = clazz.getDeclaredMethod("size");
                        //调用list的size方法，得到list的长度
                        int size = (Integer) m.invoke(field.get(t));
                        //遍历list，调用get方法，获取list中的对象实例
                        for (int i = 0; i < size; i++) {
                            Method getM = clazz.getDeclaredMethod("get", int.class);
                            if (!getM.isAccessible())
                                getM.setAccessible(true);
                            if (!check(getM.invoke(field.get(t), i), errorInfo))
                                checkResult = false;
                        }
                    }
                } else if (field.isAnnotationPresent(CustomizeCheck.class)) {
                    CustomizeCheck customizeCheck = field.getAnnotation(CustomizeCheck.class);
                    Object obj = field.get(t);
                    if (!customizeCheck.canNull() && obj == null) {
                        checkResult = false;
                        if (errorInfo != null) {
                            errorInfo.add(field.getName() + " : is null, ");
                        }
                    }
                    if (!matches(field.get(t) + "", customizeCheck.pattern())) {
                        checkResult = false;
                        if (errorInfo != null) {
                            errorInfo.add(field.getName() + " : " + customizeCheck.msg() + ", ");
                        }
                    }
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                checkResult = false;
                if (errorInfo != null) {
                    errorInfo.add(field.getName() + " : " + e.getMessage() + ", ");
                }
            }
        }
        return checkResult;
    }

    private static boolean matches(String content, String pattern) {
        if ("".equals(pattern)) {
            return false;
        }
        return Pattern.matches(pattern, content);
    }
}
