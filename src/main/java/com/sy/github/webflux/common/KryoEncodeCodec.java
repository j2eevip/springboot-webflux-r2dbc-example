package com.sy.github.webflux.common;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.AbstractEncoder;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.EncoderHttpMessageWriter;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;

/**
 * @author Sherlock
 * @since 2021/10/9-21:58
 */
public class KryoEncodeCodec extends EncoderHttpMessageWriter<Object> {

    public KryoEncodeCodec() {
        super(new KryoEncoder());
    }


    private static class KryoEncoder extends AbstractEncoder<Object> {

        public KryoEncoder() {
            super(KryoHttpMessageConverter.KRYO);
        }

        @Override
        public boolean canEncode(@Nullable ResolvableType elementType, @Nullable MimeType mimeType) {
            return Objects.nonNull(mimeType) && KryoHttpMessageConverter.KRYO.compareTo(mimeType) == 0;
        }

        @Override
        public Flux<DataBuffer> encode(@Nullable Publisher<? extends Object> inputStream, @Nullable DataBufferFactory bufferFactory, @Nullable ResolvableType elementType, MimeType mimeType,
            Map<String, Object> hints) {
            assert inputStream != null;
            return Flux.from(inputStream).mapNotNull(bytes -> encodeValue(bytes, bufferFactory, elementType, mimeType, hints));
        }

        @Override
        public DataBuffer encodeValue(@Nullable Object value, @Nullable DataBufferFactory bufferFactory, @Nullable ResolvableType valueType, MimeType mimeType, Map<String, Object> hints) {
            if (Objects.nonNull(value) && Objects.nonNull(bufferFactory) && Objects.nonNull(valueType)) {
                DataBuffer dataBuffer = bufferFactory.allocateBuffer();
                KryoHttpMessageConverter.writeInternal(dataBuffer.asOutputStream(), value);
                if (logger.isDebugEnabled() && !Hints.isLoggingSuppressed(hints)) {
                    String logPrefix = Hints.getLogPrefix(hints);
                    logger.debug("class name: " + valueType.resolve() + "<" + mimeType.getType() + ">;" + logPrefix + "Writing " + dataBuffer.readableByteCount() + " bytes");
                }
                return dataBuffer;
            }
            return null;
        }

        @Override
        public List<MimeType> getEncodableMimeTypes(ResolvableType elementType) {
            return Collections.singletonList(KryoHttpMessageConverter.KRYO);
        }
    }
}
