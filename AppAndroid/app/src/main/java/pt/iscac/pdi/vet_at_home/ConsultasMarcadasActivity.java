package pt.iscac.pdi.vet_at_home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pt.iscac.pdi.vet_at_home.modelo.Consulta;

/**
 * Created by Patricia Ferreira on 18/05/2018.
 */

public class ConsultasMarcadasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_marcadas);

        ListView listaDeConsultas = (ListView) findViewById(R.id.listaConsultas);

        List<Consulta> consultas = new ArrayList<>();

        ArrayAdapter<Consulta> adapter = new ArrayAdapter<Consulta>(this,
                android.R.layout.simple_list_item_1, consultas);

        listaDeConsultas.setAdapter(adapter);
    }


}



