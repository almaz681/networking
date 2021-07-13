package server.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        System.out.println("Server received " + msg);
        context.write("You typed: " + msg + "; server time: " + System.currentTimeMillis() + "\r\n");
        context.writeAndFlush(Unpooled.EMPTY_BUFFER);
//        context.fireChannelRead(msg); // send msg to another handler for this channel
//            .addListener(ChannelFutureListener.CLOSE); // Close connection after response
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
