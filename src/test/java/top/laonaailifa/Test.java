package top.laonaailifa;

import top.laonaailifa.game.cmdHandler.CmdHandlerFactory;
import top.laonaailifa.game.cmdHandler.ICmdHandler;
import top.laonaailifa.game.util.PackageUtil;

import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Set<Class<?>> classSet = PackageUtil.listSubClasses(CmdHandlerFactory.class.getPackageName(), true, ICmdHandler.class);
        for (Class<?> aClass : classSet) {

        }
    }
}
