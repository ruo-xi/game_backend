package top.laonaailifa.ormtest.entity;

import top.laonaailifa.ormtest.annotation.Colum;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class XxxEntity_Helper {

    public static <Entity> Entity getUser(Class<Entity> clazz, ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }
        try {
            Entity entity = clazz.getConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {

                // get the Custom Annotation value
                String s = field.getAnnotation(Colum.class).value();
                // pass the value to the filed
                Object object = resultSet.getObject(s);
                if (object != null) {
                    field.setAccessible(true);
                    field.set(entity, object);
                }
            }

            return entity;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
