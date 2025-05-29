package io.asirum.Service;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserLastPlayedTimeService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH");


    /**
     * pengecekan apakah user berhak menerima reward offline
     */
    public boolean hasUserPlayedLongEnough(String dateUser,int intervalTimeHours){
        LocalDateTime date = LocalDateTime.parse(dateUser,FORMATTER);
        LocalDateTime now = LocalDateTime.now();

        Log.debug(getClass().getName(),"user date : "+date+" date now : "+now);

        Duration duration = Duration.between(date, now);
        Log.debug(getClass().getName(),"user telah offline selama :  "+duration.toHours());


        if (duration.toHours() >= intervalTimeHours) {
            return  true;
        } else {
            return false;
        }
    }

    public short calculateEnergy(String dateUser, short maxEnergy,short energyGain) {
        LocalDateTime date = LocalDateTime.parse(dateUser,FORMATTER);

        Duration duration = Duration.between(date, LocalDateTime.now());

        Log.debug(getClass().getName(),"calculate energy duration "+duration.toHours());

        long hours = duration.toHours();
        short result = (short) ( hours * energyGain);

        Log.info(getClass().getName(),">>> reward user dari offline sebesar : "+result +" energi");

        if(result>=maxEnergy){
            return maxEnergy;
        }else {
            return result;
        }
    }

    public String timeNow(){
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMATTER);
    }
}
