package top.laonaailifa.game;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.laonaailifa.game.msg.GameMsgProtocol;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class GameMsgRecognizer {

    static final Logger LOGGER = LoggerFactory.getLogger(GameMsgRecognizer.class);

    static private final Map<Integer, GeneratedMessageV3> _msgCodeAndMsgBodyMap = new HashMap<>();

    static private final Map<Class<?>, Integer> _msgClassAndMsgCodeMap = new HashMap<>();


    static {
        for (Class<?> innerClazz : GameMsgProtocol.class.getDeclaredClasses()) {
            if (!GeneratedMessageV3.class.isAssignableFrom(innerClazz)) {
                continue;
            }
            String clazzName = innerClazz.getSimpleName();
            clazzName = clazzName.toLowerCase();
            for (GameMsgProtocol.MsgCode msgCode : GameMsgProtocol.MsgCode.values()) {
                String strMsgCode = msgCode.name();
                strMsgCode = strMsgCode.replaceAll("_", "");
                strMsgCode = strMsgCode.toLowerCase();

                if (!strMsgCode.startsWith(clazzName)) {
                    continue;
                }

                try {
                    Object returnObj = innerClazz.getDeclaredMethod("getDefaultInstance").invoke(innerClazz);
                    _msgCodeAndMsgBodyMap.put(msgCode.getNumber(), ((GeneratedMessageV3) returnObj));
                    _msgClassAndMsgCodeMap.put(innerClazz, msgCode.getNumber());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static public Message.Builder getBuilderByMsgCode(int msgCode) {
        return _msgCodeAndMsgBodyMap.get(msgCode).toBuilder();
    }

    static public int getMessageCodeByMessageClazz(Class<?> msgClazz) {
        Integer msgCode = _msgClassAndMsgCodeMap.get(msgClazz);
        if (null != msgCode) {
            return msgCode;
        }
        return -1;
    }

    private GameMsgRecognizer() {

    }
}
