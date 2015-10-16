package org.rpc4j.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.rpc4j.common.RpcDecoder;
import org.rpc4j.common.RpcEncoder;
import org.rpc4j.common.RpcRequest;
import org.rpc4j.common.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RPC客户端（用于发送 RPC 请求）
 * 
 * @author huangyong
 * @since 1.0.0
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

	private static final Logger Logger = LoggerFactory.getLogger(RpcClient.class);

	private String host;

	private int port;

	private RpcResponse response;

	private final Object obj = new Object();

	public RpcClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
		this.response = response;

		synchronized (obj) {
			obj.notifyAll();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Logger.error("client caught exception", cause);
		ctx.close();
	}

	public RpcResponse send(RpcRequest request) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel channel) throws Exception {
					channel.pipeline().addLast(new RpcEncoder(RpcRequest.class))
							.addLast(new RpcDecoder(RpcResponse.class)).addLast(RpcClient.this);
				}
			}).option(ChannelOption.SO_KEEPALIVE, true);

			ChannelFuture future = bootstrap.connect(host, port).sync();
			future.channel().writeAndFlush(request).sync();

			synchronized (obj) {
				obj.wait();
			}

			if (response != null) {
				future.channel().closeFuture().sync();
			}
			return response;
		} finally {
			group.shutdownGracefully();
		}
	}

}
