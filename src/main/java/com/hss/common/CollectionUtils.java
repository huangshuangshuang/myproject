package com.hss.common;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object ...os) {
        return os == null || os.length == 0;
    }
}
