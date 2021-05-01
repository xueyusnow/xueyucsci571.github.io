package com.example.hhhw9.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hhhw9.R;
import com.example.hhhw9.ui.movies.MoviesFragment;
import com.example.hhhw9.ui.tvs.TvsFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TvsFragment likeFragment;
    private MoviesFragment choiceFragment;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
        root.findViewById(R.id.forloading).setVisibility(View.INVISIBLE);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        likeFragment = TvsFragment.newInstance();
        choiceFragment = MoviesFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.linearLayout,choiceFragment).commit();
        TabLayout tabLayout = (TabLayout)root.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Toast.makeText(getActivity(),tab.getText(),Toast.LENGTH_SHORT).show();
                if (tab.getText().equals("TV Shows")){
                    getChildFragmentManager().beginTransaction().replace(R.id.linearLayout,likeFragment).commit();
                }
                if (tab.getText().equals("Movies")){
                    getChildFragmentManager().beginTransaction().replace(R.id.linearLayout,choiceFragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }
    public void changeLayout(int index){
        if(index==0){
            root.findViewById(R.id.forloading).setVisibility(View.GONE);
        }else if(index==1){
            root.findViewById(R.id.forloading).setVisibility(View.VISIBLE);
        }
    }
}