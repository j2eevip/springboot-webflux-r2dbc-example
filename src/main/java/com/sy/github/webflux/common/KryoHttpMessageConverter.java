package com.sy.github.webflux.common;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sy.github.webflux.model.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import org.springframework.util.MimeType;

/**
 * @author Sherlock
 * @since 2021/10/9-21:02
 */
public final class KryoHttpMessageConverter {
    public static final MimeType KRYO = new MimeType("application", "x-kryo");

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(User.class, 1);
        return kryo;
    });

    public static <T> T readInternal(InputStream body, Class<T> clazz) throws IOException, InstantiationException, IllegalAccessException {
        if (Objects.nonNull(body) && body.available() >= 1) {
            Input input = new Input(body);
            return KRYO_THREAD_LOCAL.get().readObject(input, clazz);
        }
        return clazz.newInstance();
    }

    public static <T> void writeInternal(OutputStream outputStream, T t) {
        Output output = new Output(outputStream);
        KRYO_THREAD_LOCAL.get().writeObject(output, t);
        output.flush();
    }
}
