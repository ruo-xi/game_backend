package top.laonaailifa.game.cmdHandler;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;

public interface ICmdHandler<TCmd extends GeneratedMessageV3> {
    public void handler(ChannelHandlerContext context, TCmd msg);
}
