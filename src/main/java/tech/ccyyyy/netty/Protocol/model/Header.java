package tech.ccyyyy.netty.Protocol.model;
/**
 * @author zcy
 * @date 2019年3月27日 下午9:02:38
*/

import java.util.HashMap;
import java.util.Map;

public final class Header {
	
	private int crcCode=0xabef0101;	//CRC校验码
	private int length;			   	//消息长度
	private long sessionId;			
	private byte type;				//消息类型
	private byte priority;			//消息优先级
	private Map<String, Object> attachment=new HashMap<>();
	
	
	public final int getCrcCode() {
		return crcCode;
	}
	public final  void setCrcCode(int crcCode) {
		this.crcCode = crcCode;
	}
	public  final int getLength() {
		return length;
	}
	public final void setLength(int length) {
		this.length = length;
	}
	public final Long getSessionId() {
		return sessionId;
	}
	public final void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	public final byte getType() {
		return type;
	}
	public final void setType(byte type) {
		this.type = type;
	}
	public final byte getPriority() {
		return priority;
	}
	public final void setPriority(byte priority) {
		this.priority = priority;
	}
	public final Map<String, Object> getAttachment() {
		return attachment;
	}
	public final void setAttachment(Map<String, Object> attachment) {
		this.attachment = attachment;
	}
	
	

}
