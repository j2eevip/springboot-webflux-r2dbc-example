package com.sy.github.webflux;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sy.github.webflux.common.KryoHttpMessageConverter;
import com.sy.github.webflux.model.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Sherlock
 * @since 2021/9/10-21:01
 */
public class Test {

    /*public static void main(String[] args) throws InterruptedException {
        final int corePoolSize = Runtime.getRuntime().availableProcessors();
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(corePoolSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().dispatcher(dispatcher).build();
        long start = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 100; i++) {
            Request request = new Request.Builder()
                .url("http://localhost:8080/list").build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.out.println(e.getMessage());
                    countDownLatch.countDown();
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        System.out.println(response.body().string());
                    }
                    countDownLatch.countDown();
                    response.close();
                    call.cancel();
                }
            });
        }
        countDownLatch.await();
        ///50184
        System.out.println(System.currentTimeMillis() - start);
    }*/

    private static final MediaType mediaType = MediaType.parse("application/x-kryo");
    public static void main(String[] args) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Kryo kryo = new Kryo();
        kryo.register(User.class, 1);
        User user = new User();
        user.setId(2L);
        user.setName("lisi");
        user.setAge(1234);
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        Output output = new Output(b);
        kryo.writeObject(output, user);
        RequestBody requestBody = RequestBody.create(mediaType, output.toBytes());
        output.flush();
        Request request = new Request.Builder().post(requestBody)
            .url("http://localhost:8080/test/3")
            .addHeader("Content-Type", "application/x-kryo")
            .build();
        Response execute = okHttpClient.newCall(request).execute();
        if (execute.isSuccessful()) {
            Input input = new Input(Objects.requireNonNull(execute.body()).byteStream());
            System.out.println(kryo.readObject(input, User.class));
        }
    }
}
