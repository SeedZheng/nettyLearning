package tech.ccyyyy.netty.Protocol.utils;

import java.io.IOException;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/**
 * @author zcy
 * @date 2019年3月27日 下午9:31:55
*/
public class MarshallingCodecFactory {
	
	 /** 
     * 创建Jboss Marshaller 
     * @throws IOException 
     */  
    protected static Marshaller buildMarshalling() throws IOException {  
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");  
        final MarshallingConfiguration configuration = new MarshallingConfiguration();  
        configuration.setVersion(5);  
        Marshaller marshaller = marshallerFactory.createMarshaller(configuration);  
        return marshaller;  
    }  
  
    /** 
     * 创建Jboss Unmarshaller 
     * @throws IOException 
     */  
    protected static Unmarshaller buildUnMarshalling() throws IOException {  
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");  
        final MarshallingConfiguration configuration = new MarshallingConfiguration();  
        configuration.setVersion(5);  
        final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);  
        return unmarshaller;  
    }  

}
