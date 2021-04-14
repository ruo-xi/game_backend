package top.laonaailifa.game.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import top.laonaailifa.game.Broadcaster;
import top.laonaailifa.game.model.User;
import top.laonaailifa.game.msg.GameMsgProtocol;

public class UserMoveToCmdHandler implements ICmdHandler<GameMsgProtocol.UserMoveToCmd> {
    public void handler(ChannelHandlerContext ctx, GameMsgProtocol.UserMoveToCmd msg) {
        GameMsgProtocol.UserMoveToCmd userMoveToCmd = msg;
        GameMsgProtocol.UserMoveToResult.Builder resultBuilder = GameMsgProtocol.UserMoveToResult.newBuilder();
        User user = (User) ctx.channel().attr(AttributeKey.valueOf("userId")).get();
        if (user == null) {
            return;
        }
        resultBuilder.setMoveToPosX(userMoveToCmd.getMoveToPosX());
        resultBuilder.setMoveToPosY(userMoveToCmd.getMoveToPosY());
        resultBuilder.setMoveUserId(user.getUserId());

        GameMsgProtocol.UserMoveToResult userMoveToResult = resultBuilder.build();
        Broadcaster.broadcast(userMoveToResult);
    }
}
