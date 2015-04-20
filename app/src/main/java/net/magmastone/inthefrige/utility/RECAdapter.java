package net.magmastone.inthefrige.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import net.magmastone.inthefrige.network.RECItem;
import net.magmastone.inthefrige.network.UPCItem;
import net.magmastone.inthefrige.network.tasks.GetFRGAllItemsTask;
import net.magmastone.inthefrige.network.tasks.GetRecItemsTask;
import net.magmastone.inthefrige.network.tasks.GetShoppingListTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alex on 4/16/15.
 */
public class RECAdapter extends ArrayAdapter<RECItem> implements GetRecItemsTask.NetworkResults{

    private Context c;
    public RECAdapter(Context context) {
        super(context, 0, new ArrayList<RECItem>());
        c=context;
        new GetRecItemsTask(this).execute("");
    }
    public void updateList(){
        new GetRecItemsTask(this).execute("");
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RECItem it = super.getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_rec, parent, false);
        }

        TextView iName = (TextView) convertView.findViewById(R.id.editTextRec);
        TextView iTime = (TextView) convertView.findViewById(R.id.timetRec);
        iTime.setText(it.time);
        ImageView iv = (ImageView) convertView.findViewById(R.id.imageViewRec);
        iv.setAlpha(0.5f);
        new DownloadImageTask(iv).execute(it.image);
        iName.setText(it.name);

        return convertView;
    }

    @Override
    public void NetworkSuccess(List<RECItem> ups) {
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
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}