package pt.iscac.pdi.vet_at_home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    //private Spinner opcoes, opcoesLocalidades, opcoesMedicos;
    private String valorOpcaoAnimal;
    private String valorOpcaoLocalidade;
    private String valorOpcaoMedico;
    //private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_add:
                    //mTextMessage.setText(R.string.title_activity_marcar_consulta);
                    return true;
                case R.id.navigation_pets:
                    //mTextMessage.setText(R.string.title_adicionar_animal);
                    return true;
                case R.id.navigation_face:
                    //mTextMessage.setText(R.string.title_perfil);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);

       // mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Spinner opcoes=(Spinner)findViewById(R.id.spinnerOpcoes);
        opcoes.setOnItemSelectedListener(this);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Opções, android.R.layout.simple_spinner_item);
        opcoes.setAdapter(adapter);

        Spinner opcoesLocalidades=(Spinner)findViewById(R.id.spinnerLocalidades);
        opcoesLocalidades.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.OpçõesLocalidades, android.R.layout.simple_spinner_item);
        opcoesLocalidades.setAdapter(adapter);

        Spinner opcoesMedicos=(Spinner)findViewById(R.id.spinnerMedicos);
        opcoesMedicos.setOnItemSelectedListener(this);

        adapter = ArrayAdapter.createFromResource(this, R.array.OpçõesMedicos, android.R.layout.simple_spinner_item);
        opcoesMedicos.setAdapter(adapter);

        new GetVeterinariosTarefa(this).execute();
        new GetLocalidadesTarefa(this).execute();
        new GetAnimaisTarefa(this).execute();
    }

    public void marcarConsulta (View view){
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
        if (parent.getId() == R.id.spinnerOpcoes)
        {
            valorOpcaoAnimal = (String) parent.getItemAtPosition(position);
            Log.v("valor animal: ", valorOpcaoAnimal);
        }
        else if (parent.getId() == R.id.spinnerLocalidades)
        {
            valorOpcaoLocalidade = (String) parent.getItemAtPosition(position);
            Log.v("valor localidade: ", valorOpcaoLocalidade);
        }
        else if (parent.getId() == R.id.spinnerMedicos)
        {
            valorOpcaoMedico = (String) parent.getItemAtPosition(position);
            Log.v("valor medico: ", valorOpcaoMedico);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (parent.getId() == R.id.spinnerOpcoes)
        {
            valorOpcaoAnimal = "";
        }
        else if (parent.getId() == R.id.spinnerLocalidades)
        {
            valorOpcaoLocalidade = "";
        }
        else if (parent.getId() == R.id.spinnerMedicos)
        {
            valorOpcaoMedico = "";
        }
    }
}
