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
    public Activity calledActivity;

    public NewUPCTask(Activity a){
        calledActivity=a;
    }

        @Override
    protected UPCItem doInBackground(String... params) {
        NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
        UPCItem item=iface.db.postItem(params[0],params[1],params[2],params[3],params[4]);
        Log.d("NewUPCTask", "Posted a UPC");
        return item;
    }

    @Override
    protected void onPostExecute(UPCItem upcItem) {
        super.onPostExecute(upcItem);
    }
}
