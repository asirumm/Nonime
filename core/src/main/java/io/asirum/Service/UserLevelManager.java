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
        final short DEFAULT_LEVEL  = 1;

        // Jika belum ada data level sama sekali
        if (userData.getLevel().isEmpty()){
            for (Region region : regions){

                UserLevel temp = new UserLevel();
                temp.setName(region.getName());
                temp.setLevel(DEFAULT_LEVEL);

                userData.addLevel(temp);
            }

            // Cek jika jumlah region berubah (indikasi ada region baru)
        } else if (regions.size() != userData.getLevel().size()) {

            ArrayList<UserLevel> userLevels = userData.getLevel();
            ArrayList<String> regionNames = new ArrayList<>();
            ArrayList<String> regionUserHave = new ArrayList<>();
            ArrayList<String> regionUserDoesntHad = new ArrayList<>();

            // Kumpulkan semua nama region yang tersedia
            for (Region region : regions) {
                regionNames.add(region.getName());
            }

            // Kumpulkan semua nama level yang dimiliki user
            for (UserLevel userLevel : userLevels) {
                regionUserHave.add(userLevel.getName());
            }

            // Cari nama region yang tidak dimiliki user
            for (String name : regionNames) {
                boolean notFound = regionUserHave.stream()
                    .noneMatch(userName -> userName.equals(name));

                if (notFound) {
                    regionUserDoesntHad.add(name);
                }
            }

            // Apabila ada data region yang belum dimiliki maka inject
            if (!regionUserDoesntHad.isEmpty()) {
                for (String missing : regionUserDoesntHad) {
                    UserLevel temp = new UserLevel();
                    temp.setName(missing);
                    temp.setLevel(DEFAULT_LEVEL);

                    userLevels.add(temp);
                }

                userData.setLevel(userLevels);
            }
        }


    }

    public UserData getUserData() {
        return userData;
    }
}
