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
    public Activity calledActivity;

    public GetUPCTask(Activity a){
        calledActivity=a;
    }
;    @Override
    protected UPCItem doInBackground(String... params) {

        NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
        UPCItem item=iface.db.getItem(params[0]);
        Log.d("GetUPCTask", "Got a UPC....");
        return item;
    }

    @Override
    protected void onPostExecute(UPCItem upcItem) {
        super.onPostExecute(upcItem);
        UPCItem it = upcItem;
        Log.d("GetUPCTask", "Doing something with it...");
        if(it.status.equals("N")) {
          //  Intent myIntent = new Intent(calledActivity, NewItemActivity.class);
          //  myIntent.putExtra("UPC", it.upc); //Optional parameters
          //  Log.d("BroadcastReceiver", "Didn't find, setting up...");
          //  calledActivity.startActivityForResult(myIntent, 1023);
            //Need a rework here.

        }else{
            Context ctx = calledActivity.getApplicationContext();
            CharSequence text = "Item found!"+it.itemname;
            int duration = Toast.LENGTH_SHORT;
            Log.d("BroadcastReceiver", "Found!");
            Toast toast = Toast.makeText(ctx, text, duration);
            toast.show();

        }
    }
}
