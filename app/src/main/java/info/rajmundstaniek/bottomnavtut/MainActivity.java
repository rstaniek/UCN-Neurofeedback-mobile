package info.rajmundstaniek.bottomnavtut;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import info.rajmundstaniek.bottomnavtut.navBar.fragments.ChartsFragment;
import info.rajmundstaniek.bottomnavtut.navBar.fragments.DevicesFragment;
import info.rajmundstaniek.bottomnavtut.navBar.fragments.HomeFragment;

public class MainActivity
        extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mTextMessage;

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setTitle(R.string.title_home);
        loadFragment(new HomeFragment());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                toolbar.setTitle(R.string.title_home);
                break;
            case R.id.navigation_dashboard:
                fragment = new DevicesFragment();
                toolbar.setTitle(R.string.title_devices);
                break;
            case R.id.navigation_notifications:
                fragment = new ChartsFragment();
                toolbar.setTitle(R.string.title_charts);
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
            return true;
        }
        else return false;
    }
}
