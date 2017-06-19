package io.google.gp_11;

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

import AdminFragments.AdminGuidesFragmemnt;
import AdminFragments.AdminHomeFragment;
import AdminFragments.AdminPackagesFragment;
import AdminFragments.AdminPlacesFragment;
import AdminFragments.AdminProfileFragment;
import AdminFragments.AdminUsersFragment;
import BL.Session;

public class Admin extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private Session session;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new Session(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        mToolbar = (Toolbar) findViewById(R.id.nav_actionbar);
        mToolbar.setTitle("Home");
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);
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

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {

            fm.popBackStack();

        } else {

            super.onBackPressed();

        }
    }


    public void setActionBarTitle(String title) {
        mToolbar.setTitle(title);
    }

    public void setMenuItem(int positItem) {
        navigationView.getMenu().getItem(positItem).setChecked(true);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Fragment fragment = null;
                        Class fragmentClass = AdminHomeFragment.class;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                fragmentClass = AdminHomeFragment.class;
                                break;
                            case R.id.nav_users:
                                fragmentClass = AdminUsersFragment.class;
                                break;
                            case R.id.nav_guides:
                                fragmentClass = AdminGuidesFragmemnt.class;
                                break;
                            case R.id.nav_packages:
                                fragmentClass = AdminPackagesFragment.class;
                                break;
                            case R.id.nav_places:
                                fragmentClass = AdminPlacesFragment.class;
                                break;
                            case R.id.nav_profile:
                                fragmentClass = AdminProfileFragment.class;


//                                Intent intent = new Intent(Admin.this, AdminUpdateUser.class);
//                                intent.putExtra("Mode", 2);
//                                startActivity(intent);
                                break;
                            case R.id.nav_logout:
                                session.setLoggedIn(false);
                                finish();
                                startActivity(new Intent(Admin.this, MainActivity.class));
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
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.adminContent, fragment);
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
}
