package tech.ccyyyy.netty.Protocol.handler;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import tech.ccyyyy.netty.Protocol.model.Header;
import tech.ccyyyy.netty.Protocol.model.MessageType;
import tech.ccyyyy.netty.Protocol.model.NettyMessage;

public class ChatChannelReqHandler extends ChannelHandlerAdapter{
	
	//消息缓存
	private static Queue<String> messageQueue=new ArrayBlockingQueue<>(10);
	//执行线程
	private static ExecutorService service=Executors.newFixedThreadPool(2);

	public  void createChatMessage(ChannelHandlerContext ctx) {
		//监控消息队列
		service.execute(new CreateChatMsgTask());
		service.execute(new BuildChatMsgTask(ctx));
	}
	

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NettyMessage message=(NettyMessage)msg;
		if(null!=message.getHeader() && MessageType.CHAT_RESP.value==message.getHeader().getType()) {
			//是聊天返回消息，输出
			System.out.println("客户端收到消息"+message.getBody());
		}
		if(null!=message.getHeader() && MessageType.CHAT_REQ.value==message.getHeader().getType()) {
			//是聊天消息，输出
			System.out.println("客户端收到消息"+message.getBody());
		}
		//返回
		ctx.fireChannelRead(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.fireExceptionCaught(cause);
	}
	
	 class CreateChatMsgTask implements Runnable{

		@SuppressWarnings("resource")
		@Override
		public void run() {
			while(true) {
				Scanner scanner=new Scanner(System.in);
				System.out.println("请输出文字");
				String text=scanner.nextLine();
				if(null!=text && !"".equals(text)) {
					messageQueue.add(text);
				}
			}
		}
	 }
	
	 class BuildChatMsgTask implements Runnable{
		
		private ChannelHandlerContext ctx;

		public BuildChatMsgTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			String message;
			while(true) {
				message=messageQueue.poll();
				if (null!=message) {
					NettyMessage nettyMessage=buildChatMessage(message);
					ctx.writeAndFlush(nettyMessage);
				}
			}
		}
		
	}
	
	
	private NettyMessage buildChatMessage(String messageContext) {
		NettyMessage message=new NettyMessage();
		Header header=new Header();
		header.setType(MessageType.CHAT_REQ.value);
		message.setHeader(header);
		message.setBody(messageContext);
		return message;
	}
	
	
	

}
