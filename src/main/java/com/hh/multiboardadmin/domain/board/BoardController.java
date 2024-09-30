package com.hh.multiboardadmin.domain.board;

import com.hh.multiboardadmin.common.dto.PaginationDto;
import com.hh.multiboardadmin.common.dto.SearchDto;
import com.hh.multiboardadmin.common.vo.SearchVo;
import com.hh.multiboardadmin.domain.category.CategoryService;
import com.hh.multiboardadmin.domain.category.response.CategoryResponseDto;
import com.hh.multiboardadmin.domain.comment.CommentService;
import com.hh.multiboardadmin.domain.file.FileService;
import com.hh.multiboardadmin.domain.post.PostService;
import com.hh.multiboardadmin.domain.post.response.PostResponseDto;
import com.hh.multiboardadmin.mappers.CategoryMapper;
import com.hh.multiboardadmin.mappers.FileMapper;
import com.hh.multiboardadmin.mappers.PostMapper;
import com.hh.multiboardadmin.mappers.SearchMapper;
import com.hh.multiboardadmin.utils.FileUtils;
import com.hh.multiboardadmin.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final FileService fileService;

    private final FileUtils fileUtils;
    private final PaginationUtils paginationUtils;

    private final PostMapper postModelMapper = Mappers.getMapper(PostMapper.class);
    private final SearchMapper searchModelMapper = Mappers.getMapper(SearchMapper.class);
    private final FileMapper fileModelMapper = Mappers.getMapper(FileMapper.class);
    private final CategoryMapper categoryModelMapper = Mappers.getMapper(CategoryMapper.class);

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/{boardType}")
    public String boardPage(@PathVariable String boardType, SearchDto searchDto, Model model) {
        Long typeId = BoardType.getTypeId(boardType);

        SearchVo voAsCount = searchModelMapper.toVoWithTypeId(searchDto, typeId);
        int count = postService.countAllBySearch(voAsCount);

        if(count >= 1) {
            PaginationDto paginationDto = paginationUtils.createPagination(count, searchDto);
            SearchVo voAsSearch = searchModelMapper.toVoWithPaginationAndTypeId(searchDto, paginationDto, typeId);

            List<PostResponseDto> postList = postModelMapper.toDtoList(postService.findAllBySearch(voAsSearch));
            model.addAttribute("postList", postList);

            model.addAttribute("pagination", paginationDto);

        } else {
            model.addAttribute("postList", Collections.emptyList());
            model.addAttribute("pagination", null);
        }

        List<CategoryResponseDto> categoryList = categoryModelMapper.toDtoList(categoryService.findAll(typeId));
        model.addAttribute("categoryList", categoryList);

        model.addAttribute("search", searchDto);
        model.addAttribute("boardType", boardType);

        return "board/main";
    }
}
