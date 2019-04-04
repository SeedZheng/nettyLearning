package tech.ccyyyy.netty.Protocol.model;

import java.util.List;

import io.netty.channel.Channel;

/**
 * @author zcy
 * @date 2019年4月2日 下午2:14:41
*/
public interface SessionHolder {
	
	void put(String channelId,Channel channel);
	
	Channel get(String channelId);
	
	List<Channel> getAll();
	
	List<Channel> getAllWithoutOne(String channelId);
	
	void remove(String channelId); 

}
