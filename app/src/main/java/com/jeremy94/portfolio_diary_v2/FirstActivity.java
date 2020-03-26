package com.jeremy94.portfolio_diary_v2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.jeremy94.portfolio_diary_v2.Fragment.Fragment_1;
import com.jeremy94.portfolio_diary_v2.Fragment.Fragment_2;
import com.jeremy94.portfolio_diary_v2.Fragment.Fragment_3;
import com.jeremy94.portfolio_diary_v2.Fragment.Fragment_4;


public class FirstActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;


    boolean isDrawerOpened;

    private BottomNavigationView bottomNaV;

    private NavigationView naviV;

    private FragmentManager fragmentManager;
    private Fragment_1 fragment_1;
    private Fragment_2 fragment_2;
    private Fragment_3 fragment_3;
    private Fragment_4 fragment_4;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        fragmentManager = getSupportFragmentManager();
        fragment_1 = new Fragment_1();
        fragment_2 = new Fragment_2();
        fragment_3 = new Fragment_3();
        fragment_4 = new Fragment_4();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment_1).commitAllowingStateLoss();

        naviV = (NavigationView)findViewById(R.id.main_drawer_view);
        naviV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int pos = item.getItemId();
                Menu menu = bottomNaV.getMenu();
              /*  for(int i=0; i<menu.size(); i++) {
                    MenuItem menuitem = menu.getItem(i);
                    menuitem.setChecked(false);
                }
              */
                switch (pos){
                    case R.id.menu1:
                        changeView(0);
                        onBackPressed();
                        MenuItem menuitem = menu.getItem(0);
                        menuitem.setChecked(true);
                        break;

                    case R.id.menu2:
                        changeView(1);
                        onBackPressed();
                        menuitem = menu.getItem(1);
                        menuitem.setChecked(true);
                        break;

                    case R.id.menu3:
                        changeView(2);
                        onBackPressed();
                        menuitem = menu.getItem(2);
                        menuitem.setChecked(true);
                        break;
                    case R.id.menu4:
                        changeView(3);
                        onBackPressed();
                        menuitem = menu.getItem(3);
                        menuitem.setChecked(true);
                        break;
                }
                return  true;
            }
        });

        bottomNaV = (BottomNavigationView)findViewById(R.id.navigationView);
        bottomNaV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int pos = item.getItemId();

                switch (pos){
                    case R.id.item1:
                        changeView(0);
                        break;

                    case R.id.item2:
                        changeView(1);
                        break;

                    case R.id.item3:
                        changeView(2);
                        break;
                    case R.id.item4:
                        changeView(3);
                        break;
                }
                return  true;
            }
        });


        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                isDrawerOpened=true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                isDrawerOpened=false;
            }
        };
        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();
    }

    private void changeView(int i){
        transaction = fragmentManager.beginTransaction();

        switch (i){
            case 0:
                transaction.replace(R.id.frameLayout, fragment_1).commitAllowingStateLoss();
                break;

            case 1:
                transaction.replace(R.id.frameLayout, fragment_2).commitAllowingStateLoss();
                break;

            case 2:
                transaction.replace(R.id.frameLayout, fragment_3).commitAllowingStateLoss();
                break;

            case 3:
                transaction.replace(R.id.frameLayout, fragment_4).commitAllowingStateLoss();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpened){
            drawerLayout.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }
}
