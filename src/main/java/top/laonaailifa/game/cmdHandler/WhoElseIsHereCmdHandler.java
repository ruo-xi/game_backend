package top.laonaailifa.game.cmdHandler;

import io.netty.channel.ChannelHandlerContext;
import top.laonaailifa.game.model.UserManager;
import top.laonaailifa.game.msg.GameMsgProtocol;

public class WhoElseIsHereCmdHandler implements ICmdHandler<GameMsgProtocol.WhoElseIsHereCmd> {
    public void handler(ChannelHandlerContext ctx, GameMsgProtocol.WhoElseIsHereCmd whoElseIsHereCmd) {
        GameMsgProtocol.WhoElseIsHereResult.Builder resultBuilder = GameMsgProtocol.WhoElseIsHereResult.newBuilder();
        UserManager.getUserStream().forEach(user -> {
            if (user == null) {
                return;
            }
            GameMsgProtocol.WhoElseIsHereResult.UserInfo.Builder userInfoBuilder = GameMsgProtocol.WhoElseIsHereResult.UserInfo.newBuilder();
            userInfoBuilder.setUserId(user.getUserId());
            userInfoBuilder.setHeroAvatar(user.getHeroAvatar());
            resultBuilder.addUserInfo(userInfoBuilder.build());
        });
        GameMsgProtocol.WhoElseIsHereResult result = resultBuilder.build();
        ctx.writeAndFlush(result);
    }
}
