package top.laonaailifa.game;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {

    static private final Logger LOGGER = LoggerFactory.getLogger(GameMsgEncoder.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg == null || !(msg instanceof GeneratedMessageV3)) {
            super.write(ctx, msg, promise);
            return;
        }

        int msgCode = GameMsgRecognizer.getMessageCodeByMessageClazz(msg.getClass());

        if (msgCode <= -1) {
            LOGGER.error("unknown protocol type, msgClass : " + msg.getClass().getName());
        }

        byte[] msgBody = ((GeneratedMessageV3) msg).toByteArray();
        ByteBuf byteBuf = ctx.alloc().directBuffer();
        byteBuf.writeShort((short) msgBody.length);
        byteBuf.writeShort((short) msgCode);
        byteBuf.writeBytes(msgBody);

        super.write(ctx, new BinaryWebSocketFrame(byteBuf), promise);
    }
}
