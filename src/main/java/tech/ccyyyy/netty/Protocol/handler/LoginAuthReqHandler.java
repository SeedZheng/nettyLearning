package tech.ccyyyy.netty.Protocol.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import tech.ccyyyy.netty.Protocol.model.Header;
import tech.ccyyyy.netty.Protocol.model.MessageType;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;

/**
 * @author zcy
 * @date 2019年3月29日 下午5:45:20
*/
public class LoginAuthReqHandler extends ChannelHandlerAdapter{

	

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		super.channelRead(ctx, msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}
	
	private NettyMessage buildNettyReq() {
		
		NettyMessage message=new NettyMessage();
		Header header=new Header();
		header.setType(MessageType.LOGIN_RESP.value);
		message.setHeader(header);
		return message;
	}
	
	

}
