package com.Group.Rest.controller.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PageDto<T> {
    private T content;
    private Long totalElements;
    private int totalPages;

    public PageDto(T content) {
        this.content = content;
    }

}
