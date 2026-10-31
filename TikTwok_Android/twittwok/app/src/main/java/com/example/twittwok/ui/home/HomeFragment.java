package com.example.twittwok.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.twittwok.databinding.FragmentHomeBinding;

//Pagina che mostra tutti i twok presenti nel server
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Mostro la lista dei Twok
        HomeTwokListViewModel model = new ViewModelProvider(requireActivity()).get(HomeTwokListViewModel.class);
        HomeListOfTwoks.getTwok(root, container, model, getViewLifecycleOwner());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}