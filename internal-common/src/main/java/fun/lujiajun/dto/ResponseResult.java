package fun.lujiajun.dto;
import fun.lujiajun.constant.CommonStatusEnum;
import  lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private  int code;
    private  String message;
    T data;

    /*


     */
    public static <T> ResponseResult success(T data){
        return  new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data);
    }

    public  static ResponseResult fail(int code,String message){
        return  new ResponseResult().setCode(code).setMessage(message);
    }

    public static ResponseResult fail(int code,String message,String data){
        return  new ResponseResult().setCode(code).setMessage(message).setData(data);
    }

    public static <T> ResponseResult fail(T data){
        return  new ResponseResult().setData(data);
    }

}
