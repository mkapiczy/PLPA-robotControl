package dk.plpa.utils;


import java.util.List;

public class SchemeUtils {


    public static <T> String javaListToSchemeList(List<T> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else {
            return "'(" + javaList.toString().replace(",", "").replace("[", "").replace("]", "") + ')';
        }
    }
}
