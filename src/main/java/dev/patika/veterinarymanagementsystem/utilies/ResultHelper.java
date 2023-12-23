package dev.patika.veterinarymanagementsystem.utilies;

import dev.patika.veterinarymanagementsystem.core.config.result.Result;
import dev.patika.veterinarymanagementsystem.core.config.result.ResultData;
import dev.patika.veterinarymanagementsystem.dto.response.CursorResponse;
import org.springframework.data.domain.Page;

public class ResultHelper {
    public static  <T>ResultData<T> created(T data){
        return new ResultData<>(true,MSG.CREATED,"201",data);
    }
    public static <T>ResultData<T> validateError(T data){
        return new ResultData<>(false,MSG.VALİDATE_ERROR,"400",data);
    }
    public static <T>ResultData<T> succes(T data){
        return new ResultData<>(true,MSG.OK,"200 ",data);
    }
    public static Result notFoundError(String msg){
        return new Result( false,msg,"404 ");
    }
    public static <T> ResultData<CursorResponse<T>> cursor( Page<T> pageData){

        CursorResponse<T> cursor=new CursorResponse<>();
        cursor.setItems(pageData.getContent());
        cursor.setPageNumber(pageData.getNumber());
        cursor.setPageSize(pageData.getSize());
        cursor.setAtotalElements(pageData.getTotalElements());
        return ResultHelper.succes(cursor);
    }
    public static Result ok(){
        return new Result(true,MSG.OK,"200");


    }
}
