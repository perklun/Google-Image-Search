package activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.perklim.google_image_search.R;

import models.ImageResult;

/**
 * Created by PerkLun on 5/17/2015.
 */
public class FullScreenActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        //remove actionbar
        getSupportActionBar().hide();
       // getActionBar().hide();
        //need to cast object back
        ImageResult image = (ImageResult) getIntent().getSerializableExtra("image");
        ImageView fullImage = (ImageView) findViewById(R.id.ivFullImage);
        Picasso.with(this).load(image.getFullURL()).into(fullImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_screen, menu);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
