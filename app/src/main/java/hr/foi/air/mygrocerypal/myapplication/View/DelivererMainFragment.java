package hr.foi.air.mygrocerypal.myapplication.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hr.foi.air.mygrocerypal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelivererMainFragment extends Fragment {


    public DelivererMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_deliverer_main, container, false);
    }

}