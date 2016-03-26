package com.example.enda.flickadvisor.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.enda.flickadvisor.R;
import com.example.enda.flickadvisor.fragments.BrowseFragment;
import com.example.enda.flickadvisor.services.UserRealmService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG_ACTIVITY = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new BrowseFragment()).commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);

            // set navigation menu icon states
            Menu nav = navigationView.getMenu();
            nav.findItem(R.id.nav_browse).setChecked(true);

            setMenuAccountSectionState(nav, UserRealmService.isLoggedIn());
        }
    }

    private void setMenuAccountSectionState(Menu nav, boolean isLoggedIn) {
        nav.findItem(R.id.nav_sign_in).setVisible(!isLoggedIn);
        nav.findItem(R.id.nav_sign_up).setVisible(!isLoggedIn);
        nav.findItem(R.id.nav_sign_out).setVisible(isLoggedIn);
    }

    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem signOut = menu.findItem(R.id.action_logout);

        if (!UserRealmService.isLoggedIn()) {
            signOut.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_logout:
                signOutDialog().show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment;
        switch (id) {
            case R.id.nav_browse:
                fragment = new BrowseFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                setTitle(R.string.title_browse_fragment);
                break;
            case R.id.nav_discover:
                break;
            case R.id.nav_my_movies:
                break;
            case R.id.nav_my_series:
                break;
            case R.id.nav_sign_in:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.nav_sign_up:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.nav_sign_out:
                signOutDialog().show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Dialog signOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Sign Out");
        builder.setMessage("Are you sure you want to leave?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserRealmService.logout();
                Toast.makeText(getApplicationContext(), "Signed out.", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG_ACTIVITY, "Cancelled sign out process.");
            }
        });
        return builder.create();
    }
}
