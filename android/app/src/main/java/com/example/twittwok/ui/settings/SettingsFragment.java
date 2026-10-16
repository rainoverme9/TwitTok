package com.example.twittwok.ui.settings;

import static androidx.core.content.ContextCompat.getMainExecutor;
import static androidx.navigation.Navigation.findNavController;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.twittwok.MainActivity;
import com.example.twittwok.R;
import com.example.twittwok.databinding.FragmentSettingsBinding;
import com.example.twittwok.ui.profile.ProfileTwokListViewModel;
import com.example.twittwok.utils.DataBase.StoredUser;
import com.example.twittwok.utils.DataBase.StoredUserRepository;
import com.example.twittwok.utils.ReuqestUtils.GetRequestClass.Profile;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.TwokListUtils.TwokPostRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

//Pagina che permette di modificare nome e foto dell'utente loggato
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private String name = null;
    private String picture = null;

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setPicture(String picture) { this.picture = picture; }
    public String getPicture() { return picture; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Seleziono i componenti del layout
        CircleImageView picture = binding.picture;
        TextInputEditText newName = binding.newName;

        //Imposto la foto dell'utente loggato
        CommunicationController.getProfile(
                profile ->{
                    //Ottengo UID dell'utente loggato
                    String uid = ((Profile) profile).getUid();

                    StoredUserRepository db = Room.databaseBuilder(getContext(), StoredUserRepository.class, "database").build();
                    ListenableFuture<StoredUser> storedUser = db.storedUserDao().getUser(uid);
                    storedUser.addListener(() -> {
                        try {
                            //Seleziono l'utente dal DB
                            StoredUser user = storedUser.get();

                            //Se l'utente è presente
                            if (user != null) {
                                byte[] decodedString = Base64.decode(user.getPicture(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                picture.setImageBitmap(decodedByte);

                                //Se la versione della foto è stata aggiornata
                                //1. scarico la foto dal server e la inserisco nel db
                                //2. aggiorno la foto
                                CommunicationController.getProfile(
                                        response -> {
                                            if (((Profile) response).getPversion() > user.getPversion()) {
                                                try {
                                                    byte[] decodedStringNew = Base64.decode(((Profile) response).getPicture(), Base64.DEFAULT);
                                                    Bitmap decodedByteNew = BitmapFactory.decodeByteArray(decodedStringNew, 0, decodedStringNew.length);
                                                    picture.setImageBitmap(decodedByteNew);

                                                    StoredUser newStoredUser = new StoredUser();
                                                    newStoredUser.setUid(((Profile) response).getUid());
                                                    newStoredUser.setPversion(((Profile) response).getPversion());
                                                    newStoredUser.setPicture(((Profile) response).getPicture());
                                                    db.storedUserDao().insertAll(newStoredUser);

                                                } catch (Exception e) {
                                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
                                                    alertDialog.setTitle("Ops");
                                                    alertDialog.setMessage("Si è verificato un errore durante il download della foto profilo");
                                                    alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                                                    AlertDialog alert = alertDialog.create();
                                                    alert.show();
                                                }
                                            }
                                        },
                                        error -> {
                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
                                            alertDialog.setTitle("Ops");
                                            alertDialog.setMessage("Si è verificato un errore durante il download della foto profilo");
                                            alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                                            AlertDialog alert = alertDialog.create();
                                            alert.show();
                                        }
                                );
                                //se l'utente non è presente nel DB
                            } else {
                                //1. scarico la foto dal server e la inserisco nel db
                                //2. aggiorno la foto
                                CommunicationController.getProfile(
                                        response -> {
                                            if (((Profile) response).getPversion() != 0) {
                                                try {
                                                    byte[] decodedStringNew = Base64.decode(((Profile) response).getPicture(), Base64.DEFAULT);
                                                    Bitmap decodedByteNew = BitmapFactory.decodeByteArray(decodedStringNew, 0, decodedStringNew.length);
                                                    picture.setImageBitmap(decodedByteNew);

                                                    StoredUser newStoredUser = new StoredUser();
                                                    newStoredUser.setUid(((Profile) response).getUid());
                                                    newStoredUser.setPversion(((Profile) response).getPversion());
                                                    newStoredUser.setPicture(((Profile) response).getPicture());
                                                    db.storedUserDao().insertAll(newStoredUser);
                                                } catch (Exception e) {
                                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
                                                    alertDialog.setTitle("Ops");
                                                    alertDialog.setMessage("Si è verificato un errore durante il download della foto profilo");
                                                    alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                                                    AlertDialog alert = alertDialog.create();
                                                    alert.show();
                                                }
                                            }
                                        },
                                        error -> {
                                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
                                            alertDialog.setTitle("Ops");
                                            alertDialog.setMessage("Si è verificato un errore durante il download della foto profilo");
                                            alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                                            AlertDialog alert = alertDialog.create();
                                            alert.show();
                                        }
                                );
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }, getMainExecutor(getContext()));
                },
                error -> {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
                    alertDialog.setTitle("Ops");
                    alertDialog.setMessage("Si è verificato un errore durante il download della foto profilo");
                    alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = alertDialog.create();
                    alert.show();
                }
        );

        //Al click sulla foto si apre l'ImagePicker
        picture.setOnClickListener(
                view -> pickMedia.launch(new PickVisualMediaRequest.Builder() .build())
        );

        //Modifica del profilo
        Button modificaButton = binding.modificaButton;
        modificaButton.setOnClickListener(v -> {
            if (!newName.getText().equals("") && newName.getText().toString().trim().length() > 0) {
                setName(String.valueOf(newName.getText()));
            }
            if (getName() == null && getPicture() == null) {
                modificaButton.setEnabled(false);
            } else {
                CommunicationController.setProfile(getName(), getPicture(),
                        response -> {
                            ProfileTwokListViewModel modelProfile = new ViewModelProvider(((MainActivity) getContext())).get(ProfileTwokListViewModel.class);
                            modelProfile.getListOfTwoks().setValue(new TwokPostRepository());
                            findNavController(root).navigate(R.id.action_global_toProfile);
                        },
                        error -> {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
                            alertDialog.setTitle("Ops");
                            alertDialog.setMessage("Si è verificato un errore durante la modifica del tuo profilo");
                            alertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                            AlertDialog alert = alertDialog.create();
                            alert.show();
                        }
                );
            }
        });

        return root;
    }

    //ImagePicker
    private final ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(),
            uri -> {
                if (uri != null) {
                    try {
                        //From URI to Bitmap
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        //From Bitmap to Base64
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] byteArray = baos.toByteArray();
                        String imageToBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        if (imageToBase64.length() > 137000) {
                            AlertDialog.Builder imageDialog = new AlertDialog.Builder(getContext());

                            imageDialog.setTitle("Modifica il tuo profilo");
                            imageDialog.setMessage("L'immagine che hai selezionato è troppo grande");
                            imageDialog.setPositiveButton("Scegli", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    pickMedia.launch(new PickVisualMediaRequest.Builder()
                                            .build());
                                }
                            });
                            imageDialog.setNegativeButton("Annulla", (dialog, id) -> dialog.cancel());
                            AlertDialog alert = imageDialog.create();
                            alert.show();
                        } else {
                            setPicture(imageToBase64);
                            CircleImageView picture = binding.picture;
                            picture.setImageBitmap(selectedImage);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
}