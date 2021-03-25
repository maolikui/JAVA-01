package io.kimmking.rpcfx.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * The type Kryo pool factory.
 *
 * @author Liquid
 */
public final class KryoPoolFactory implements KryoFactory {

    private static final KryoPoolFactory FACTORY = new KryoPoolFactory();

    private KryoPool pool = new KryoPool.Builder(this).softReferences().build();

    private KryoPoolFactory() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static KryoPoolFactory getInstance() {
        return FACTORY;
    }

    /**
     * Get kryo.
     *
     * @return the kryo
     */
    public Kryo get() {
        return pool.borrow();
    }

    /**
     * Return kryo.
     *
     * @param kryo the kryo
     */
    public void returnKryo(final Kryo kryo) {
        if (Objects.nonNull(kryo)) {
            pool.release(kryo);
        }
    }

    @Override
    public Kryo create() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        //register serializer
        kryo.register(BigDecimal.class, new DefaultSerializers.BigDecimalSerializer());
        kryo.register(BigInteger.class, new DefaultSerializers.BigIntegerSerializer());
        return kryo;
    }
}
