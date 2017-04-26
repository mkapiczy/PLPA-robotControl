package dk.plpa.scheme;


import java.util.List;

public class SchemeTypesMapper {


    public static <T> String javaListToSchemeList(List<T> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else {
            String s = "'(" + javaList.toString().replace(",", "").replace("[", "").replace("]", "") + ')';
            return s;
        }
    }

    public static <T> String javaTwoElementListToSchemeCons(List<T> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else if(javaList.size()==2){
            String s = "(cons " + javaList.toString().replace(",", "").replace("[", "").replace("]", "").replace("null","'()") + ')';
            return s;
        } else{
            return null;
        }
    }


    public static <T> String javaListOfListToSchemeListOfPairs(List<List<T>> javaList) {
        if (javaList == null || javaList.isEmpty()) {
            return "'()";
        } else {
            String s = "'(";
            for (int i = 0; i < javaList.size(); i++) {
                    s += javaTwoElementListToSchemeCons(javaList.get(i));
                    s+= " ";
            }
            s += ')';
            return s;
        }
    }
}
