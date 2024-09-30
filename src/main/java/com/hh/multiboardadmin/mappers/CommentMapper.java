package com.hh.multiboardadmin.mappers;

import com.hh.multiboardadmin.domain.comment.CommentVo;
import com.hh.multiboardadmin.domain.comment.request.CommentRequestDto;
import com.hh.multiboardadmin.domain.comment.response.CommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    CommentVo toVo(CommentRequestDto commentRequestDto, Long memberId, Long postId);

    CommentResponseDto toDto(CommentVo commentVo);

    default List<CommentResponseDto> toDtoList(List<CommentVo> commentVoList) {
        return commentVoList.stream().map(this::toDto).collect(toList());
    }
}
