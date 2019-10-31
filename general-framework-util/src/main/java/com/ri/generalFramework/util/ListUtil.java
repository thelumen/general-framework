package com.ri.generalFramework.util;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListUtil {

    /**
     * 检查 collection 不为空
     * @param collection collection
     * @return boolean
     */
    private boolean checkListNotEmpty(Collection<?> collection) {
        return !checkListIsEmpty(collection);
    }

    /**
     * 检查 collection 为空
     * @param collection collection
     * @return boolean
     */
    private boolean checkListIsEmpty(Collection<?> collection) {
        if (collection == null)
            return true;
        // 剔除 collection 中的空元素
        collection = collection.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return collection.size() == 0;
    }
}
