package thepetersmith.com.humhubdemoapi;

import thepetersmith.com.services.Posts;

/**
 * Created by smith on 2016-03-19.
 */
public interface PostsFinishedListener {
    void onPostsFinished(Posts posts);
}
