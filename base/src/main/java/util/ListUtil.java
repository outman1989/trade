package util;

import java.util.List;

public class ListUtil {
    public ListUtil() {
    }

    public static boolean isEmpty(List list) {
        return null == list || 0 >= list.size();
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isEmpty(Object[] objs) {
        return null == objs || 0 >= objs.length;
    }
}
