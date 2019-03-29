package tech.ccyyyy.netty.Protocol.utils;

import java.io.IOException;

import org.jboss.marshalling.ByteOutput;

import io.netty.buffer.ByteBuf;

/**
 * @author zcy
 * @date 2019年3月28日 下午1:50:39
*/
class ChannelBufferByteOutput implements ByteOutput {  
	  
    private final ByteBuf buffer;  
      
    /** 
     * Create a new instance which use the given {@link ByteBuf} 
     */  
    public ChannelBufferByteOutput(ByteBuf buffer) {  
        this.buffer = buffer;  
    }  
      
    @Override  
    public void close() throws IOException {  
          
    }  
  
    @Override  
    public void flush() throws IOException {  
          
    }  
  
    @Override  
    public void write(int b) throws IOException {  
        buffer.writeByte(b);  
    }  
  
    @Override  
    public void write(byte[] bytes) throws IOException {  
        buffer.writeBytes(bytes);  
    }  
  
    @Override  
    public void write(byte[] bytes, int srcIndex, int length) throws IOException {  
        buffer.writeBytes(bytes, srcIndex, length);  
    }  
      
    /** 
     * Return the {@link ByteBuf} which contains the written content 
     */  
    ByteBuf getBuffer() {  
        return buffer;  
    }  
  
}  
