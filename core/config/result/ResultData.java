package dev.patika.veterinarymanagementsystem.core.config.result;

import lombok.Getter;

@Getter
public class ResultData <T> extends Result {
    private T data;

        public ResultData(boolean status, String massage, String httpCode,T data) {
        super(status, massage, httpCode);
        this.data=data;

    }
}
