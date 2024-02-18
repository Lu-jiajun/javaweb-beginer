package fun.lujiajun.internalcommon.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PassengerUser {
    private  Long id;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String passengerPhone;
    private  String passengerName;
    private  byte passengerGender;
    private  byte state;
    private String profilePhoto;

//    public boolean getPassengerPhone() {
//    }
}
