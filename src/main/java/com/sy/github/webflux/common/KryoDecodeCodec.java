package com.sy.github.webflux.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.AbstractDataBufferDecoder;
import org.springframework.core.codec.DecodingException;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.lang.Nullable;
import org.springframework.util.MimeType;

/**
 * @author Sherlock
 * @since 2021/10/9-21:00
 */
public class KryoDecodeCodec extends DecoderHttpMessageReader<Object> {

    public KryoDecodeCodec() {
        super(new KryoDecoder());
    }

    private static class KryoDecoder extends AbstractDataBufferDecoder<Object> {
        public KryoDecoder() {
            super(KryoHttpMessageConverter.KRYO);
        }

        @Override
        public boolean canDecode(@Nullable ResolvableType elementType, @Nullable MimeType mimeType) {
            return Objects.nonNull(mimeType) && KryoHttpMessageConverter.KRYO.compareTo(mimeType) == 0;
        }

        @Override
        public Object decode(@Nullable DataBuffer buffer, @Nullable ResolvableType targetType, MimeType mimeType, Map<String, Object> hints) throws DecodingException {
            Object result;
            if (Objects.nonNull(buffer) && Objects.nonNull(targetType)) {
                try {
                    InputStream inputStream = buffer.asInputStream();
                    result = KryoHttpMessageConverter.readInternal(inputStream, targetType.resolve());
                    DataBufferUtils.release(buffer);
                    if (logger.isDebugEnabled()) {
                        logger.debug("class name: " + targetType.resolve() + "<" + mimeType.getType() + ">;" + Hints.getLogPrefix(hints) + "Read" + inputStream.available() + "bytes");
                    }
                } catch (IOException|InstantiationException|IllegalAccessException e) {
                    logger.error("read inputStream error", e);
                    result = new Object();
                }
            } else {
                result = new Object();
            }
            return result;
        }

        @Override
        public List<MimeType> getDecodableMimeTypes(ResolvableType targetType) {
            return Collections.singletonList(KryoHttpMessageConverter.KRYO);
        }
    }
}
