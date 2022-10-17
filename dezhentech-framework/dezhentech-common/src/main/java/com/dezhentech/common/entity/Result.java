package com.dezhentech.common.entity;

import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;


/**
 * 统一返回结果集
 *
 * @author yfcui
 * @date 2022/10/17
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -2972287209328006642L;
    private int code;

    private String msg;

    private T data;

    private Result() {

    }

    /**
     * 私有化构造函数
     *
     * @param code 代码
     * @param msg  消息
     * @param data 数据
     */
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 默认操作成功
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success() {
        return Result.successMsg("操作成功");
    }


    /**
     * 带返回数据的成功
     *
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(T data) {
        return Result.success("操作成功", data);
    }


    /**
     * 仅包含成功消息
     *
     * @param msg 消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> successMsg(String msg) {
        return Result.success(msg, null);
    }


    /**
     * 包含消息和数据
     *
     * @param msg  消息
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(String msg, T data) {
        return Result.success(HttpStatus.HTTP_OK, msg, data);
    }


    /**
     * 自定义code和返回消息
     *
     * @param code 代码
     * @param msg  消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(int code, String msg) {
        return Result.success(code, msg, null);
    }


    /**
     * 成功
     *
     * @param code 代码
     * @param msg  味精
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }


    /**
     * 操作失败
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error() {
        return Result.error("操作失败");
    }


    /**
     * 带错误信息的操作失败
     *
     * @param msg 味精
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error(String msg) {
        return Result.error(msg, null);
    }


    /**
     * 带错误信息和数据的失败
     *
     * @param msg  消息
     * @param data 数据
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(HttpStatus.HTTP_INTERNAL_ERROR, msg, data);
    }


    /**
     * 带code和消息的失败
     *
     * @param code 代码
     * @param msg  消息
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

}
