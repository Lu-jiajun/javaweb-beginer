package fun.lujiajun.apipassenger.request;

public class VerificationCodeDTO {
    private  String passengerPhone;
    public String getPassengerPhone(){
        return  passengerPhone;
    }
    public  void setPassengerPhone(String passengerPhone){
        this.passengerPhone = passengerPhone;
    }
}
