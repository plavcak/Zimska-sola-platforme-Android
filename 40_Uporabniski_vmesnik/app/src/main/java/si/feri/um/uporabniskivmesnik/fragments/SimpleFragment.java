package si.feri.um.uporabniskivmesnik.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import si.feri.um.uporabniskivmesnik.R;

public class SimpleFragment extends Fragment {

    public static SimpleFragment newInstance() {
        SimpleFragment fragment = new SimpleFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple, container, false);


        return view;
    }
}
