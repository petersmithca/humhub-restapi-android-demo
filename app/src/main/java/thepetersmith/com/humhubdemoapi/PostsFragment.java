package thepetersmith.com.humhubdemoapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import thepetersmith.com.services.FeedStore;
import thepetersmith.com.services.Post;
import thepetersmith.com.services.Posts;

/**
 * Created by smith on 2016-03-19.
 */
public class PostsFragment extends ListFragment implements
        PostsFinishedListener {
    @SuppressWarnings("unused")
    private static final String TAG = "PostsFragment";
    public static final String POST_ID = "com.thepetersmith.humhubapi.post_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Messages(PostsFragment.this, getActivity().getApplicationContext())
                .execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.base_container, container, false);
    }

    private class Messages extends AsyncTask<Posts, Void, Posts> {
        PostsFinishedListener mFinishedListener;
        Context mContext;

        Messages(PostsFinishedListener finishedListener, Context context) {
            mFinishedListener = finishedListener;
            mContext = context;
        }

        @Override
        protected Posts doInBackground(Posts... params) {
            Posts posts = new Posts();
            try {
                FeedStore store = FeedStore.get(mContext);
                posts = store.posts();
            } catch (Exception e) {
                posts.setMessage(e.getMessage());
            }
            return posts;

        }

        @Override
        protected void onPostExecute(Posts result) {
            if (result.getMessage().equals("")) {
                mFinishedListener.onPostsFinished(result);
            } else {
                Toast.makeText(getActivity(), result.getMessage(),
                        Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }

    }
    /*
    @Override
    public void onListItemClick(ListView l, View w, int position, long id) {

        Post post = (Post) getListView()
                .getItemAtPosition(position);
        Intent i = new Intent(getActivity(), RepliesActivity.class);
        i.putExtra(POST_ID, post.getID());
        startActivity(i);
    }
    */
    @Override
    public void onPostsFinished(Posts posts) {
        PostsAdapter adapter = new PostsAdapter(getActivity(),
                R.id.adapter_item, posts.getItems());
        View header = getActivity().getLayoutInflater().inflate(
                R.layout.list_header, null);
        ListView lv = getListView();
        lv.setDivider(new ColorDrawable(getActivity().getResources().getColor(
                R.color.dark_blue)));
        lv.setDividerHeight(1);
        lv.addHeaderView(header, null, false);
        this.setListAdapter(adapter);
    }
}