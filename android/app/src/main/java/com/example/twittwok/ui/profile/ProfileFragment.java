package com.example.twittwok.ui.profile;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.example.twittwok.R;
import com.example.twittwok.databinding.FragmentProfileBinding;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Profile;

//Pagina che mostra la bacheca specifica dell'utente loggato
public class ProfileFragment extends Fragment implements MenuProvider {
    private FragmentProfileBinding binding;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        this.root = binding.getRoot();

        //Mostro la lista dei Twok dell'utente loggato
        CommunicationController.getProfile(
                profile ->{
                    String uid = ((Profile) profile).getUid();
                    ProfileTwokListViewModel model = new ViewModelProvider(requireActivity()).get(ProfileTwokListViewModel.class);
                    ProfileListOfTwoks.getTwok(root, container, uid, model, getViewLifecycleOwner());
                },
                error -> {}
        );

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Modifico il menu della toolbar
        getActivity().addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    //TOOLBAR MODIFIED
    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.icon_right_profile, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_settings:
                findNavController(this.root).navigate(R.id.action_navigation_profileFragment_to_settingsFragment);
                return true;
            case R.id.action_followers:
                findNavController(this.root).navigate(R.id.action_navigation_profileFragment_to_followerFragment);
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}