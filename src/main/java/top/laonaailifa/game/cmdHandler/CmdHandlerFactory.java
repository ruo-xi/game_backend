package top.laonaailifa.game.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import top.laonaailifa.game.msg.GameMsgProtocol;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class CmdHandlerFactory {

    static private Map<Class<?>, ICmdHandler<? extends GeneratedMessageV3>> HANDLER_MAP = new HashMap<>();

    //    static public void init() {
    static {
        File dir = new File("src/main/java/top/laonaailifa/practice/cmdHandler");
        System.out.println(dir.getAbsolutePath());
        ClassLoader classLoader = CmdHandlerFactory.class.getClassLoader();
        for (File file : dir.listFiles()) {
            String fileName = file.getName().split("\\.")[0];
            for (Class<?> innerClazz : GameMsgProtocol.class.getDeclaredClasses()) {
                String clazzName = innerClazz.getSimpleName();
                if (fileName.startsWith(clazzName) && "Handler".equals(fileName.substring(clazzName.length()))) {
                    try {
                        HANDLER_MAP.put(innerClazz, (ICmdHandler<? extends GeneratedMessageV3>) classLoader.loadClass("top.laonaailifa.practice.cmdHandler." + fileName).getConstructor().newInstance());
                    } catch (InstantiationException e) {
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        HANDLER_MAP.put(GameMsgProtocol.UserEntryCmd.class, new UserEntryCmdHandler());
//        HANDLER_MAP.put(GameMsgProtocol.WhoElseIsHereCmd.class, new WhoElseIsHereCmdHandler());
//        HANDLER_MAP.put(GameMsgProtocol.UserMoveToCmd.class, new UserMoveToCmdHandler());
    }

    public static ICmdHandler<? extends GeneratedMessageV3> cmdFactory(Class<?> klass) {
        if (klass == null) {
            return null;
        }
        return HANDLER_MAP.get(klass);
    }

    private CmdHandlerFactory() {

    }
}
