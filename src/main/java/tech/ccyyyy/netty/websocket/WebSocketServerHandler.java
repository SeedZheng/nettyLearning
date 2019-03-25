package tech.ccyyyy.netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object>{
	
	private WebSocketServerHandshaker handshaker;

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		//HTTP接入
		if(msg instanceof FullHttpRequest) {
			handlerHttpRequest(ctx,(FullHttpRequest)msg);
		}
		if(msg instanceof WebSocketFrame) {
			handlerWebSocketFrame(ctx,(WebSocketFrame)msg);
		}
	}
	
	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}



	private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// 判断是否是关闭链路的指令
		if(frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		//是否是PING消息
		if(frame instanceof PingWebSocketFrame) {
			ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		//是否是文本消息
		if(!(frame instanceof TextWebSocketFrame)) {
			throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
		}
		//返回应答消息
		String result=((TextWebSocketFrame)frame).text();
		ctx.channel().write(new TextWebSocketFrame("服务器收到消息："+result));
		
		
	}

	private void handlerHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception{
		//如果HTTP解码失败，返回HTTP异常
		if(!req.getDecoderResult().isSuccess() || (!"Websocket".equalsIgnoreCase(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		//构造握手相应返回
		WebSocketServerHandshakerFactory factory=
				new WebSocketServerHandshakerFactory("ws://localhost:8888/websocket", null, false);
		handshaker=factory.newHandshaker(req);
		if(null==handshaker) {
			WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		}else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
			FullHttpResponse defaultFullHttpResponse) {
		// 返回应答给客户端
		if(defaultFullHttpResponse.getStatus().code()!=200) {
			ByteBuf buf=Unpooled.copiedBuffer(defaultFullHttpResponse.getStatus().toString(),CharsetUtil.UTF_8);
			defaultFullHttpResponse.content().writeBytes(buf);
			buf.release();
			HttpHeaders.setContentLength(defaultFullHttpResponse,defaultFullHttpResponse.content().readableBytes());
		}
		//如果连接非 keep-alive 关闭连接
		ChannelFuture future=ctx.channel().writeAndFlush(defaultFullHttpResponse);
		if(!HttpHeaders.isKeepAlive(req) || defaultFullHttpResponse.getStatus().code()!=200) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
		
		
	}

}
