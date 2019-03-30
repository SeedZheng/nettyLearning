package tech.ccyyyy.netty.Protocol.handler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import tech.ccyyyy.netty.Protocol.model.Header;
import tech.ccyyyy.netty.Protocol.model.MessageType;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;

/**
 * @author zcy
 * @date 2019年3月30日 下午1:23:12
*/
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
	
	private Map<String, Boolean> nodeCheck=new ConcurrentHashMap<>();
	private String[] whiteList= {"127.0.0.1"};
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage) msg;
		if(null!=message.getHeader() && message.getHeader().getType()==MessageType.LOGIN_REQ.value) {
			String nodeIndex=ctx.channel().remoteAddress().toString();
			NettyMessage loginResp;
			//拒绝重复登录
			if(nodeCheck.containsKey(nodeIndex)) {
				loginResp=buildResponse((byte) -1);
			}else {
				InetSocketAddress address=(InetSocketAddress) ctx.channel().remoteAddress();
				String ip=address.getAddress().getHostAddress();
				boolean isOk=false;
				for(String wip:whiteList) {
					if (wip.equals(ip)) {
						isOk=true;
						break;
					}
				}
				loginResp=isOk?buildResponse((byte)0):buildResponse((byte) -1);
				if(isOk) {
					nodeCheck.put(nodeIndex, true);
				}
			}
			System.out.println("The login response is :"+loginResp+"body ["+loginResp.getBody()+" ]");
			ctx.writeAndFlush(loginResp);
		}else {
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//删除缓存
		nodeCheck.remove(ctx.channel().remoteAddress().toString());
		ctx.close();
		ctx.fireExceptionCaught(cause);
	}


	private NettyMessage buildResponse(byte result) {
		
		NettyMessage message=new NettyMessage();
		Header header=new Header();
		header.setType(MessageType.LOGIN_RESP.value);
		message.setHeader(header);
		message.setBody(result);
		return message;
	}

}
