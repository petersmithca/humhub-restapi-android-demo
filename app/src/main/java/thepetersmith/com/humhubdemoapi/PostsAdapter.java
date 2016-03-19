package thepetersmith.com.humhubdemoapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import thepetersmith.com.services.Post;
import thepetersmith.com.services.User;

/**
 * Created by smith on 2016-03-19.
 */
public class PostsAdapter extends ArrayAdapter<Post> {
    private final Context context;

    public PostsAdapter(Context context, int textViewResourceId,
                        ArrayList<Post> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.posts, null);
        }

        Post item = getItem(position);
        if (item != null) {
            TextView title = (TextView) view.findViewById(R.id.posts_title);
            title.setText(item.getMessage() + " - by " + item.getUserName() + " (" + item.getCommentCount() + " Comments)");
        }

        return view;
    }
}

