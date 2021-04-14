package top.laonaailifa.game.model;

import java.util.HashMap;
import java.util.stream.Stream;

public final class UserManager {

    static private final HashMap<Integer, User> USER_MAP = new HashMap<>();

    private UserManager() {

    }

    static public void addUser(User user) {
        if (user != null) {
            USER_MAP.put(user.getUserId(), user);
        }
    }

    static public void removeUser(User user) {
        USER_MAP.remove(user.getUserId());
    }

    static public Stream<User> getUserStream(){
        return USER_MAP.values().stream();
    }


}
