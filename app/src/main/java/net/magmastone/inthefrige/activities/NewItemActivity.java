package net.magmastone.inthefrige.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.magmastone.inthefrige.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewItemActivity extends ActionBarActivity {
    String upcForItem;
    String imageB64;
    private void takePic() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        Intent intent = getIntent();
        upcForItem = intent.getStringExtra("UPC"); //if it's a string you stored.
        TextView tv= (TextView)findViewById(R.id.upc);
        tv.setText(upcForItem);

        Button butto= (Button) findViewById(R.id.picbutton);
        butto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                takePic();
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView iv=(ImageView)findViewById(R.id.itempic);
            iv.setImageBitmap(imageBitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            imageB64 = Base64.encodeToString(b, Base64.DEFAULT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_item, menu);
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

            Intent intent = new Intent();
            intent.putExtra("upc", upcForItem);
            intent.putExtra("image", imageB64);
            DatePicker dp = (DatePicker)findViewById(R.id.expiry);
            EditText nme = (EditText)findViewById(R.id.itemname);
            EditText tpe = (EditText)findViewById(R.id.itemType);

            int   day  = dp.getDayOfMonth();
            int   month= dp.getMonth();
            int   year = dp.getYear();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formatedDate = sdf.format(new Date(year, month, day));

            intent.putExtra("expiry", formatedDate);
            intent.putExtra("name",nme.getText().toString());
            intent.putExtra("type",tpe.getText().toString());
            this.setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
