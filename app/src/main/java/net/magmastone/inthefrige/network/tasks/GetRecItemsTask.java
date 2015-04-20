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
import net.magmastone.inthefrige.network.RECItem;
import net.magmastone.inthefrige.network.UPCItem;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by alex on 3/4/15.
 */
public class GetRecItemsTask extends AsyncTask<String,String,List<RECItem> > {

    public NetworkResults caller;
    public GetRecItemsTask(NetworkResults delegate){
        caller=delegate;
    }
    public Exception ex;
    @Override
    protected List<RECItem> doInBackground(String... params) {
        //    try {
        NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
        List<RECItem> list1 = iface.db.getRecs();

        return list1;
        //    }catch (Exception e){
        //        ex=e;
        //        return null;
        //    }
    }

    @Override
    protected void onPostExecute(List<RECItem> upcItems) {
        if(upcItems != null) {
            super.onPostExecute(upcItems);
            caller.NetworkSuccess(upcItems);
        }else{
            caller.NetworkFailed(ex);
        }
    }

    public interface NetworkResults{
        public void NetworkSuccess(List<RECItem> upcs);
        public void NetworkFailed(Exception e);
    }
}
