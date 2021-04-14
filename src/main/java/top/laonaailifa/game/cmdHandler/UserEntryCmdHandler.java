package top.laonaailifa.game.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import top.laonaailifa.game.Broadcaster;
import top.laonaailifa.game.model.User;
import top.laonaailifa.game.model.UserManager;
import top.laonaailifa.game.msg.GameMsgProtocol;

public class UserEntryCmdHandler implements ICmdHandler<GameMsgProtocol.UserEntryCmd> {
    public void handler(ChannelHandlerContext ctx, GameMsgProtocol.UserEntryCmd msg) {
        int userId = msg.getUserId();
        String heroAvatar = msg.getHeroAvatar();

        GameMsgProtocol.UserEntryResult.Builder resultBuilder = GameMsgProtocol.UserEntryResult.newBuilder();
        resultBuilder.setUserId(userId);
        resultBuilder.setHeroAvatar(heroAvatar);

        User user = new User(userId, heroAvatar);
        UserManager.addUser(user);

        ctx.channel().attr(AttributeKey.valueOf("userId")).set(user);

        GameMsgProtocol.UserEntryResult userEntryResult = resultBuilder.build();
        Broadcaster.broadcast(userEntryResult);
    }
}
