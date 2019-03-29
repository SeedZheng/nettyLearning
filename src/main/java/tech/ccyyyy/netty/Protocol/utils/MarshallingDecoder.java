package tech.ccyyyy.netty.Protocol.utils;
/**
 * @author zcy
 * @date 2019年3月29日 下午3:41:36
*/

import java.io.IOException;

import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;

import io.netty.buffer.ByteBuf;

public class MarshallingDecoder {
	
	private final Unmarshaller unmarshaller;

	public MarshallingDecoder() throws IOException {
		unmarshaller=MarshallingCodecFactory.buildUnMarshalling();
	}
	
	public Object decode(ByteBuf in)throws Exception{
		
		int ObjectSize=in.readInt();
		ByteBuf buf=in.slice(in.readerIndex(), ObjectSize);
		ByteInput input=new ChannelBufferByteInput(buf);
		
		try {
			unmarshaller.start(input);
			Object object=unmarshaller.readObject();
			unmarshaller.finish();
			in.readerIndex(in.readerIndex()+ObjectSize);
		} finally {
			unmarshaller.close();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
