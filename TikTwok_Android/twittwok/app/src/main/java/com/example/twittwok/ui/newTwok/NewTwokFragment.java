package com.example.twittwok.ui.newTwok;

import static android.content.Context.LOCATION_SERVICE;
import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.Gravity.END;
import static android.view.Gravity.START;
import static android.view.Gravity.TOP;
import static androidx.core.location.LocationRequestCompat.QUALITY_HIGH_ACCURACY;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.example.twittwok.R;
import com.example.twittwok.databinding.FragmentNewtwokBinding;

import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.example.twittwok.utils.ReuqestUtils.SendRequestClass.SendTwok;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;


public class NewTwokFragment extends Fragment implements MenuProvider {

    private FragmentNewtwokBinding binding;
    private NewTwokViewModel model;
    private FusedLocationProviderClient fusedLocationClient;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewtwokBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView twok = binding.twok;
        //Se clicco su twok si apre la schermata per modificarne il testo
        twok.setOnClickListener(view -> {
            LinearLayout twokInputTextPage = binding.twokInputTextPage;
            TextInputEditText twokTextModified = binding.twokTextModified;

            twokTextModified.setText(twok.getText());
            twokTextModified.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            twokInputTextPage.setVisibility(View.VISIBLE);
        });

        //Se clicco su clore il testo non viene modificato
        MaterialButton close = binding.close;
        close.setOnClickListener(view -> {
            LinearLayout twokInputTextPage = binding.twokInputTextPage;

            InputMethodManager inputMethodManager = (InputMethodManager) (getActivity()).getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            twokInputTextPage.setVisibility(View.GONE);
        });

        //Se clicco su ok il testo viene modificato
        MaterialButton ok = binding.ok;
        ok.setOnClickListener(view -> {
            LinearLayout twokInputTextPage = binding.twokInputTextPage;
            TextInputEditText twokTextModified = binding.twokTextModified;

            twok.setText(twokTextModified.getText());
            model.setText(String.valueOf(twokTextModified.getText()));
            InputMethodManager inputMethodManager = (InputMethodManager) (getActivity()).getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            twokInputTextPage.setVisibility(View.GONE);
        });

        //Seleziono i componenti del layout
        Button fontfamilyButton = binding.fontfamilyButton;
        MaterialButton halignButton = binding.halignButton;
        MaterialButton valignButton = binding.valignButton;
        MaterialButton backgroundcolorButton = binding.backgroundcolorButton;
        MaterialButton fontcolorButton = binding.fontcolorButton;
        MaterialButton posizioneButton = binding.posizioneButton;


        model = new ViewModelProvider(requireActivity()).get(NewTwokViewModel.class);
        model.getAttributesTwok();
        model.getAttributesTwok().observe(getViewLifecycleOwner(),
                changedTwok -> {

                    //MODIFICA TESTO
                    twok.setText(changedTwok.getText());

                    //MODIFICA FONTTYPE
                    switch (changedTwok.getFonttype()) {
                        case 0:
                            fontfamilyButton.setTypeface(Typeface.MONOSPACE);
                            twok.setTypeface(Typeface.MONOSPACE);
                            break;
                        case 1:
                            fontfamilyButton.setTypeface(Typeface.SANS_SERIF);
                            twok.setTypeface(Typeface.SANS_SERIF);
                            break;
                        case 2:
                            fontfamilyButton.setTypeface(Typeface.SERIF);
                            twok.setTypeface(Typeface.SERIF);
                            break;
                    }

                    //MODIFICA HALIGN
                    switch (changedTwok.getHalign()) {
                        case 0:
                            halignButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.alignleft_foreground));
                            twok.setGravity(START | changedTwok.getValign());
                            break;
                        case 1:
                            halignButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.aligncenter_foreground));
                            twok.setGravity(CENTER_HORIZONTAL | changedTwok.getValign());
                            break;
                        case 2:
                            halignButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.alignright_foreground));
                            twok.setGravity(END | changedTwok.getValign());

                            break;
                    }

                    //MODIFICA VALIGN
                    switch (changedTwok.getValign()) {
                        case 0:
                            valignButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.justifytop_foreground));
                            twok.setGravity(changedTwok.getHalign() | TOP);
                            break;
                        case 1:
                            valignButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.justifycenter_foreground));
                            twok.setGravity(changedTwok.getHalign() | CENTER_VERTICAL);
                            break;
                        case 2:
                            valignButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.justifybottom_foreground));
                            twok.setGravity(changedTwok.getHalign() | BOTTOM);
                            break;
                    }

                    //MODIFICA BACKGROUNDCOLOR
                    backgroundcolorButton.setIconTint(ColorStateList.valueOf(Color.parseColor("#" + changedTwok.getBgcol())));
                    twok.setBackgroundColor(Color.parseColor("#" + changedTwok.getBgcol()));

                    //MODIFICA TEXTCOLOR
                    fontcolorButton.setIconTint(ColorStateList.valueOf(Color.parseColor("#" + changedTwok.getFontcol())));
                    twok.setTextColor(Color.parseColor("#" + changedTwok.getFontcol()));
                }
        );

        model.getRequestPermission().observe(getViewLifecycleOwner(),
                permesso -> {
                    if (permesso) {
                        posizioneButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.position_foreground));
                    } else {
                        posizioneButton.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.positionoff_foreground));
                    }
                });

        //MODIFICA FONTTYPE
        fontfamilyButton.setOnClickListener(view -> {
            if (model.getFonttype() < 2) {
                model.setFonttype(model.getFonttype() + 1);
            } else {
                model.setFonttype(0);
            }
        });

        //MODIFICA HALIGN
        halignButton.setOnClickListener(view -> {
            if (model.getHalign() < 2) {
                model.setHalign(model.getHalign() + 1);
            } else {
                model.setHalign(0);
            }
        });

        //MODIFICA VALIGN
        valignButton.setOnClickListener(view -> {
            if (model.getValign() < 2) {
                model.setValign(model.getValign() + 1);
            } else {
                model.setValign(0);
            }
        });

        //MODIFICA BACKGROUND COLOR
        backgroundcolorButton.setOnClickListener(view -> showColorPickerDialog("BackgroundColor", model));

        //MODIFICA TEXT COLOR
        fontcolorButton.setOnClickListener(view -> showColorPickerDialog("FontColor", model));

        //MODIFICA POSIZIONE
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        posizioneButton.setOnClickListener(v -> {
            // Controllo se ho i permessi e setto la risposta nel ViewModel
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
                //Controllo se il GPS è attivo
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    if (!model.getRequestPermission().getValue()) {
                        model.getRequestPermission().setValue(true);

                        // Richiedo la posizione
                        getCurrentLocation();
                    } else {
                        model.getRequestPermission().setValue(false);
                        model.getAttributesTwok().getValue().setLat(null);
                        model.getAttributesTwok().getValue().setLon(null);
                    }
                } else {
                    model.getRequestPermission().setValue(false);
                    model.getAttributesTwok().getValue().setLat(null);
                    model.getAttributesTwok().getValue().setLon(null);

                    // Mostro all'utente un dialog che segnali il GPS spento
                    AlertDialog.Builder positionAlertDialog = new AlertDialog.Builder(getContext());
                    positionAlertDialog.setTitle("Posizione");
                    positionAlertDialog.setMessage("Non hai il gps Attivo");
                    positionAlertDialog.setPositiveButton("Attiva GPS", (dialog, id) -> {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    });
                    positionAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                    AlertDialog alert = positionAlertDialog.create();
                    alert.show();
                }

                // Se non ho il permesso dall'utente lo richiedo
            } else {
                requestPermissionLauncher.launch(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                });
            }
        });

        //MODIFICA FONT SIZE
        SeekBar seekBar = binding.seekBar;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int fontsize, boolean fromUser) {
                if (fontsize >= 20 && fontsize < 30) {
                    model.setFontsize(0);
                } else if (fontsize >= 30 && fontsize < 40) {
                    model.setFontsize(1);
                } else {
                    model.setFontsize(2);
                }
                twok.setTextSize(fontsize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Called when the user first touches the SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // called after the user finishes moving the SeekBar
            }
        });


        return root;
    }

    //Controllo che l'utente abbia dato i permessi di posizione
    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                Boolean fineLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseLocationGranted = result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false);

                // Se ho il permesso concesso dall'utente
                if ((fineLocationGranted != null && fineLocationGranted) || (coarseLocationGranted != null && coarseLocationGranted)) {
                    // Location access granted

                    LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

                    // Controllo se il GPS è attivo
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        if (!model.getRequestPermission().getValue()) {
                            model.getRequestPermission().setValue(true);

                            // Richiedo la posizione
                            getCurrentLocation();
                        } else {
                            model.getRequestPermission().setValue(false);
                            model.getAttributesTwok().getValue().setLat(null);
                            model.getAttributesTwok().getValue().setLon(null);
                        }
                    } else {
                        model.getRequestPermission().setValue(false);
                        model.getAttributesTwok().getValue().setLat(null);
                        model.getAttributesTwok().getValue().setLon(null);

                        // Mostro all'utente un dialog che segnali il GPS spento
                        AlertDialog.Builder positionAlertDialog = new AlertDialog.Builder(getContext());
                        positionAlertDialog.setTitle("Posizione");
                        positionAlertDialog.setMessage("Non hai il gps Attivo");
                        positionAlertDialog.setPositiveButton("Attiva GPS", (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)));
                        positionAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = positionAlertDialog.create();
                        alert.show();
                    }

                // Se non ho il permesso concesso dall'utente
                } else {
                    // Setto i valori di lat e lon a null
                    model.getRequestPermission().setValue(false);
                    model.getAttributesTwok().getValue().setLat(null);
                    model.getAttributesTwok().getValue().setLon(null);

                    // Mostro all'utente un dialog che segnali il GPS spento
                    MaterialButton posizioneButton = binding.posizioneButton;
                    posizioneButton.setOnClickListener(view -> {
                        AlertDialog.Builder positionAlertDialog = new AlertDialog.Builder(getContext());
                        positionAlertDialog.setTitle("Posizione");
                        positionAlertDialog.setMessage("Hai negato i permessi di accesso");
                        positionAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = positionAlertDialog.create();
                        alert.show();
                    });
                }
            }
    );


    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        // Calcolo l'ultima posizione registrata
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                // Se ho il valore dell'ultima posizione registrata
                if (location != null) {
                    model.setLat(location.getLatitude());
                    model.setLon(location.getLongitude());

                    // Se non ho nessun valore per l'ultima posizione registrata inizio una nuova richiesta
                } else {
                    com.google.android.gms.location.LocationRequest locationRequest = com.google.android.gms.location.LocationRequest.create()
                            .setPriority(QUALITY_HIGH_ACCURACY)
                            .setInterval(10000)
                            .setFastestInterval(1000)
                            .setNumUpdates(1);

                    LocationCallback locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            Location location1 = locationResult.getLastLocation();
                            model.setLat(location1.getLatitude());
                            model.setLon(location1.getLongitude());
                        }
                    };
                    // Richiedo l'update della posizione
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                }
            }
        });
    }


    /*   COLORPICKER MODAL   */
    private void showColorPickerDialog(String whatToChange, NewTwokViewModel model) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialoglayout = inflater.inflate(R.layout.colorpicker, null);

        alertDialogBuilder.setView(dialoglayout);

        ColorPicker picker = dialoglayout.findViewById(R.id.picker);
        SVBar svBar =  dialoglayout.findViewById(R.id.svbar);
        OpacityBar opacityBar = dialoglayout.findViewById(R.id.opacitybar);

        TextView twok = binding.twok;

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.setOnColorChangedListener(picker.getOnColorChangedListener());
        picker.setOldCenterColor(twok.getCurrentTextColor());

        alertDialogBuilder.setPositiveButton("Ok", (dialog, id) -> {
            if (whatToChange.equals("FontColor")) {
                model.setFontcol(Integer.toHexString(picker.getColor()).substring(2));

            } else if (whatToChange.equals("BackgroundColor")) {
                model.setBgcol(Integer.toHexString(picker.getColor()).substring(2));
            }
        });
        alertDialogBuilder.setNegativeButton("Annulla", (dialog, id) -> dialog.cancel());

        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }


    /*    TOOLBAR MODIFIED    */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.icon_right_newtwok, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_paperplane) {
            CommunicationController.addTwok(
                    model.getAttributesTwok().getValue(),
                    response -> {
                        model.getAttributesTwok().setValue(new SendTwok("", "FFFFFF", "000000", 0, 0, 0, 0, null, null));
                        model.getRequestPermission().setValue(false);
                    },
                    error -> {
                        AlertDialog.Builder sendTwokAlertDialog = new AlertDialog.Builder(getContext());
                        sendTwokAlertDialog.setTitle("Ops");
                        sendTwokAlertDialog.setMessage("Si è verificato un errore durante l'invio del twok");
                        sendTwokAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = sendTwokAlertDialog.create();
                        alert.show();
                    }
            );
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