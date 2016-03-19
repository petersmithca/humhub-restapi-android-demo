package thepetersmith.com.humhubdemoapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuFragment extends Fragment {
    @SuppressWarnings("unused")
    private static final String TAG = "MainMenuFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_menu, parent, false);

        Button btnDirectory = (Button) v.findViewById(R.id.main_menu_directory);
        btnDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DirectoryActivity.class);
                startActivity(i);
            }
        });

        Button btnPosts = (Button) v.findViewById(R.id.main_menu_posts);
        btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PostsActivity.class);
                startActivity(i);
            }
        });

        return v;
    }

}