package com.example.myfirebasejavaproject.ExtraWork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myfirebasejavaproject.R;

public class FragmentTwo extends Fragment {

    public FragmentTwo() {
       // Log.i("Fragment Check", "Fragment Two Created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_two, container, false);
    }
}
