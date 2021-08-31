package com.sy.github.webflux.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Sherlock
 * @since 2021/8/31-21:59
 */
@Data
@ToString
@EqualsAndHashCode
@Table("t_options")
public class AnswerOption {
    @Id
    private Integer id;
    private Integer answerId;
    private String key;
    private String value;

}
