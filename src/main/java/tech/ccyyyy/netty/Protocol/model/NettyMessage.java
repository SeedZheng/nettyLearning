package tech.ccyyyy.netty.Protocol.model;
/**
 * @author zcy
 * @date 2019年3月27日 下午9:08:26
*/
public final class NettyMessage {
	
	private Header header;//消息头
	private Object body;	//消息体
	
	public final Header getHeader() {
		return header;
	}
	public final void setHeader(Header header) {
		this.header = header;
	}
	public final Object getBody() {
		return body;
	}
	public final void setBody(Object body) {
		this.body = body;
	}
	
	

}
