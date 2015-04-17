package net.magmastone.inthefrige.network.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.magmastone.inthefrige.network.NetworkInterfacer;
import net.magmastone.inthefrige.network.UPCItem;

/**
 * Created by alex on 3/4/15.
 */
public class GetUPCTask extends AsyncTask<String,String,UPCItem> {

    public NetworkResults caller;
    public GetUPCTask(NetworkResults delegate){
        caller=delegate;
    }

    public Exception ex;
    @Override
    protected UPCItem doInBackground(String... params) {
        try {
            NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
            UPCItem item = iface.db.getItem(params[0]);
            Log.d("GetUPCTask", "Got a UPC....");
            return item;
        }catch (Exception e){
            ex=e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(UPCItem upcItem) {
        if(upcItem != null) {
            super.onPostExecute(upcItem);
            Log.d("GetUPCTask", "Doing something with it...");
            caller.NetworkSuccess(upcItem);
        }else{
            caller.NetworkFailed(ex);
        }
    }

    public interface NetworkResults{
        public void NetworkSuccess(UPCItem it);
        public void NetworkFailed(Exception e);
    }
}
