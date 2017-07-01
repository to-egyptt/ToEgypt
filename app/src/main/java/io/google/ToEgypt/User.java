package io.google.ToEgypt;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import BL.Session;
import Fragments.Packages;
import Fragments.Places;
import Fragments.Profile;

public class User extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private Session session;
    private NavigationView navigationView;
    public Boolean myPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new Session(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
        mToolbar.setTitle("Places");
        myPackage = false;
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_places, 0);
            setMenuItem(0);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment = null;
                        Class fragmentClass = Places.class;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_packages:
                                fragmentClass = Packages.class;
                                break;
                            case R.id.nav_places:
                                fragmentClass = Places.class;
                                break;
                            case R.id.nav_myPackages:
                                myPackage = true;
                                fragmentClass = Packages.class;
                                break;

                            case R.id.nav_profile:
                                fragmentClass = Profile.class;
//                                Intent intent = new Intent(User.this, UpdateUser.class);
//                                intent.putExtra("Mode", 2);
//                                startActivity(intent);

                                break;
                            case R.id.nav_logout:
                                session.setLoggedIn(false);
                                finish();
                                startActivity(new Intent(User.this, Login.class));

//                                fragmentClass = Profile.class;
//                                mToolbar.setTitle("My Packages");
//                                break;
//                            default:
//                                fragmentClass = Home.class;
                        }

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.userContent, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();

                        // Highlight the selected item has been done by NavigationView
//                        menuItem.setChecked(true);
                        // Close the navigation drawer
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {

            fm.popBackStack();

        } else {

            super.onBackPressed();

        }
    }


//    public Boolean checkPrivate (){
//
//    }

    public void setActionBarTitle(String title) {
        mToolbar.setTitle(title);
    }

    public void setMenuItem(int positItem) {
        navigationView.getMenu().getItem(positItem).setChecked(true);
    }

}
