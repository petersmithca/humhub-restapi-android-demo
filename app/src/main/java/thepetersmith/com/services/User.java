package thepetersmith.com.services;

/**
 * Created by smith on 2016-03-13.
 */
public class User {
    private String mUsername;
    private int mId;
    private String mAvatar;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    private String fName;
    private String lName;

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String userName) {
        mUsername = userName;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = "http://10.0.2.2/uploads/profile_image/" + avatar + ".jpg";
    }

}
