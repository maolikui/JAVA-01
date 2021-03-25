package io.kimmking.rpcfx.common.codec;

import io.kimmking.rpcfx.serializer.kryo.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Rpcfx 编码器
 *
 * @author Liquid
 */
public class RpcfxEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcfxEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = KryoSerializer.getInstance().serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}