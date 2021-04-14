package top.laonaailifa.game.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public final class PackageUtil {
    private PackageUtil() {

    }

    static public Set<Class<?>> listSubClasses(String packageName, boolean recursive, Class<?> superClazz) {
        if (superClazz == null) {
            return Collections.EMPTY_SET;
        } else {
            return listClazz(packageName, recursive, superClazz::isAssignableFrom);
        }
    }

    private static Set<Class<?>> listClazz(String packageName, boolean recursive, IClassFilter filter) {
        if (packageName == null || packageName.isEmpty()) {
            return null;
        }

        String packagePath = packageName.replace(".", "/");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        HashSet<Class<?>> tmp = new HashSet<>();

        try {
            Enumeration<URL> urlEnum = classLoader.getResources(packagePath);

            while (urlEnum.hasMoreElements()) {
                URL url = urlEnum.nextElement();
                String protocol = url.getProtocol();


                if ("File".equalsIgnoreCase(protocol)) {
                    getClassesFromDir(new File(url.getFile()), packageName, recursive, filter, tmp);
                }else if ("jar".equalsIgnoreCase(protocol)){
                    getClassesFromJar(new File(url.getFile()), packageName, recursive, filter, tmp);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    private static void getClassesFromJar(File file, String packageName, boolean recursive, IClassFilter filter, HashSet<Class<?>> tmp) {

    }

    private static void getClassesFromDir(File dirFile, String packageName, boolean recursive, IClassFilter filter, HashSet<Class<?>> tmp) {
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return;
        }

        for (File file : dirFile.listFiles()) {
            if (file.isDirectory()) {
                if (recursive) {
                    getClassesFromDir(file, packageName, recursive, filter, tmp);
                } else {
                    return;
                }
            }

            if (!file.isFile() || !file.getName().endsWith(".class")) {
                return;
            }

            String className = packageName + file.getName().split("\\.")[0];
            try {
                Class<?> clazz = Class.forName(className);
                if (filter.accept(clazz)) {
                    tmp.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    @FunctionalInterface
    static public interface IClassFilter {

        boolean accept(Class<?> clazz);
    }
}
