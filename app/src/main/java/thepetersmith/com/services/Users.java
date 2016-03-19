package thepetersmith.com.services;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> mItems = new ArrayList<User>();
    private String mMessage = "";

    public ArrayList<User> getItems() {
        return mItems;
    }

    public void setItems(ArrayList<User> items) {
        mItems = items;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}