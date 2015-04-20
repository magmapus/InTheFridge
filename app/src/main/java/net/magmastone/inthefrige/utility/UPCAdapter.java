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
import net.magmastone.inthefrige.network.tasks.GetShoppingListTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alex on 4/16/15.
 */
public class UPCAdapter extends ArrayAdapter<UPCItem> implements GetShoppingListTask.NetworkResults{

    private Context c;
    public UPCAdapter(Context context) {
        super(context, 0, new ArrayList<UPCItem>());
        c=context;
        new GetShoppingListTask(this).execute("");
    }
    public void updateList(){
        new GetShoppingListTask(this).execute("");
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UPCItem it = super.getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_upcs, parent, false);
        }
        // Lookup view for data population
        TextView iName = (TextView) convertView.findViewById(R.id.itemnameUPC);
        ImageView iv = (ImageView) convertView.findViewById(R.id.imViewUPC);
        byte[] decodedString = Base64.decode(it.itempic, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
        iName.setText(it.itemname);


        // Populate the data into the template view using the data object

        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void NetworkSuccess(List<UPCItem> ups) {
        super.clear();
        super.addAll(ups);

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