package pt.iscac.pdi.vet_at_home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class PerfilClienteEditarActivity extends AppCompatActivity {

    public static Button btnguardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilcliente);

        btnguardar = findViewById(R.id.btnguardar);
    }
}
