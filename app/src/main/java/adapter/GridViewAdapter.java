package adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import net.perklim.google_image_search.R;
import java.util.ArrayList;
import models.ImageResult;

/**
 * Created by PerkLun on 5/16/2015.
 *
 * Adapter that arranges the pictures in a grid
 */
public class GridViewAdapter extends ArrayAdapter<ImageResult> {

    public GridViewAdapter(Context context, ArrayList<ImageResult> images) {
        super(context, R.layout.image_item, images);
    }

    /**
     * Override getView and use Picasso to retrieve image
     *
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ImageResult image = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_item, parent, false);
        }
        // Lookup view for data population
        ImageView ivThumbnail = (ImageView) convertView.findViewById(R.id.ivThumbnail);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        // Populate the data into the template view using the data object (formats the HTML)
        tvTitle.setText(Html.fromHtml(image.getTitle()));
        // Clear out image in case of view is recycled
        ivThumbnail.setImageResource(0);
        // Use Picassi to retrieve image
        Picasso.with(getContext()).load(image.getThumbURL()).into(ivThumbnail);
        return convertView;
    }
}
