package com.sy.github.webflux.repository;

import com.sy.github.webflux.model.Answer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sherlock
 * @since 2021/8/30-21:17
 */
@Repository
public interface AnswerRepository extends R2dbcRepository<Answer, Integer> {

}
