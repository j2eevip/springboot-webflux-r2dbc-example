package com.sy.github.webflux.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Sherlock
 * @since 2021/8/30-20:28
 */
@Data
@ToString
@EqualsAndHashCode
@Table("t_user")
public class User {
    @Id
    private Long id;
    private String name;
    private String gender;
}
