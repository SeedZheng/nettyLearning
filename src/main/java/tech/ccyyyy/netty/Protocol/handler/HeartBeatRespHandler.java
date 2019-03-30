package tech.ccyyyy.netty.Protocol.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import tech.ccyyyy.netty.Protocol.model.Header;
import tech.ccyyyy.netty.Protocol.model.MessageType;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;

/**
 * @author zcy
 * @date 2019年3月30日 下午2:13:39
*/
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage) msg;
		//返回心跳应答消息
		if(null!=message.getHeader() &&
				MessageType.HEARTBEAT_REQ.value==message.getHeader().getType()) {
			System.out.println("Receive client heart beat message:"+message);
			NettyMessage heartBeat=buildHeartBeart();
			System.out.println("Send Heart beat response to client:"+heartBeat);
			ctx.writeAndFlush(heartBeat);
		}else {
			ctx.writeAndFlush(msg);
		}
	}

	private NettyMessage buildHeartBeart() {
		NettyMessage message =new NettyMessage();
		Header header=new Header();
		header.setType(MessageType.HEARTBEAT_RESP.value);
		message.setHeader(header);
		return message;
	}

}
