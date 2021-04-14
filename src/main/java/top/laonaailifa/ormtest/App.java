package top.laonaailifa.ormtest;

import top.laonaailifa.ormtest.entity.UserEntity;
import top.laonaailifa.ormtest.entity.XxxEntity_Helper;

import java.sql.*;

public class App {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbConnStr = "jdbc:mysql://127.0.0.1:3306/game_hero_story?user=root&password=cao19981128&rewriteBatchedStatements=true";

            Connection connection = DriverManager.getConnection(dbConnStr);
//            insert(connection);

            ResultSet resultSet = select(connection);

            long start = System.currentTimeMillis();
            passValue(resultSet);
            System.out.println(System.currentTimeMillis() - start + "ms");

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void passValue(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            UserEntity userEntity = XxxEntity_Helper.getUser(UserEntity.class, resultSet);
        }
    }

    private static ResultSet select(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user limit 1000000");
        return resultSet;
    }

    private static void insert(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement cmd = connection
                .prepareStatement("insert into user(user_name, password)  values(?,?)");

        for (int i = 0; i < 1000000; i++) {
            System.out.println(i);
            cmd.setString(1, "caoxiaoyu" + i);
            cmd.setString(2, "test" + i);
            cmd.addBatch();
            if (i % 2000 == 0) {
                cmd.executeBatch();
            }
        }
        connection.commit();
    }
}

// add item to mysql

