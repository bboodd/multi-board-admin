package com.hh.multiboardadmin.domain.category.response;

import lombok.Builder;

@Builder
public record CategoryResponseDto(
          Long categoryId
        , String categoryName
) {

}
