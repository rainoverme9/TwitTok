package com.example.twittwok.ui.follower;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twittwok.databinding.FragmentFollowerBinding;

//Pagina che mostra la lista degli utenti seguiti
public class FollowerFragment extends Fragment {

    private FragmentFollowerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFollowerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Mostro la lista dei degli utenti seguiti
        FollowerListViewModel model = new ViewModelProvider(requireActivity()).get(FollowerListViewModel.class);
        ListOfFollower.getFollower(root, container, model, getViewLifecycleOwner());

        return root;
    }
}