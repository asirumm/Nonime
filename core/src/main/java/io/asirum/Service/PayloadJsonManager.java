package io.asirum.Service;

import com.badlogic.gdx.utils.Array;
import io.asirum.Constant;
import io.asirum.SchemaObject.Payload;

public class PayloadJsonManager {
    private JsonManager jsonManager;
    private Payload payloads;

    public PayloadJsonManager(){
        jsonManager = new JsonManager();
    }

    public Payload load(){
        payloads = jsonManager.parser(Payload.class, Constant.GAME_DATA_JSON);;

        Log.info(getClass().getName(),"telah berhasil load payload data");

        return payloads;
    }

    public Payload getPayload() {
        return payloads;
    }
}
