package io.google.gp_11;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import AdminFragments.AdminHomeFragment;
import AdminFragments.AdminPackagesFragment;
import AdminFragments.AdminPlacesFragment;

public class User extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
        mToolbar.setTitle("Places");
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_places, 0);
            mToolbar.setTitle("Places");
            navigationView.getMenu().getItem(0).setChecked(true);
        } else {

            int intentFragment = getIntent().getExtras().getInt("frgToLoad");
            switch (intentFragment) {
                case 2:
                    Fragment fragment = null;

                    break;
            }
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
                        Class fragmentClass = AdminHomeFragment.class;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_packages:
                                fragmentClass = AdminPackagesFragment.class;
                                mToolbar.setTitle("Packages");
                                break;
                            case R.id.nav_places:
                                fragmentClass = AdminPlacesFragment.class;
                                mToolbar.setTitle("Places");
                                break;
                            case R.id.nav_myPackages:
                                fragmentClass = AdminPackagesFragment.class;
                                mToolbar.setTitle("My Packages");
                                break;

                            case R.id.nav_profile:
                                Intent intent = new Intent(User.this, AdminUpdateUser.class);
                                intent.putExtra("Mode", 2);
                                startActivity(intent);
                                break;


//                                fragmentClass = AdminProfileFragment.class;
//                                mToolbar.setTitle("My Packages");
//                                break;
                            default:
                                fragmentClass = AdminHomeFragment.class;
                        }

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.userContent, fragment).commit();

                        // Highlight the selected item has been done by NavigationView
//                        menuItem.setChecked(true);
                        // Close the navigation drawer
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

}
