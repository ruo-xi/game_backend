package top.laonaailifa.ormtest.entity;

import javassist.*;

import java.sql.ResultSet;

public final class EntityHelperFactory {
    private EntityHelperFactory() {

    }

    static public AbstractEntityHelper getEntityHelper(Class<?> clazz) throws NotFoundException, CannotCompileException {
        if (clazz == null) {
            return null;
        }
        ClassPool p = ClassPool.getDefault();
        p.appendSystemPath();

        p.importPackage(ResultSet.class.getName());
        p.importPackage(clazz.getName());

        CtClass clazzHelper = p.getCtClass(AbstractEntityHelper.class.getName());

        // the name of Helper class to be create
        String helperClazz = clazz.getName() + "_Helper";

        //make created **_Helper class extend AEH.class
        CtClass ctClass = p.makeClass(helperClazz, clazzHelper);

        // create ctor
        CtConstructor ctConstructor = new CtConstructor(new CtClass[0], ctClass);

        ctClass.addConstructor(ctConstructor);
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("public Object create(java.sql.ResultSet rs) throw Exception {\n");
        stringBuffer.append("return null");
        stringBuffer.append("{");


    }
}
