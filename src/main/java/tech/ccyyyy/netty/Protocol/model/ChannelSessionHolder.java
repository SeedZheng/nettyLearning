package tech.ccyyyy.netty.Protocol.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import io.netty.channel.Channel;

/**
 * @author zcy
 * @date 2019年4月2日 下午2:04:41
*/
public class ChannelSessionHolder{
	
	//存储在线用户
	private static Map<String, Channel> onlineUsers=new ConcurrentHashMap<>();

	public static void put(String channelId, Channel channel) {
		onlineUsers.put(channelId, channel);
	}

	public static Channel get(String channelId) {
		return onlineUsers.get(channelId);
	}

	public static List<Channel> getAll() {
		return onlineUsers.entrySet().stream().map(channel ->channel.getValue()).collect(Collectors.toList());
	}

	public static List<Channel> getAllWithoutOne(String channelId) {
		return onlineUsers.entrySet().stream().
				filter(c -> !c.getKey().equalsIgnoreCase(channelId)).
				map(channel ->channel.getValue()).
				collect(Collectors.toList());
	}

	public static void remove(String channelId) {
		onlineUsers.remove(channelId);
	}
	
	public boolean contain(String channelId) {
		return onlineUsers.containsKey(channelId);
	}
	
	public boolean contail(Channel channel) {
		return onlineUsers.containsValue(channel);
	}
}
