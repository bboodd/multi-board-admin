package com.hh.multiboardadmin.aop;

import com.hh.multiboardadmin.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 커스텀 exception 한곳에서 처리
     * @param exception - custom exception
     * @return - error page
     */
    @ExceptionHandler(CustomException.class)
    public String handleCustomException(CustomException exception, Model model) {
        model.addAttribute("status", exception.getErrorCode().defaultHttpStatus());
        model.addAttribute("code", exception.getErrorCode().name());
        model.addAttribute("msg", exception.getErrorCode().defaultMessage());
        return "error/error";
    }

    /**
     * 400에러
     * 요청 객체의 validation을 수행할 때, MethodArgumentNotValidException이 발생
     * 각 검증 어노테이션 별로 지정해놨던 메시지를 응답
     * @param e - 에러
     * @return 400 + message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidException(MethodArgumentNotValidException e, Model model) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        model.addAttribute("status", 400);
        model.addAttribute("code", "BAD_REQUEST");
        model.addAttribute("msg", message);
        return "error/error";
    }
}
