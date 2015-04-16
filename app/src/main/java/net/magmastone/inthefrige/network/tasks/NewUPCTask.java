package net.magmastone.inthefrige.network.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import net.magmastone.inthefrige.network.NetworkInterfacer;
import net.magmastone.inthefrige.network.UPCItem;

/**
 * Created by alex on 3/4/15.
 */
public class NewUPCTask extends AsyncTask<String,String,UPCItem> {

    public NetworkResults caller;
    public NewUPCTask(NetworkResults a){
        caller=a;
    }

        @Override
    protected UPCItem doInBackground(String... params) {
        NetworkInterfacer iface = new NetworkInterfacer("10.23.131.68:8080");
        UPCItem item=iface.db.postItem(params[0],params[1],params[2],params[3],params[4]);
        Log.d("NewUPCTask", "Posted a UPC");
        return item;
    }

    @Override
    protected void onPostExecute(UPCItem upcItem) {
        super.onPostExecute(upcItem);
        caller.NetworkSuccess(upcItem);
    }

    public interface NetworkResults{
        public void NetworkSuccess(UPCItem it);
        public void NetworkFailed(String reason);
    }
}
