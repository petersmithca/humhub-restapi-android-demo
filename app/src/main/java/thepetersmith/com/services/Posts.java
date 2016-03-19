package thepetersmith.com.services;

import java.util.ArrayList;

public class Posts {
    private ArrayList<Post> mItems = new ArrayList<Post>();
    private String mMessage = "";

    public ArrayList<Post> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<Post> items) {
        mItems = items;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}