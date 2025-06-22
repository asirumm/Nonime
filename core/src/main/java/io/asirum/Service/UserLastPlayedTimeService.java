package io.asirum.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserLastPlayedTimeService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH");


    /**
     * pengecekan apakah user berhak menerima reward offline
     */
    public boolean isUserEligibleToGetReward(String dateUser, int intervalTimeHours){
        LocalDateTime date = LocalDateTime.parse(dateUser,FORMATTER);
        LocalDateTime now = LocalDateTime.now();

        Log.debug(getClass().getName(),"waktu terakhir online %s : waktu sekarang %s",dateUser,now.format(FORMATTER));

        Duration duration = Duration.between(date, now);
        Log.debug(getClass().getName(),"user telah offline selama :  %s jam",duration.toHours());


        if (duration.toHours() >= intervalTimeHours) {
            return  true;
        } else {
            return false;
        }
    }

    public short calculateEnergy(String lastTimeLogin, short maxEnergyUserCanGet,short energyGain) {
        LocalDateTime userDate = LocalDateTime.parse(lastTimeLogin,FORMATTER);

        Duration duration = Duration.between(userDate, LocalDateTime.now());

        Log.debug(getClass().getName(),"menghitung reward energi yang didapatkan selama offline ");

        long hours = duration.toHours();
        short energyUserWillHave = (short) ( hours * energyGain);

        Log.info(getClass().getName(),"reward user dari offline sebesar : %s energi",energyUserWillHave);

        if(energyUserWillHave>=maxEnergyUserCanGet){
            return maxEnergyUserCanGet;
        }else {
            return energyUserWillHave;
        }
    }

    public String timeNow(){
        LocalDateTime now = LocalDateTime.now();
        return now.format(FORMATTER);
    }
}
