package io.asirum.Service;

import io.asirum.SchemaObject.UserData;

public class UserEnergyManager {
    // jumah energi yang didapatkan per interval waktu
    private static final short ENERGY_GAIN_PER_INTERVAL = 3;
    // energi maks yang dapat diberikan dari off ke on
    // semisalnya user off 24 jam maka 24 x 3. Ini dibatasi
    private static final short MAX_ENERGY_INTERVAL_HOURS = 20;
    // rentan waktu interval untuk user diberikan reward dari
    // off
    private static final short ENERGY_INTERVAL_HOURS = 3;
    // reward setelah menang
    private static final short ENERGY_REWARD_FOR_WIN = 2;
    // maks energy user yang dapat ditampung
    private static final short MAXIMUM_ENERGY_CAN_HOLD = 600;


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
                ENERGY_INTERVAL_HOURS);

        if(status){
            // mendapatkan total energi yang perlu ditambaah ke user
            short energyReward = timeService
                .calculateEnergy(dateUser,MAX_ENERGY_INTERVAL_HOURS,ENERGY_GAIN_PER_INTERVAL);

            short currentEnergy = userData.getEnergy();
            short totalEnergy   = (short) (currentEnergy + energyReward);

            Log.info(getClass().getName(),"user berhak mendapatkan energy sebesar "+energyReward );

            // memberikan batas nilai energy yang dapat dimiliki user
            if(totalEnergy<=MAXIMUM_ENERGY_CAN_HOLD){
                userData.setEnergy(totalEnergy);
            }
        }
    }

    public void energyRewardAfterWinningGame(){
        short lastEnergy = userData.getEnergy();
        short amount = (short) (lastEnergy+ENERGY_REWARD_FOR_WIN);
        userData.setEnergy(amount);
        Log.info(getClass().getName(),"user mendapatkan penambahan energi, reward kemenangan sejumlah %s",ENERGY_REWARD_FOR_WIN);
    }
}
