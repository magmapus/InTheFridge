package net.magmastone.inthefrige.network.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import net.magmastone.inthefrige.R;
import net.magmastone.inthefrige.network.FRGItem;
import net.magmastone.inthefrige.network.NetworkInterfacer;
import net.magmastone.inthefrige.network.UPCItem;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by alex on 3/4/15.
 */
public class GetShoppingListTask extends AsyncTask<String,String,List<UPCItem> > {

    public NetworkResults caller;
    public GetShoppingListTask(NetworkResults delegate){
        caller=delegate;
    }
    public Exception ex;
    @Override
    protected List<UPCItem> doInBackground(String... params) {
    //    try {
            NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
            List<UPCItem> list1 = iface.db.shoppinglist();

            return list1;
    //    }catch (Exception e){
    //        ex=e;
    //        return null;
    //    }
    }

    @Override
    protected void onPostExecute(List<UPCItem> upcItems) {
        if(upcItems != null) {
            super.onPostExecute(upcItems);
            Log.d("GetUPCTask", "Doing something with it...");
            caller.NetworkSuccess(upcItems);
        }else{
            caller.NetworkFailed(ex);
        }
    }

    public interface NetworkResults{
        public void NetworkSuccess(List<UPCItem> upcs);
        public void NetworkFailed(Exception e);
    }
}
