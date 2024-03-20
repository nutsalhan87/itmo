package ru.nutsalhan87.swt.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class Magazine {
    private String name;
    @Singular
    private List<Article<?>> articles;
}
