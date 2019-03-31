package tech.ccyyyy.netty.Protocol.server;
/**
 * @author zcy
 * @date 2019年3月30日 下午2:21:04
*/

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import tech.ccyyyy.netty.Protocol.handler.ClientChannelHandler;
import tech.ccyyyy.netty.Protocol.model.NettyConstant;

public class NettyClient {
	
	private ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
	EventLoopGroup group=new NioEventLoopGroup();
	
	
	public static void main(String[] args) throws Exception {
		new NettyClient().connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTE_IP);
	}
	
	
	public void connect(int port,String host) throws Exception {
		
		try {
			Bootstrap bootstrap=new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ClientChannelHandler());
			//异步连接
			ChannelFuture future=bootstrap.connect(
					new InetSocketAddress(host, port)).sync();
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		
			System.out.println("尝试重连");
			//释放资源,重连
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						TimeUnit.SECONDS.sleep(5);
						//重连
						connect(NettyConstant.REMOTE_PORT, NettyConstant.REMOTE_IP);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
		}
	}

}
