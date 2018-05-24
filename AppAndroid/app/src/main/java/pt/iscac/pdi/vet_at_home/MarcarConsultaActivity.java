package pt.iscac.pdi.vet_at_home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MarcarConsultaActivity extends AppCompatActivity {

    Spinner opcoes, opcoesLocalidades, opcoesMedicos;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_add:
                    mTextMessage.setText(R.string.title_activity_marcar_consulta);
                    return true;
                case R.id.navigation_pets:
                    mTextMessage.setText(R.string.title_adicionar_animal);
                    return true;
                case R.id.navigation_face:
                    mTextMessage.setText(R.string.title_perfil);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_consulta);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        opcoes=(Spinner)findViewById(R.id.spinnerOpcoes);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Opções, android.R.layout.simple_spinner_item);
        opcoes.setAdapter(adapter);

        opcoesLocalidades=(Spinner)findViewById(R.id.spinnerLocalidades);

        adapter = ArrayAdapter.createFromResource(this, R.array.OpçõesLocalidades, android.R.layout.simple_spinner_item);
        opcoesLocalidades.setAdapter(adapter);

        opcoesMedicos=(Spinner)findViewById(R.id.spinnerMedicos);

        adapter = ArrayAdapter.createFromResource(this, R.array.OpçõesMedicos, android.R.layout.simple_spinner_item);
        opcoesMedicos.setAdapter(adapter);

    }

    public void marcarConsulta (View view){
        AlertDialog AlertDialog;
        AlertDialog = new AlertDialog.Builder(this).create();
        AlertDialog.setTitle("Marcação de Consulta");
        AlertDialog.setMessage("Consulta marcada com sucesso!");
        AlertDialog.show();
    }

    
}
