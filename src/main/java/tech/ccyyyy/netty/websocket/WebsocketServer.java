package tech.ccyyyy.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author seed
 * @date 2019年3月25日 下午9:02:52
 */
public class WebsocketServer {
	
	public static void main(String[] args) throws Exception {
		new WebsocketServer().run(8888);;
	}
	
	private void run(int port) throws Exception{
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		
		try {
			ServerBootstrap bootstrap=new ServerBootstrap();
			bootstrap.group(bossGroup,workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChildChannelHandler());
				
			Channel ch=bootstrap.bind(port).sync().channel();
			System.out.println("Web socket server start at "+port);
			System.out.println("Open your browser and navigate to http://localhost:"+port+"/");
			ch.closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
