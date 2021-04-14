package top.laonaailifa.game;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public final class Broadcaster {
    /**
     * clients channel
     */
    static private final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Broadcaster(){

    }

    /**
     * add channel
     * @param channel
     */
    static public void addChannel(Channel channel){
        CHANNEL_GROUP.add(channel);
    }

    /**
     * remove channel
     * @param channel
     */
    static public void removeChannel(Channel channel){
        CHANNEL_GROUP.remove(channel);
    }

    static public void broadcast(Object msg){
        if (msg == null) {
            return;
        }
        CHANNEL_GROUP.writeAndFlush(msg);
    }
}
