package thepetersmith.com.humhubdemoapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

import thepetersmith.com.services.User;

/**
 * Created by smith on 2016-03-13.
 */
public class UsersAdapter extends ArrayAdapter<User> {
    private final Context context;

    public UsersAdapter(Context context, int textViewResourceId,
                             ArrayList<User> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_list, null);
        }

        User item = getItem(position);
        if (item != null) {

            TextView name = (TextView) view.findViewById(R.id.user_name);
            ImageView img = (ImageView) view
                    .findViewById(R.id.user_avatar);
            if (item.getAvatar().equals("")) {
                img.setImageDrawable(null);
            } else {
                Picasso.with(context).load(item.getAvatar()).into(img);
            }
            name.setText(item.getfName() + " " + item.getlName());

        }
        return view;
    }
}
