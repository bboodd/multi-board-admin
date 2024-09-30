package com.hh.multiboardadmin.mappers;

import com.hh.multiboardadmin.common.dto.PaginationDto;
import com.hh.multiboardadmin.common.dto.SearchDto;
import com.hh.multiboardadmin.common.vo.SearchVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SearchMapper {
    SearchVo toVoWithTypeId(SearchDto searchDto, Long typeId);

    default SearchVo toVoWithPaginationAndTypeId(SearchDto searchDto, PaginationDto paginationDto, Long typeId) {
        int page = searchDto.page();

        if(page > paginationDto.totalPageCount()) {
            page = paginationDto.totalPageCount();
        }

        return SearchVo.builder()
                .startDate(searchDto.startDate())
                .endDate(searchDto.endDate())
                .categoryId(searchDto.categoryId())
                .keyword(searchDto.keyword())
                .page(page)
                .recordSize(searchDto.recordSize())
                .pageSize(searchDto.pageSize())
                .limitStart(paginationDto.limitStart())
                .orderBy(searchDto.orderBy())
                .sortBy(searchDto.sortBy())
                .typeId(typeId)
                .build();
    }
}
