package tech.ccyyyy.netty.Protocol.service;

import java.io.IOException;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;

/**
 * @author zcy
 * @date 2019年3月27日 下午9:20:49
*/
public final class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
	
	//MarshallingEncoder marshallingEncoder;
	

	public NettyMessageEncoder() throws IOException {
		//this.marshallingEncoder=new MarshallingEncoder();
	}





	@Override
	protected void encode(ChannelHandlerContext arg0, NettyMessage arg1, List<Object> arg2) throws Exception {
		// TODO Auto-generated method stub
	}

}
