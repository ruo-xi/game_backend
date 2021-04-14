package top.laonaailifa.ormtest.entity;

import java.sql.ResultSet;

public abstract class AbstractEntityHelper {
    public abstract Object create(ResultSet resultSet) throws Exception;
}
