package top.ddddvvvv.viaversion;

import com.viaversion.viaversion.api.connection.UserConnection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class ddddvvvvViaDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final UserConnection user;

    public ddddvvvvViaDecoder(UserConnection user) {
        this.user = user;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) {
        if (!bytebuf.isReadable()) {
            return;
        }

        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            user.transformClientbound(transformedBuf, DecoderException::new);
            out.add(transformedBuf.retain());
        } catch (Exception e) {
            if (e instanceof com.viaversion.viaversion.exception.CancelCodecException) {
                return;
            }
            out.add(bytebuf.retain());
        } finally {
            transformedBuf.release();
        }
    }
}