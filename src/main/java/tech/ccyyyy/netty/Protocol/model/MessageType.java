package tech.ccyyyy.netty.Protocol.model;
/**
 * @author zcy
 * @date 2019年3月29日 下午5:49:44
*/
public enum MessageType {
	
	//心跳请求，应答
    HEARTBEAT_REQ((byte) 5),
    HEARTBEAT_RESP((byte) 6),
 
    //握手请求，应答
    LOGIN_REQ((byte) 3),
    LOGIN_RESP((byte) 4),
	
	//聊天消息
	CHAT_REQ((byte)7),
	CHAT_RESP((byte)8);
 
    public byte value;
 
    MessageType(byte value) {
        this.value = value;
    }
    
    /**
     *
    Byte BUSINESS_REQ = 0;
 
    Byte BUSINESS_RESP = 1;
 
    Byte BUSINESS_ONE_WAY = 2;
 
    Byte LOGIN_REQ = 3;
 
    Byte LOGIN_RESP = 4;
 
    Byte HEARTBEAT_REQ = 5;
 
    Byte HEARTBEAT_RESP = 6;
     * 
     * 
     */


}
