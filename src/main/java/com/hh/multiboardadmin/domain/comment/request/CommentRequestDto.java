package com.hh.multiboardadmin.domain.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CommentRequestDto (
          Long commentId
        , @NotBlank(message = "댓글을 입력해 주세요")
          String content
) {
}
