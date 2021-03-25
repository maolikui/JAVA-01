package io.kimmking.rpcfx.common.codec;

import io.kimmking.rpcfx.serializer.kryo.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Rpcfx 解码器
 *
 * @author Liquid
 */
public class RpcfxDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RpcfxDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(KryoSerializer.getInstance().deSerialize(data, genericClass));
    }
}
