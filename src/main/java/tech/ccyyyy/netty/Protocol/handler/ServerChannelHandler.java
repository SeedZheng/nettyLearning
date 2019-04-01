package tech.ccyyyy.netty.Protocol.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import tech.ccyyyy.netty.Protocol.service.NettyMessageDecoder;
import tech.ccyyyy.netty.Protocol.service.NettyMessageEncoder;

/**
 * @author zcy
 * @date 2019年3月30日 下午2:49:14
*/
public class ServerChannelHandler extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(new NettyMessageDecoder(1024*1024, 4, 4));
		ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
		//ch.pipeline().addLast("readTimeOutHandler", new ReadTimeoutHandler(50));
		ch.pipeline().addLast("LoginAuthHandler", new LoginAuthRespHandler());
		//ch.pipeline().addLast("HeartBeatHandler", new HeartBeatRespHandler());
		ch.pipeline().addLast("ChatChannelRespHandler", new ChatChannelRespHandler());
		
	}

}
