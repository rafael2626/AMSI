package vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.projeto.R;
import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String email;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open,R.string.close);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        fragmentManager=getSupportFragmentManager();

        navigationView.setNavigationItemSelectedListener(this);

        carregarCabecalho();
        carregarFragmentoInical();
    }

    private boolean carregarFragmentoInical() {
            Menu menu = navigationView.getMenu();
            MenuItem item=menu.getItem(0);
            item.setChecked(true);
            return onNavigationItemSelected(item);
    }

    private void carregarCabecalho() {
        email = getIntent().getStringExtra("EMAIL").toString();
        SharedPreferences sharedPreferencesInfoUser=getSharedPreferences("DADOS_USER", Context.MODE_PRIVATE);
        if(email != null)
        {
            //armazenar no Shared
            SharedPreferences.Editor editor=sharedPreferencesInfoUser.edit();
            editor.putString("EMAIL", email);
            editor.apply();
        }else {
            email = sharedPreferencesInfoUser.getString("EMAIL", "Email não identificado");
        }
        //buscar o email no shared

        View hView = navigationView.getHeaderView(0);
        TextView tvEmail = hView.findViewById(R.id.tvEmail);
        tvEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId())
        {
            case  R.id.navreservas:
                fragment= new ListaReservasFragment();
                setTitle(item.getTitle());
                break;

        }
        if(fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment,fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}