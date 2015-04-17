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
    public Exception ex;
    public NewUPCTask(NetworkResults a){
        caller=a;
    }

        @Override
    protected UPCItem doInBackground(String... params) {
    try{
        NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
        UPCItem item=iface.db.postItem(params[0],params[1],params[2],params[3],params[4]);
        Log.d("NewUPCTask", "Posted a UPC");
        return item;
        }catch (Exception e){
        ex=e;
        return null;
    }
    }

    @Override
    protected void onPostExecute(UPCItem upcItem) {
    if(upcItem!= null){
        super.onPostExecute(upcItem);
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
