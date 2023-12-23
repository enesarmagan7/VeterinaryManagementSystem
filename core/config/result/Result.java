package dev.patika.veterinarymanagementsystem.core.config.result;

import lombok.Getter;
@Getter
public class Result {
    private boolean status;
    private String massage;
    private String httpCode;


    public Result(boolean status, String massage, String httpCode) {
        this.status = status;
        this.massage = massage;
        this.httpCode = httpCode;
    }


}
