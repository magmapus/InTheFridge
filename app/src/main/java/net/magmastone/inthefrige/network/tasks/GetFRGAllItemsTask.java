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
public class GetFRGAllItemsTask extends AsyncTask<String,String,Object> {

    public NetworkResults caller;
    public GetFRGAllItemsTask(NetworkResults delegate){
        caller=delegate;
    }
    public Exception ex;
    @Override
      protected Object doInBackground(String... params) {
        try {
            NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
            List<FRGItem> list1 = iface.db.frgItems();
            FRGItem[] items1 = list1.toArray(new FRGItem[list1.size()]);
            List<UPCItem> list2 = iface.db.frgupcItems();
            UPCItem[] items2 = list2.toArray(new UPCItem[list2.size()]);
            HashMap<String, Object> its = new HashMap<String, Object>();
            its.put("frg", items1);
            its.put("upcs", items2);

            return its;
        }catch (Exception e){
            ex=e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object upcItems) {
        if(upcItems != null) {
            super.onPostExecute(upcItems);
            Log.d("GetUPCTask", "Doing something with it...");
            caller.NetworkSuccess(upcItems);
        }else{
            caller.NetworkFailed(ex);
        }
    }

    public interface NetworkResults{
        public void NetworkSuccess(Object upcitems);
        public void NetworkFailed(Exception e);
    }
}
