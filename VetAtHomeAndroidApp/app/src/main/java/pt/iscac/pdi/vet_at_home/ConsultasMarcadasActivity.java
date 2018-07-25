package pt.iscac.pdi.vet_at_home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pt.iscac.pdi.vet_at_home.modelo.Consulta;
import pt.iscac.pdi.vet_at_home.pedidos.GetConsultasMarcadasTarefa;

/**
 * Created by Patricia Ferreira on 18/05/2018.
 */

public class ConsultasMarcadasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_marcadas);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            Menu menu = navigation.getMenu();
            MenuItem menuItem = menu.getItem(0).setChecked(true);
            //menuItem.setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_add:
                        Intent intent1 = new Intent(ConsultasMarcadasActivity.this, MarcarConsultaActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_pets:
                        //Intent intent3 = new Intent(MainActivity.this, ConsultasMarcadasActivity.class);
                        //startActivity(intent3);
                        break;
                    case R.id.navigation_face:
                        //Intent intent4 = new Intent(MainActivity.this, ConsultasMarcadasActivity.class);
                        //startActivity(intent4);
                        break;
                }
                return false;
            }
        });

        new GetConsultasMarcadasTarefa(this, "3").execute();

    }
}



