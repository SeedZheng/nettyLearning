package tech.ccyyyy.netty.Protocol.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import tech.ccyyyy.netty.Protocol.handler.ServerChannelHandler;
import tech.ccyyyy.netty.Protocol.model.NettyConstant;

/**
 * @author zcy
 * @date 2019年3月30日 下午2:45:09
*/
public class NettyServer {
	
	public static void main(String[] args) throws Exception {
		new NettyServer().bind();
	}
	
	public void bind() throws Exception{
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup worker=new NioEventLoopGroup();
		ServerBootstrap b=new ServerBootstrap();
		b.group(boss, worker).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ServerChannelHandler());
		
		//绑定端口，等待连接
		b.bind(NettyConstant.REMOTE_IP,NettyConstant.REMOTE_PORT).sync();
		System.out.println("Netty server start at "+NettyConstant.REMOTE_IP+":"+NettyConstant.REMOTE_PORT);
		
	}

}
