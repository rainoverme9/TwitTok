package com.example.twittwok;

import android.content.Context;
import android.graphics.Color;

import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.twittwok.databinding.FragmentHomeBinding;
import com.example.twittwok.utils.CheckInternetConnection;
import com.example.twittwok.utils.DataBase.SidRepository;
import com.example.twittwok.utils.ReuqestUtils.CommunicationController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.twittwok.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel model;

    int firstStart = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //Controllo la connessione
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        model.getCheckConncetion();
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();
        CheckInternetConnection network = new CheckInternetConnection(getApplicationContext(), model, networkRequest);
        network.registerNetworkCallback();

        if (firstStart == 0) {
            if (network.firstStart()) {
                firstStart = 1;
                SidRepository.register(this,
                        sid -> {
                            CommunicationController.setSid(String.valueOf(sid));

                            setContentView(binding.getRoot());

                            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                            NavController navController = navHostFragment.getNavController();

                            Toolbar myToolbar = findViewById(R.id.my_toolbar);
                            setSupportActionBar(myToolbar);

                            BottomNavigationView navView = findViewById(R.id.nav_view);
                            appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_newtwok, R.id.navigation_profile).build();
                            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                            NavigationUI.setupWithNavController(navView, navController);

                            //Cambio la toolbar in base allo Screen in cui sonos
                            changeToolbar();
                        },
                        error -> {
                            AlertDialog.Builder noInternetAlertDialog = new AlertDialog.Builder(this);
                            noInternetAlertDialog.setTitle("Ops");
                            noInternetAlertDialog.setMessage("Si è verificato un errore durante la registrazione");
                            noInternetAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                            AlertDialog alert = noInternetAlertDialog.create();
                            alert.show();
                        }
                );
            } else {
                firstStart = 1;
                AlertDialog.Builder noInternetAlertDialog = new AlertDialog.Builder(this);
                noInternetAlertDialog.setTitle("Ops");
                noInternetAlertDialog.setMessage("Non sei connesso ad Internet 🌐");
                noInternetAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                AlertDialog alert = noInternetAlertDialog.create();
                alert.show();

                setContentView(R.layout.activity_main);
                Toolbar myToolbar = findViewById(R.id.my_toolbar);
                LinearLayout noInternetConnection = findViewById(R.id.noInternetConnection);
                myToolbar.setVisibility(View.GONE);
                noInternetConnection.setVisibility(View.VISIBLE);
            }
        }

        model.getCheckConncetion().observe(this,
                checkConnection -> {
                    Log.d("Status Internet", String.valueOf(checkConnection));
                    if (!checkConnection) {
                        Log.d("Internet", "No connessione");
                        //Se non c'è connessione mostro un alert
                        AlertDialog.Builder noInternetAlertDialog = new AlertDialog.Builder(this);
                        noInternetAlertDialog.setTitle("Ops");
                        noInternetAlertDialog.setMessage("Non sei connesso ad Internet 🌐");
                        noInternetAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = noInternetAlertDialog.create();
                        alert.show();
                    } else {
                        if (firstStart == 1) {
                            NavHostFragment navHostFragment2 = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                            NavController navController2 = navHostFragment2.getNavController();
                            navController2.navigate(R.id.navigation_home);

                            SidRepository.register(this,
                                    sid -> {
                                        CommunicationController.setSid(String.valueOf(sid));

                                        setContentView(binding.getRoot());

                                        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
                                        NavController navController = navHostFragment.getNavController();

                                        Toolbar myToolbar = findViewById(R.id.my_toolbar);
                                        setSupportActionBar(myToolbar);

                                        BottomNavigationView navView = findViewById(R.id.nav_view);
                                        appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_newtwok, R.id.navigation_profile).build();
                                        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
                                        NavigationUI.setupWithNavController(navView, navController);

                                        //Cambio la toolbar in base allo Screen in cui sono
                                        changeToolbar();
                                    },
                                    error -> {
                                        AlertDialog.Builder noInternetAlertDialog = new AlertDialog.Builder(this);
                                        noInternetAlertDialog.setTitle("Ops");
                                        noInternetAlertDialog.setMessage("Si è verificato un errore durante la registrazione");
                                        noInternetAlertDialog.setNegativeButton("Chiudi", (dialog, id) -> dialog.cancel());
                                        AlertDialog alert = noInternetAlertDialog.create();
                                        alert.show();
                                    }
                            );
                        }
                    }
                }
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public void changeToolbar() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        LinearLayout noInternetConnection = findViewById(R.id.noInternetConnection);

        //Controllo lo stato della connessione
        model.getCheckConncetion().observe(this,
                checkConnection -> {
                    if (checkConnection) {
                        noInternetConnection.setVisibility(View.GONE);
                        myToolbar.setVisibility(View.VISIBLE);
                    } else {
                        //Se non c'è mostro una schermata che notifichi l'utente
                        noInternetConnection.setVisibility(View.VISIBLE);
                        myToolbar.setVisibility(View.GONE);
                    }
                }
        );

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {

                switch (navDestination.getId()) {
                    case R.id.navigation_home:
                        myToolbar.setTitleTextColor(Color.parseColor("red"));

                        navView.getMenu().getItem(0).setChecked(true);
                        return;
                    case R.id.navigation_userinfo: case R.id.navigation_map:
                        myToolbar.setTitleTextColor(Color.parseColor("black"));

                        navView.getMenu().getItem(0).setChecked(true);
                        navView.setOnItemReselectedListener(item -> {
                            if (item.getItemId() == R.id.navigation_home) {
                                navController.navigateUp();
                            }
                        });
                        return;
                    case R.id.navigation_settings: case R.id.navigation_follower: case R.id.navigation_followerUserInfo:
                        myToolbar.setTitleTextColor(Color.parseColor("black"));

                        navView.getMenu().getItem(2).setChecked(true);
                        navView.setOnItemReselectedListener(item -> {
                            if (item.getItemId() == R.id.navigation_profile) {
                                navController.navigateUp();
                            }
                        });
                        return;
                    default:
                        myToolbar.setTitleTextColor(Color.parseColor("black"));
                        return;
                }
            }
        });
    }
}