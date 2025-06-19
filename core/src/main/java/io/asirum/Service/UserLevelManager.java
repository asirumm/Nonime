package io.asirum.Service;

import io.asirum.SchemaObject.Region;
import io.asirum.SchemaObject.UserData;
import io.asirum.SchemaObject.UserLevel;

import java.util.ArrayList;

/**
 * Memanajemen level pada user
 */
public class UserLevelManager {
    private UserData userData;

    public UserLevelManager(UserData userData) {
        this.userData = userData;
    }

    public short getUserLevelByRegion(Region regionTarget){
        for (UserLevel data: userData.getLevel()){

            if(data.getName().equals(regionTarget.getName())){
                return data.getLevel();
            }
        }

        return 1;
    }

    /**
     * Mengisi data level pengguna berdasarkan daftar region:
     * <ul>
     *   <li>Jika pengguna baru dan belum memiliki data level, maka semua region akan diisi dengan level default.</li>
     *   <li>Jika terdapat region baru yang belum ada pada data pengguna, maka akan ditambahkan dengan level default.</li>
     * </ul>
     * level default = 1
     */
    public void fillUserLevels(ArrayList<Region> regions){
        Log.debug(getClass().getCanonicalName(),"memulai pengecekan apakah ada level region baru");

        final short DEFAULT_LEVEL  = 1;

        // Jika belum ada data level sama sekali
        // maka user akan diberikan level default di setiap region
        if (userData.getLevel().isEmpty()){

            Log.debug(getClass().getCanonicalName(),"user belum memiliki data sama sekali");

            for (Region region : regions){

                UserLevel temp = new UserLevel();
                temp.setName(region.getName());
                temp.setLevel(DEFAULT_LEVEL);

                userData.addLevel(temp);
            }

            Log.debug(getClass().getCanonicalName(),"telah ditambah level default ke user");

        } else {

            Log.debug(getClass().getCanonicalName(),"proses pengecekan data region user");

            ArrayList<UserLevel> userLevels = userData.getLevel();

            //  list nama region dari game data
            ArrayList<String> regionsAvailabelAtGameData = getRegionNameFromGameData(regions);

            // list nama region dari user data
            ArrayList<String> regionsUserHave = getRegionNameFromUserData(userLevels);

            // nama region yang user tidak miliki
            ArrayList<String> regionsWhereUserDoesntHave = new ArrayList<>();


            // Cari nama region yang tidak dimiliki user
            for (String name : regionsAvailabelAtGameData) {

                // stream apabila ditemukan maka true
                boolean notFound = regionsUserHave
                    .stream()
                    .noneMatch(userName -> userName.equals(name));

                if (notFound) {
                    regionsWhereUserDoesntHave.add(name);
                }
            }

            // Apabila ada data region yang belum dimiliki maka inject
            if (!regionsWhereUserDoesntHave.isEmpty()) {

                Log.info(getClass().getCanonicalName(),"ada region yang belum dimiliki oleh user, mulai menambahkan");

                for (String missing : regionsWhereUserDoesntHave) {

                    UserLevel temp = new UserLevel();

                    temp.setName(missing);
                    temp.setLevel(DEFAULT_LEVEL);

                    userLevels.add(temp);
                }

                userData.setLevel(userLevels);
            }
        }


    }

    private ArrayList<String> getRegionNameFromGameData(ArrayList<Region> regions){
        ArrayList<String> data= new ArrayList<>();
        // Kumpulkan semua nama region yang tersedia
        for (Region region : regions) {
            data.add(region.getName());
        }

        return data;
    }

    private ArrayList<String> getRegionNameFromUserData(ArrayList<UserLevel> userLevels){
        ArrayList<String> data = new ArrayList<>();

        for (UserLevel userLevel : userLevels){
            data.add(userLevel.getName());
        }
        return data;
    }

    public UserData getUserData() {
        return userData;
    }
}
