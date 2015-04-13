package net.magmastone.inthefrige.network;

import retrofit.RestAdapter;

/**
 * Created by alex on 2/28/15.
 */
public class NetworkInterfacer {
    public UPCDatabase db;
    public NetworkInterfacer(String endpoint){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://"+endpoint)
                .build();
        db=restAdapter.create(UPCDatabase.class);
    }

}
