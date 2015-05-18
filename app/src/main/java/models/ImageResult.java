package models;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PerkLun on 5/16/2015.
 */
public class ImageResult implements Serializable {

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

    public ImageResult(String new_fullURL, String new_thumbURL, String new_title, int new_width, int new_height){
        fullURL = new_fullURL;
        thumbURL = new_thumbURL;
        title = new_title;
        width = new_width;
        height = new_height;
    }

    //takes the JSONArray, creates the objects and returns an array of ImageResult
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

    public String getTitle(){
        return title;
    }

    public String getThumbURL(){
        return thumbURL;
    }

    public String getFullURL(){
        return fullURL;
    }
}
