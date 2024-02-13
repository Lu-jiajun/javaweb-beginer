package fun.lujiajun.apipassenger.request;

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
