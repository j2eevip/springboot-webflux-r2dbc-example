package com.sy.github.webflux;

import com.sy.github.webflux.exception.EntityNotFoundException;
import com.sy.github.webflux.model.User;
import com.sy.github.webflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
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
    private UserRepository userRepository;

    @PostMapping("println")
    public Mono<User> println(@RequestBody User user) {
        return userRepository.save(user).switchIfEmpty(Mono.error(new EntityNotFoundException()));
    }

}
