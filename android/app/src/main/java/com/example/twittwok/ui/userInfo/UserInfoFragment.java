package com.example.twittwok.ui.userInfo;

import android.app.AlertDialog;
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

import com.example.twittwok.MainActivity;
import com.example.twittwok.R;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.databinding.FragmentUserinfoBinding;

//Pagina che mostra la bacheca specifica dell'utente selezionato
public class UserInfoFragment extends Fragment implements MenuProvider {

    private FragmentUserinfoBinding binding;

    private boolean follower;
    public void setFollower(boolean follower) {
        this.follower = follower;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentUserinfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Mostro la lista dei Twok dell'utente selezionato
        UserInfoTwokListViewModel model = new ViewModelProvider(requireActivity()).get(UserInfoTwokListViewModel.class);
        UserInfoListOfTwoks.getTwok(root, container, getArguments().getString("uid"), model, getViewLifecycleOwner());

        return root;
    }

    //TOOLBAR MODIFIED
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Ricevo gli argomenti
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getArguments().getString("name"));

        //Modifico il menu della toolbar
        getActivity().addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.icon_right_userinfo, menu);

        CommunicationController.isFollowed(
                getArguments().getString("uid"),
                result -> {
                    setFollower(Boolean.parseBoolean(result.toString()));
                    if(Boolean.parseBoolean(result.toString())) {
                        menu.findItem(R.id.action_follower).setIcon(R.drawable.removefollower_foreground);
                    } else {
                        menu.findItem(R.id.action_follower).setIcon(R.drawable.addfollower_foreground);
                    }
                },
                error -> {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Ops");
                    alertDialog.setMessage("Si è verificato un errore durante l'identificazione dell'utente come seguito/non seguito");
                    alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = alertDialog.create();
                    alert.show();
                }
        );
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.action_follower) {
            menuItem.setEnabled(false);
            if (follower) {
                Log.d("Follower", "Rimozione dell'utente dalla lista dei seguiti avvenuta con successo");
                CommunicationController.unFollow(
                        getArguments().getString("uid"),
                        response -> {
                            menuItem.setIcon(R.drawable.addfollower_foreground);
                            menuItem.setEnabled(true);
                            setFollower(false);
                        },
                        error -> {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                            alertDialog.setTitle("Ops");
                            alertDialog.setMessage("Si è verificato un errore durante la rimozione dell'utente dalla lista dei seguiti");
                            alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                            AlertDialog alert = alertDialog.create();
                            alert.show();
                            menuItem.setEnabled(true);
                        }
                );
            } else {
                Log.d("Follower", "Aggiunta dell'utente dalla lista dei seguiti avvenuta con successo");
                CommunicationController.follow(
                        getArguments().getString("uid"),
                        response -> {
                            menuItem.setIcon(R.drawable.removefollower_foreground);
                            menuItem.setEnabled(true);
                            setFollower(true);
                        },
                        error -> {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                            alertDialog.setTitle("Ops");
                            alertDialog.setMessage("Si è verificato un errore durante l'aggiunta dell'utente alla lista dei seguiti");
                            alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                            AlertDialog alert = alertDialog.create();
                            alert.show();
                            menuItem.setEnabled(true);
                        }
                );
            }
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