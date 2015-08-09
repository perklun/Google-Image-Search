package activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import net.perklim.google_image_search.R;

/**
 *  Activity that allows user to select their search filters
 */
public class SettingsActivity extends ActionBarActivity {

    // Views for available filters
    private EditText etSiteFilter;
    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;

    /**
     * Assign views to id
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        Intent i = getIntent();
        spImageSize.setSelection(i.getIntExtra("ImageSize",0));
        spColorFilter.setSelection(i.getIntExtra("ColorFilter",0));
        spImageType.setSelection(i.getIntExtra("ImageType",0));
        etSiteFilter.setText(i.getStringExtra("SiteFilter"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    /**
     * Method called when button to save filters is pressed
     * Passes new search filters back to parent activity
     *
     * @param v
     */
    public void onSubmit(View v){
        //create intent and pass back parameters
        Intent settings = new Intent();
        settings.putExtra("SiteFilter", etSiteFilter.getText().toString());
        settings.putExtra("ImageSize", spImageSize.getSelectedItemPosition());
        settings.putExtra("ColorFilter", spColorFilter.getSelectedItemPosition());
        settings.putExtra("ImageType", spImageType.getSelectedItemPosition());
        setResult(RESULT_OK, settings);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
