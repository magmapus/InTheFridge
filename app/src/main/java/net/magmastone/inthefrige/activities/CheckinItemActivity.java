package net.magmastone.inthefrige.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import net.magmastone.inthefrige.R;
import net.magmastone.inthefrige.network.FRGItem;
import net.magmastone.inthefrige.network.UPCItem;
import net.magmastone.inthefrige.network.tasks.GetUPCTask;
import net.magmastone.inthefrige.network.tasks.SetFRGStatusTask;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CheckinItemActivity extends ActionBarActivity {

    private TextView tv;
    private ImageView iv;
    private String upc;
    private SeekBar sbar;
    private DatePicker dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_item);
        sbar= (SeekBar) findViewById(R.id.newitembar);
        Intent i =getIntent();
        upc = i.getStringExtra("UPC");
        Log.d("CheckIn",upc);
        dp= (DatePicker) findViewById(R.id.checkinexpiry);
        tv = (TextView) findViewById(R.id.newitemquant);
        tv.setText("1");
        iv = (ImageView) findViewById(R.id.newitemimageView);
        new GetUPCTask(new GetUPCTask.NetworkResults(){
            @Override
            public void NetworkSuccess(UPCItem it){
                if(it.status.equals("N")){

                }else{

                    byte[] decodedString = Base64.decode(it.itempic, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    iv.setImageBitmap(decodedByte);
                }
            }
            @Override
            public void NetworkFailed(String reason){

            }
        }).execute(upc);
        sbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText(String.valueOf(progress+1));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_checkin_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            int   day  = dp.getDayOfMonth();
            int   month= dp.getMonth();
            int   year = dp.getYear();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formatedDate = sdf.format(new Date(year, month, day));

            new SetFRGStatusTask(new SetFRGStatusTask.NetworkResults() {
                @Override
                public void NetworkSuccess(FRGItem it) {
                    finish();
                }

                @Override
                public void NetworkFailed(String reason) {

                }
            }).execute(upc,String.valueOf(tv.getText()),formatedDate);

        }

        return super.onOptionsItemSelected(item);
    }
}
