package net.magmastone.inthefrige.network.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import net.magmastone.inthefrige.network.FRGItem;
import net.magmastone.inthefrige.network.NetworkInterfacer;
import net.magmastone.inthefrige.network.UPCItem;

/**
 * Created by alex on 3/4/15.
 */
public class SetFRGStatusTask extends AsyncTask<String,String,FRGItem> {

    public NetworkResults caller;
    public SetFRGStatusTask(NetworkResults a){
        caller=a;
    }

    @Override
    protected FRGItem doInBackground(String... params) {
        NetworkInterfacer iface = new NetworkInterfacer("10.23.131.68:8080");
        FRGItem item;
        if(Integer.valueOf(params[1]).intValue() > 0) {
            item = iface.db.postFrg(params[0], "alex", params[1], params[2]);
        }else{
             item = iface.db.remItem(params[0], "alex");
        }
        Log.d("NewUPCTask", "Posted a UPC");
        return item;
    }

    @Override
    protected void onPostExecute(FRGItem upcItem) {
        super.onPostExecute(upcItem);
        if(caller != null) {
            caller.NetworkSuccess(upcItem);
        }
    }

    public interface NetworkResults{
        public void NetworkSuccess(FRGItem it);
        public void NetworkFailed(String reason);
    }
}
