package top.laonaailifa.game;

import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class GameMsgDecoder extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (ctx == null || msg == null){
            return;
        }

        if (!(msg instanceof BinaryWebSocketFrame)) {
            return;
        }

        BinaryWebSocketFrame frame = (BinaryWebSocketFrame) msg;
        ByteBuf content = frame.content();

        content.readShort();
        int msgCode = content.readShort();

        Message.Builder msgBuilder = GameMsgRecognizer.getBuilderByMsgCode(msgCode);
        if (msgBuilder == null) {
            System.out.println("can't recognize the msg type");
            return;
        }

        byte[] msgBody = new byte[content.readableBytes()];
        content.readBytes(msgBody);

        msgBuilder.mergeFrom(msgBody);
        Message message = msgBuilder.build();

        if (message != null) {
            ctx.fireChannelRead(message);
        }
    }
}
