package tech.ccyyyy.netty.Protocol.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import tech.ccyyyy.netty.Protocol.model.MessageType;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;

/**
 * @author zcy
 * @date 2019年4月1日 上午10:21:10
*/
public class ChatChannelRespHandler extends ChannelHandlerAdapter{


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage) msg;
		if(null!=message.getHeader() && MessageType.CHAT_REQ.value==message.getHeader().getType()) {
			//是聊天消息，输出
			System.out.println("服务端收到消息："+message.getBody());
		}else if(null!=message.getHeader() && 
				MessageType.HEARTBEAT_RESP.value==message.getHeader().getType()) {
			 System.out.println("Client receive server heart beat message:"+message);
		}else {
			ctx.fireChannelRead(msg);
		}
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
	

}
