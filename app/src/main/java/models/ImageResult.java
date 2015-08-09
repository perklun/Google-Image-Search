package models;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PerkLun on 5/16/2015.
 *
 * Representation of each search result (i.e. picture)
 */
public class ImageResult implements Serializable {

    // Attributes of picture
    public String fullURL;
    public String thumbURL;
    public String title;
    public int width;
    public int height;

    public ImageResult(JSONObject image_object){
        try {
            fullURL = image_object.getString("url");;
            thumbURL = image_object.getString("tbUrl");;
            title = image_object.getString("title");
            width = image_object.getInt("width");;
            height = image_object.getInt("height");;
        } catch (JSONException je) {
            Log.d("DEBUG JSONObject", je.toString());
        }
    }

    /**
     * Parses the json object returned by Google
     * Creates ImageResult for each result in json
     *
     * @param image_json json object returned by Google
     * @return result ArrayList of ImageResult
     */
    public static ArrayList<ImageResult> parseJSON(JSONArray image_json){
        ArrayList<ImageResult> result = new ArrayList<ImageResult>();
        try {
            for(int i=0; i < image_json.length(); i++){
                ImageResult new_image = new ImageResult(image_json.getJSONObject(i));
                result.add(new_image);
            }
        } catch (JSONException je) {
            Log.d("DEBUG JSON", je.toString());
        }
        return result;
    }

    /**
     * Get title of picture
     *
     * @return title
     */
    public String getTitle(){
        return title;
    }

    /**
     * Get thumbnail URL of picture
     *
     * @return thumbURL
     */
    public String getThumbURL(){
        return thumbURL;
    }

    /**
     * Get full URL of picture
     *
     * @return fullURL
     */
    public String getFullURL(){
        return fullURL;
    }
}
