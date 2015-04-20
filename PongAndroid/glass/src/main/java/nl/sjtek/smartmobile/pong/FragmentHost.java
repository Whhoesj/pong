package nl.sjtek.smartmobile.pong;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentHost extends Fragment {

    private static final String ARG_IP = "ip_address";
    private String ipAddress;

    public static FragmentHost newInstance(String ipAddress) {
        FragmentHost fragment = new FragmentHost();
        Bundle args = new Bundle();
        args.putString(ARG_IP, ipAddress);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentHost() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ipAddress = getArguments().getString(ARG_IP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_host, container, false);
        TextView textViewIp = (TextView) rootView.findViewById(R.id.textViewIp);
        textViewIp.setText(ipAddress);
        return rootView;
    }
}
