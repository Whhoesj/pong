package nl.sjtek.smartmobile.pong;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentJoin extends Fragment {

    public static FragmentJoin newInstance() {
        return new FragmentJoin();
    }

    public FragmentJoin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join, container, false);
    }


}
