package activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import net.perklim.google_image_search.R;
import adapter.GridViewAdapter;
import listener.EndlessScrollListener;
import models.ImageResult;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by PerkLun on 5/17/2015.
 *
 * Main activity that presents the search functionality to dogthe user
 */
public class SearchActivity extends ActionBarActivity {

    private final int REQUEST_CODE = 1;

    private EditText etSearchField;
    private GridView gvResults;
    private ArrayList<ImageResult> resultArrayList;
    private GridViewAdapter gridAdapter;

    // URLs for search query and parameters
    private String googleImageURL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
    private int start = 0;
    private String query = "";
    private String rsz = "&rsz=8";
    private final String p_imagesize = "&imgsz=";
    private final String p_imagecolor = "&imgcolor=";
    private final String p_imagetype = "&imgtype=";
    private final String p_sitefilter = "&as_sitesearch=";
    private final String p_start = "&start=";

    // Variables to store search filters
    private int imagesize_val = 0;
    private int imagecolor_val = 0;
    private int imagetype_val = 0;
    private String sitefilter_val = "";
    private String[] image_size_array;
    private String[] color_filter_array;
    private String[] image_type_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUp();
    }

    /**
     * Set up adapters and listeners for activity, called during onCreate
     * Includes listener to launch FullScreenActivity when an image is clicked
     * Includes listener for endless scrolling
     */
    public void setUp(){
        resultArrayList = new ArrayList<ImageResult>();
        etSearchField = (EditText) findViewById(R.id.etSearchField);
        gvResults = (GridView) findViewById(R.id.gvResults);
        // Attach datasource to GridAdapter
        gridAdapter = new GridViewAdapter(this, resultArrayList);
        gvResults.setAdapter(gridAdapter);
        // Launch full screen activity to display picture in screen
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, FullScreenActivity.class);
                i.putExtra("image", resultArrayList.get(position));
                startActivity(i);
            }
        });
        color_filter_array = getResources().getStringArray(R.array.color_filter_array);
        image_size_array = getResources().getStringArray(R.array.image_size_array);
        image_type_array = getResources().getStringArray(R.array.image_type_array);
        // Provide infinite scrolling ability
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                start += 8;
                fetchPopularPhotos(query, start);
            }
        });
    }

    /**
     * Launch SettingsActivity to toggle search filters
     * Passes existing search filters to SettingsActivity in a bundle
     *
     * @param mi
     */
    public void onComposeAction(MenuItem mi){
        Intent i = new Intent(SearchActivity.this, SettingsActivity.class);
        i.putExtra("ImageSize", imagesize_val);
        i.putExtra("ColorFilter", imagecolor_val);
        i.putExtra("ImageType", imagetype_val);
        i.putExtra("SiteFilter", sitefilter_val);
        startActivityForResult(i, REQUEST_CODE);
    }

    /**
     * Obtain new search filters from MenuItem selection
     *
     * @param requestCode
     * @param resultCode
     * @param settings
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent settings){
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            imagesize_val = settings.getExtras().getInt("ImageSize");
            imagecolor_val = settings.getExtras().getInt("ColorFilter");
            imagetype_val = settings.getExtras().getInt("ImageType");
            sitefilter_val = settings.getExtras().getString("SiteFilter");
            Toast.makeText(this, "Settings updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    /**
     * Query for photos when search button is clicked
     *
     * @param v search button view
     */
    public void onImageSearch(View v){
        query = etSearchField.getText().toString();
        // Launch a toast to let user know search made
        Toast.makeText(this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
        gridAdapter.clear();
        start = 0;
        fetchPopularPhotos(query, start);
    }

    /**
     * Fetches photos base on query, page and search filters from Google
     *
     * @param query query string
     * @param page page to retrieve
     */
    public void fetchPopularPhotos(String query, int page){
        AsyncHttpClient client = new AsyncHttpClient();
        // Create query string to be sent to Google
        StringBuffer full_query = new StringBuffer();
        full_query.append(googleImageURL);
        full_query.append(query);
        full_query.append(rsz);
        full_query.append(p_imagesize + image_size_array[imagesize_val]);
        full_query.append(p_imagecolor + color_filter_array[imagecolor_val]);
        full_query.append(p_imagetype + image_type_array[imagetype_val]);
        full_query.append(p_sitefilter + sitefilter_val);
        full_query.append(p_start + page);
        client.get(full_query.toString(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray image_json = null;
                try {
                    image_json = response.getJSONObject("responseData").getJSONArray("results");
                    gridAdapter.addAll(ImageResult.parseJSON(image_json));
                } catch (JSONException je) {
                    Log.d("DEBUG JSON", je.toString());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG JSON", statusCode + " " + errorResponse.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}