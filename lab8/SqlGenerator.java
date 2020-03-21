import com.company.annotation.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SqlGenerator {
    public static String getTableHeader(Class<?> entity) {
        return "CREATE TABLE " + entity.getName().toLowerCase() + "(";
    }
    public static String getTableFooter() {
        return ")";
    }

    public static String getTableFiels(Class<?> entity) {
        String code = "";
        Field[] fields = entity.getDeclaredFields();
        for (Field f : fields) {
            Annotation[] fannotations = f.getAnnotations();
            for (Annotation a : fannotations) {
                if (a.annotationType().equals(Id.class)) {
                    code = code + f.getName().toLowerCase() + " SERIAL, ";
                }
            }
        }
        return code;
    }
}
