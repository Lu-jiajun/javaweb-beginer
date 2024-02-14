package fun.lujiajun.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerUser {
    private  Long id;
    private LocalDate gmtCreate;
    private LocalDate gmtModified;
    private String passengerPhone;
    private  String passengerName;
    private  byte passengerGender;
    private  byte state;

//    public boolean getPassengerPhone() {
//    }
}
