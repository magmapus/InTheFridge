package net.magmastone.inthefrige.network.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.magmastone.inthefrige.network.FRGItem;
import net.magmastone.inthefrige.network.NetworkInterfacer;
import net.magmastone.inthefrige.network.UPCItem;

/**
 * Created by alex on 3/4/15.
 */
public class GetFRGStatusTask extends AsyncTask<String,String,FRGItem> {

    public NetworkResults caller;
    public GetFRGStatusTask(NetworkResults delegate){
        caller=delegate;
    }
    ;    @Override
         protected FRGItem doInBackground(String... params) {

        NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
        FRGItem item=iface.db.getStatus(params[0]);
        return item;
    }

    @Override
    protected void onPostExecute(FRGItem upcItem) {
        super.onPostExecute(upcItem);
        Log.d("GetUPCTask", "Doing something with it...");
        caller.NetworkSuccess(upcItem);
    }

    public interface NetworkResults{
        public void NetworkSuccess(FRGItem it);
        public void NetworkFailed(String reason);
    }
}
