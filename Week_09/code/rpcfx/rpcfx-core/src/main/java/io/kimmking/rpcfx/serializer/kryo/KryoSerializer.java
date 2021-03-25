package io.kimmking.rpcfx.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * The type Kryo serializer.
 *
 * @author Liquid
 */
public class KryoSerializer {

    private KryoSerializer() {
    }

    public static class KryoSerializerHolder {
        private static KryoSerializer INSTANCE = new KryoSerializer();
    }

    public static KryoSerializer getInstance() {
        return KryoSerializerHolder.INSTANCE;
    }

    public byte[] serialize(final Object obj) throws Exception {
        byte[] bytes;
        Kryo kryo = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); Output output = new Output(outputStream)) {
            //获取kryo对象
            kryo = KryoPoolFactory.getInstance().get();
            kryo.writeObject(output, obj);
            bytes = output.toBytes();
            output.flush();
        } catch (IOException ex) {
            throw new RuntimeException("kryo serialize error" + ex.getMessage());
        } finally {
            KryoPoolFactory.getInstance().returnKryo(kryo);
        }
        return bytes;
    }

    public <T> T deSerialize(final byte[] param, final Class<T> clazz) throws Exception {
        T object;
        Kryo kryo = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            kryo = KryoPoolFactory.getInstance().get();
            Input input = new Input(inputStream);
            object = kryo.readObject(input, clazz);
            input.close();
        } catch (IOException e) {
            throw new RuntimeException("kryo deSerialize error" + e.getMessage());
        } finally {
            KryoPoolFactory.getInstance().returnKryo(kryo);
        }
        return object;
    }
}
