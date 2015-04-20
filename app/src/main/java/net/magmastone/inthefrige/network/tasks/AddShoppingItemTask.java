package net.magmastone.inthefrige.network.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import net.magmastone.inthefrige.network.NetworkInterfacer;
import net.magmastone.inthefrige.network.UPCItem;

import retrofit.client.Response;

/**
 * Created by alex on 3/4/15.
 */
public class AddShoppingItemTask extends AsyncTask<String,String,String> {

    public NetworkResults caller;
    public Exception ex;
    public AddShoppingItemTask(NetworkResults a){
        caller=a;
    }

    @Override
    protected String doInBackground(String... params) {
        //try{
            NetworkInterfacer iface = new NetworkInterfacer("192.168.0.183:8080");
            Response r =iface.db.postListItem(params[0]);
            return "G";
        //}catch (Exception e){
         //   ex=e;
         //   return null;
       // }
    }

    @Override
    protected void onPostExecute(String upcItem) {
        if(upcItem!= null){
            super.onPostExecute(upcItem);
            caller.NetworkSuccess();
        }else{
            caller.NetworkFailed(ex);
        }
    }

    public interface NetworkResults{
        public void NetworkSuccess();
        public void NetworkFailed(Exception e);
    }
}
