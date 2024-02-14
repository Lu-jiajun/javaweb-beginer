package fun.lujiajun.internalcommon.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {
    private  String passengerPhone;
    private  String verificationCode;
//    public String getPassengerPhone(){
//        return  passengerPhone;
//    }
//    public  void setPassengerPhone(String passengerPhone){
//        this.passengerPhone = passengerPhone;
//    }
}
