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
		ctx.writeAndFlush(buildLoginReq());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage)msg;
		//如果是握手应答消息，需要判断是否认证成功
		if(null!=message.getHeader() && MessageType.LOGIN_RESP.value==message.getHeader().getType()) {
			byte loginResult=(byte) message.getBody();
			if(loginResult!=(byte)0) {
				//握手失败，关闭连接x
				ctx.close();
			}
			else {
				System.out.println("Login is ok:"+message);
				ctx.fireChannelRead(msg);
			}
		}else {
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
	
	private NettyMessage buildLoginReq() {
		
		NettyMessage message=new NettyMessage();
		Header header=new Header();
		header.setType(MessageType.LOGIN_REQ.value);
		message.setHeader(header);
		return message;
	}
	
	

}
