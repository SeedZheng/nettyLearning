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
		//1. 读取第一个4bytes，里面放置的是object对象的byte长度
		int ObjectSize=in.readInt();
		ByteBuf buf=in.slice(in.readerIndex(), ObjectSize);
		 //2 . 使用bytebuf的代理类
		ByteInput input=new ChannelBufferByteInput(buf);
		
		try {
			//3. 开始解码
			unmarshaller.start(input);
			Object object=unmarshaller.readObject();
			unmarshaller.finish();
			//4. 读完之后设置读取的位置
			in.readerIndex(in.readerIndex()+ObjectSize);
			return object;
		} finally {
			unmarshaller.close();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
