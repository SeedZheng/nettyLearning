package tech.ccyyyy.netty.Protocol.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import tech.ccyyyy.netty.Protocol.model.Header;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;
import tech.ccyyyy.netty.Protocol.utils.MarshallingDecoder;

/**
 * @author zcy
 * @date 2019年3月29日 下午3:40:14
*/
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
	
	MarshallingDecoder marshallingDecoder;

	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		marshallingDecoder=new MarshallingDecoder();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf frame=(ByteBuf) super.decode(ctx, in);
		if(null==frame) {
			return null;
		}
		NettyMessage message=new NettyMessage();
		Header header=new Header();
		header.setCrcCode(frame.readInt());
		header.setLength(frame.readInt());
		header.setSessionId(frame.readLong());
		header.setType(frame.readByte());
		header.setPriority(frame.readByte());
		
		//附件
		int size=frame.readInt();
		if(size>0) {
			Map<String, Object> attach=new HashMap<>(size);
			int keySize=0;
			byte[] keyArray;
			String key;
			for(int i=0;i<size;i++) {
				keySize=in.readInt();
				keyArray=new byte[keySize];
				in.readBytes(keyArray);
				key=new String(keyArray, "UTF-8");
				attach.put(key, marshallingDecoder.decode(in));
			}
			keyArray=null;
			key=null;
			header.setAttachment(attach);
		}
		if(frame.readableBytes()>4) {
			message.setBody(marshallingDecoder.decode(frame));
		}
		message.setHeader(header);
		
		return message;
	}
	
	

}
