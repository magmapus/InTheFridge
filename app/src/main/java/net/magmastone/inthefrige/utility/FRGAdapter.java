package net.magmastone.inthefrige.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.magmastone.inthefrige.R;
import net.magmastone.inthefrige.network.FRGItem;
import net.magmastone.inthefrige.network.UPCItem;
import net.magmastone.inthefrige.network.tasks.GetFRGAllItemsTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alex on 4/16/15.
 */
public class FRGAdapter extends ArrayAdapter<FRGItem> implements GetFRGAllItemsTask.NetworkResults{
    private FRGItem[] frgs;
    private UPCItem[] upcs;
    private Context c;
    public FRGAdapter(Context context) {
        super(context, 0, new ArrayList<FRGItem>());
        c=context;
        new GetFRGAllItemsTask(this).execute("");
    }
    public void updateList(){
        new GetFRGAllItemsTask(this).execute("");
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FRGItem user = frgs[position];
        UPCItem it = upcs[position];
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_frgs, parent, false);
        }
        // Lookup view for data population
        TextView iName = (TextView) convertView.findViewById(R.id.itemname);
        TextView eDate = (TextView) convertView.findViewById(R.id.expiryDate);
        TextView lUser = (TextView) convertView.findViewById(R.id.lastUser);
        TextView nL = (TextView) convertView.findViewById(R.id.numLeft);
        ImageView iv = (ImageView) convertView.findViewById(R.id.imView);
        byte[] decodedString = Base64.decode(it.itempic, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
        iName.setText(it.itemname);
        eDate.setText("Expires on " + user.expiry);
        lUser.setText(user.name + " used it last");
        nL.setText(String.valueOf(user.quantity)+ " left");

        // Populate the data into the template view using the data object

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void NetworkSuccess(Object upcitems) {
        Log.d("NSuccess", "Got some new items?");
        HashMap<String,Object> k =(HashMap<String,Object>) upcitems;
         frgs=(FRGItem[])k.get("frg");
         upcs=(UPCItem[])k.get("upcs");
        List<FRGItem> lf = new ArrayList<FRGItem>(Arrays.asList(frgs));
        super.clear();
        super.addAll(frgs);


    }

    @Override
    public void NetworkFailed(Exception e) {
        String failureReason = e.getClass().getName();
        if(failureReason.equals("retrofit.RetrofitError")){
            Toast.makeText(c,"Network Error. Try connecting to WiFi!",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(c,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}