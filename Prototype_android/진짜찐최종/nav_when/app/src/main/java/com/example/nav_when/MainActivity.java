package com.example.nav_when;

import android.content.Intent;
import android.os.Bundle;

import com.example.nav_when.fragment.HomeFragment;
import com.example.nav_when.fragment.select_person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


//실행시 야무지게 터지는 어플
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    // BackPressHandler 객체 선언, 할당
    private BackPressHandler backPressHandler = new BackPressHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View nav_header_view = navigationView.getHeaderView(0);
        TextView nav_header_id_name = (TextView) nav_header_view.findViewById(R.id.textView_name);
        TextView nav_header_id_email = (TextView) nav_header_view.findViewById(R.id.textView);
        nav_header_id_name.setText(getUserName());
        nav_header_id_email.setText(getUserMail());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_select,
                R.id.nav_cal, R.id.nav_per_dic, R.id.nav_365_dic)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if (item.getItemId()==R.id.action_settings){
            Intent mint = new Intent(MainActivity.this, settings.class);
            startActivity(mint);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        // Default
        backPressHandler.onBackPressed();
    }
    public String getUserName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userName = "로그인을 해주세요";
        if(user!=null){
            userName = user.getDisplayName();
        }

        return userName;
    }
    public String getUserMail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userMail = "";
        if(user!=null){
            userMail = user.getEmail();
        }
        return userMail;
    }

}
