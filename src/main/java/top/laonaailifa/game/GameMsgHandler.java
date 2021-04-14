package top.laonaailifa.game;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import top.laonaailifa.game.cmdHandler.*;
import top.laonaailifa.game.model.User;
import top.laonaailifa.game.model.UserManager;
import top.laonaailifa.game.msg.GameMsgProtocol;

public class GameMsgHandler extends SimpleChannelInboundHandler<Object> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (ctx == null) {
            return;
        }
        super.channelActive(ctx);
        Broadcaster.addChannel(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

        User user = ((User) ctx.channel().attr(AttributeKey.valueOf("userId")).get());
        if (user == null) {
            return;
        }
        UserManager.removeUser(user);
        Broadcaster.removeChannel(ctx.channel());

        GameMsgProtocol.UserQuitResult.Builder resultBuilder = GameMsgProtocol.UserQuitResult.newBuilder();
        resultBuilder.setQuitUserId(user.getUserId());

        Broadcaster.broadcast(resultBuilder.build());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ICmdHandler<? extends GeneratedMessageV3> cmdHandler = null;

        cmdHandler = CmdHandlerFactory.cmdFactory(msg.getClass());

        if (cmdHandler != null) {
            cmdHandler.handler(ctx, cast(msg));
        }
    }

    static private <TCmd extends GeneratedMessageV3> TCmd cast(Object msg) {
        if (null == msg) {
            return null;
        } else {
            return ((TCmd) msg);
        }
    }

}
