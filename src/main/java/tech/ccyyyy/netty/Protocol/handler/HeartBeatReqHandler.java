package tech.ccyyyy.netty.Protocol.handler;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import tech.ccyyyy.netty.Protocol.model.Header;
import tech.ccyyyy.netty.Protocol.model.MessageType;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;

/**
 * @author zcy
 * @date 2019年3月30日 下午1:54:32
*/
public class HeartBeatReqHandler extends ChannelHandlerAdapter {
	
	private volatile ScheduledFuture<?> heartBeat;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage) msg;
		
		if(null!=message.getHeader() && 
				MessageType.LOGIN_RESP.value==message.getHeader().getType()) {
			heartBeat=ctx.executor().
					scheduleAtFixedRate(new HeartBeatTask(ctx), 0, 5000, TimeUnit.MILLISECONDS);
		}else if(null!=message.getHeader() && 
				MessageType.HEARTBEAT_RESP.value==message.getHeader().getType()) {
			 System.out.println("Client receive server heart beat message:"+message);
		}else {
			ctx.fireChannelRead(msg);
		}
	}
	
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if(heartBeat!=null) {
			heartBeat.cancel(true);
			heartBeat=null;
		}
		ctx.fireExceptionCaught(cause);
	}
	
	private class HeartBeatTask implements Runnable{
		
		private final ChannelHandlerContext ctx;
		
		public HeartBeatTask(final ChannelHandlerContext ctx) {
			this.ctx=ctx;
		}

		@Override
		public void run() {
			NettyMessage message=buildHeartBeart();
			System.out.println("Client send heart beat message to server:"+heartBeat);
			ctx.writeAndFlush(message);
			
		}
		
		private NettyMessage buildHeartBeart() {
			NettyMessage message =new NettyMessage();
			Header header=new Header();
			header.setType(MessageType.HEARTBEAT_REQ.value);
			message.setHeader(header);
			return message;
		}
		
	}

}
