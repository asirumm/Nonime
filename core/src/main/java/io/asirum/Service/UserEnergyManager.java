package io.asirum.Service;

import io.asirum.SchemaObject.UserData;

public class UserEnergyManager {
    // jumah energi yang didapatkan per interval waktu
    public static final short ENERGY_GAIN_PER_INTERVAL = 3;
    // energi maks yang dapat diberikan dari off ke on
    // semisalnya user off 24 jam maka (24 : 2) x 3. Ini dibatasi
    public static final short MAX_ENERGY_INTERVAL = 30;
    // rentan waktu interval untuk user diberikan reward dari
    // off
    public static final short INTERVAL_HOURS = 2;
    // reward setelah menang
    public static final short ENERGY_REWARD_FOR_WIN = 1;
    // maks energy user yang dapat ditampung
    public static final short MAXIMUM_ENERGY_CAN_HOLD = 1000;


    private UserLastPlayedTimeService timeService;
    private UserData userData;

    public UserEnergyManager(UserData userData) {
        this.userData = userData;
        timeService = new UserLastPlayedTimeService();
    }

    /**
     * memproses setiap waktu untuk menambahkan energi pada interval waktu tertentu
     */
    public void userRewardProcessAfterOfflineSeveralTimes(){
        String dateUser = userData.getLastPlayedTime();

        // mengecek apakah user berhak diberikan reward off
        boolean status = timeService
            .isUserEligibleToGetReward(
                dateUser,
                INTERVAL_HOURS);

        if(status){
            // mendapatkan total energi yang perlu ditambaah ke user
            short energyReward = timeService
                .calculateEnergy(dateUser, MAX_ENERGY_INTERVAL,ENERGY_GAIN_PER_INTERVAL);

            int currentEnergy = userData.getEnergy();
            int totalEnergy   =  currentEnergy + energyReward;

            Log.info(getClass().getName(),"user berhak mendapatkan energy sebesar "+energyReward );

            // memberikan batas nilai energy yang dapat dimiliki user
            if(totalEnergy<=MAXIMUM_ENERGY_CAN_HOLD){
                userData.setEnergy(totalEnergy);
            }
        }
    }

    public void energyRewardAfterWinningGame(){
        int lastEnergy = userData.getEnergy();
        int amount =  (lastEnergy+ENERGY_REWARD_FOR_WIN);
        userData.setEnergy(amount);
        Log.info(getClass().getName(),"user mendapatkan penambahan energi, reward kemenangan sejumlah %s",ENERGY_REWARD_FOR_WIN);
    }
}
