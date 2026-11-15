package com.example.restaurant.exception;

/**
 * 業務ルール違反（例：品切れのメニューを注文しようとした）を表す例外
 * メッセージはそのまま画面に表示する
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
