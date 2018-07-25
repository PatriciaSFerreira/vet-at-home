package pt.iscac.pdi.vet_at_home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import pt.iscac.pdi.vet_at_home.pedidos.GetAnimaisTarefa;
import pt.iscac.pdi.vet_at_home.pedidos.GetLocalidadesTarefa;
import pt.iscac.pdi.vet_at_home.pedidos.GetVeterinariosTarefa;
import pt.iscac.pdi.vet_at_home.pedidos.MarcarConsultaTarefa;

public class MarcarConsultaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String valorOpcaoAnimal;
    private String valorOpcaoLocalidade;
    private String valorOpcaoMedico;

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_add:
                    return true;
                case R.id.navigation_pets:
                    return true;
                case R.id.navigation_face:
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            Menu menu = navigation.getMenu();
            MenuItem menuItem = menu.getItem(1).setChecked(true);
            //menuItem.setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent2 = new Intent(MarcarConsultaActivity.this, ConsultasMarcadasActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_add:
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


        Spinner opcoes = (Spinner) findViewById(R.id.spinnerOpcoes);
        opcoes.setOnItemSelectedListener(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Opções, android.R.layout.simple_spinner_item);
        opcoes.setAdapter(adapter);

        Spinner opcoesLocalidades = (Spinner) findViewById(R.id.spinnerLocalidades);
        opcoesLocalidades.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.OpçõesLocalidades, android.R.layout.simple_spinner_item);
        opcoesLocalidades.setAdapter(adapter);

        Spinner opcoesMedicos = (Spinner) findViewById(R.id.spinnerMedicos);
        opcoesMedicos.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.OpçõesMedicos, android.R.layout.simple_spinner_item);
        opcoesMedicos.setAdapter(adapter);

        new GetVeterinariosTarefa(this, "1").execute();
        new GetLocalidadesTarefa(this).execute();
        new GetAnimaisTarefa(this, "3").execute();
    }

    public void marcarConsulta(View view) {
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this).create();

        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        String userLogged = sharedPreferences.getString("username", "defaultValue");

        CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.calendarView);
        long selectedDate = simpleCalendarView.getDate();

        Log.v("msg", "a enviar mensagem");
        new MarcarConsultaTarefa(valorOpcaoAnimal, valorOpcaoLocalidade, valorOpcaoMedico, userLogged, selectedDate, MarcarConsultaActivity.this).execute();
        Log.v("msg", "mensagem enviada");

//        AlertDialog.setTitle("Marcação de Consulta");
//        AlertDialog.setMessage("Consulta marcada com sucesso!");
//        AlertDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerOpcoes) {
            valorOpcaoAnimal = (String) parent.getItemAtPosition(position);
            Log.v("valor animal: ", valorOpcaoAnimal);
        } else if (parent.getId() == R.id.spinnerLocalidades) {
            valorOpcaoLocalidade = (String) parent.getItemAtPosition(position);
            Log.v("valor localidade: ", valorOpcaoLocalidade);
        } else if (parent.getId() == R.id.spinnerMedicos) {
            valorOpcaoMedico = (String) parent.getItemAtPosition(position);
            Log.v("valor medico: ", valorOpcaoMedico);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent.getId() == R.id.spinnerOpcoes) {
            valorOpcaoAnimal = "";
        } else if (parent.getId() == R.id.spinnerLocalidades) {
            valorOpcaoLocalidade = "";
        } else if (parent.getId() == R.id.spinnerMedicos) {
            valorOpcaoMedico = "";
        }
    }
}
