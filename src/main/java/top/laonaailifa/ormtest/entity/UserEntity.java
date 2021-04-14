package top.laonaailifa.ormtest.entity;

import top.laonaailifa.ormtest.annotation.Colum;

public class UserEntity {

    @Colum("user_id")
    private int id;

    @Colum("user_name")
    private String name;

    @Colum("password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
