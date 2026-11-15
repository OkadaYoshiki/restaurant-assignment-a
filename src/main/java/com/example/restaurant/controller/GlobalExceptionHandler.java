package com.example.restaurant.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.restaurant.exception.BusinessException;

/**
 * 各Controllerで処理されなかった BusinessException をまとめて受け取り、エラー画面を表示する
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/business";
    }
}
