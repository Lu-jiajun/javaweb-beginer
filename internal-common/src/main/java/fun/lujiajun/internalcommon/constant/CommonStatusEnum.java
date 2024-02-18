package fun.lujiajun.internalcommon.constant;

import lombok.Data;
import lombok.Getter;

public enum CommonStatusEnum {

    VERIFICATION_CODE_ERROR(1099,"验证码不正确"),


    //token error
    TOKEN_ERROR(1199,"token error"),
    SUCCESS(1,"success"),
    FAIL(0,"fail")
    ;
    @Getter
    private int code;
    @Getter
    private String value;
    CommonStatusEnum(int code,String value){
        this.code=code;
        this.value=value;
    }
}
