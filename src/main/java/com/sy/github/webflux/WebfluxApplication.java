package com.sy.github.webflux;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sy.github.webflux.common.CommonResult;
import com.sy.github.webflux.model.Answer;
import com.sy.github.webflux.model.AnswerOption;
import com.sy.github.webflux.repository.AnswerOptionRepository;
import com.sy.github.webflux.repository.AnswerRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Sherlock
 */
@EnableWebFlux
@RestController
@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @PostMapping("add")
    public Mono<Answer> println() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder().url("https://scjgzsfw.spc.net.cn/contestBank/findOne")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
        String body = client.newCall(request).execute().body().string();
        CommonResult<?> data = JSON.parseObject(body, CommonResult.class);
        Mono<Answer> save = Mono.empty();
        if (data.isSuccess()) {
            JSONObject object = (JSONObject) data.getData();
            Answer answer = new Answer();
            answer.setTcode(object.get("tcode").toString());
            answer.setTitle(object.get("title").toString());
            answer.setTianalysis(object.get("tianalysis").toString());
            answer.setTiclass(Integer.parseInt(object.get("ticlass").toString()));
            answer.setOrgLawName(object.get("orgLawName").toString());
            save = answerRepository.save(answer).doOnSuccess(item -> {
                JSONArray options = object.getJSONArray("options");
                for (int i = 0; i < options.size(); i++) {
                   JSONObject jsonObject = options.getJSONObject(i);
                   AnswerOption answerOption = new AnswerOption();
                   answerOption.setAnswerId(item.getId());
                   answerOption.setValue(jsonObject.getString("optionvalue"));
                   answerOption.setKey(jsonObject.getString("optionkey"));
                   answerOptionRepository.save(answerOption);
                }
            });
        }
        return save;
    }

    @GetMapping("/list")
    public Flux<String> list() {
        // 查询列表
        List<String> result = new ArrayList<>();
        result.add("trek");
        result.add("specialized");
        result.add("look");
        // 返回列表
        return Flux.fromIterable(result);
    }

}
