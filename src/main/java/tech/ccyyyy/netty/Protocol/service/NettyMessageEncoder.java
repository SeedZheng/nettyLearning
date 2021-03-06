package tech.ccyyyy.netty.Protocol.service;

import java.io.IOException;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;
import tech.ccyyyy.netty.Protocol.utils.MarshallingEncoder;

/**
 * @author zcy
 * @date 2019年3月27日 下午9:20:49
*/
public final class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
	
	MarshallingEncoder marshallingEncoder;
	
	public NettyMessageEncoder() throws IOException {
		this.marshallingEncoder=new MarshallingEncoder();
	}


	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
		if(msg==null || msg.getHeader()==null) {
			throw new Exception("The encode message is null");
		}
		sendBuf.writeInt(msg.getHeader().getCrcCode());
		sendBuf.writeInt(msg.getHeader().getLength());
		sendBuf.writeLong(msg.getHeader().getSessionId());
		sendBuf.writeByte(msg.getHeader().getType());
		sendBuf.writeByte(msg.getHeader().getPriority());
		sendBuf.writeInt(msg.getHeader().getAttachment().size());
	
		
		String key;
		byte[] keyArray;
		Object value;
		for(Map.Entry<String, Object> param:msg.getHeader().getAttachment().entrySet()) {
			key=param.getKey();
			keyArray=key.getBytes("UTF-8");
			sendBuf.writeInt(keyArray.length);
			sendBuf.writeBytes(keyArray);
			value=param.getValue();
			marshallingEncoder.encode(value, sendBuf);
		}
		//for gc
		key=null;
		keyArray=null;
		value=null;
		if(null!=msg.getBody()) {
			marshallingEncoder.encode(msg.getBody(), sendBuf);
		}else {
			sendBuf.writeInt(0);
			// 之前写了crcCode 4bytes，除去crcCode和length 8bytes即为更新之后的字节
			//sendBuf.setInt(4, sendBuf.readableBytes());
		}
		sendBuf.setInt(4, sendBuf.readableBytes()-8);
	}

}
