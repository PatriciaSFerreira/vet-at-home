package pt.iscac.pdi.vet_at_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PerfilClienteActivity extends AppCompatActivity {

    public static Button btneditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfilcliente);
        new perfilcliente().execute();
        btneditar = findViewById(R.id.btneditar);


    }
    public void editarperfil(View view) {
        Intent intent = new Intent(this, PerfilClienteEditarActivity.class);
        startActivity(intent);
    }
    private class perfilcliente extends AsyncTask<String, Void, String> {
        private String strURL = "http://maelis.atwebpages.com/PerfilCliente.php";
        ProgressDialog pDialog = new ProgressDialog(PerfilClienteActivity.this);
        HttpURLConnection urlConnection;
        ArrayList<Cliente> clienteArrayList = new ArrayList<>();
        private String userId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PerfilClienteActivity.this);
            pDialog.setMessage("A carregar os dados");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();
            try {
                //URL url = new URL(params[0]);
                //String urlString = String.format("%s?userId=%s", strURL, this.userId);
                URL url = new URL(strURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                Log.v("json_received:", result.toString());
                JSONObject jsonObject = new JSONObject(result.toString());
                String autStatus = jsonObject.getString("status");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();

        }
/*
        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            clienteArrayList = new ArrayList<Cliente>();

            try {
                //Do something with the JSON string
                JSONObject jsonObject = new JSONObject(result).getJSONObject("content");
                // get a JSONArray from inside an object
                JSONArray jsonCliente = jsonObject.getJSONArray("cliente");

                Log.v("Json Array cliente: ", jsonCliente.toString());

                if (jsonCliente != null) {
                    for (int i = 0; i < jsonCliente.length(); i++) {
                        JSONObject jsonP = jsonCliente.getJSONObject(i);
                        Cliente cliente = new Cliente(
                                Integer.parseInt(jsonP.getString("id")),
                                jsonP.getString("username"),
                                jsonP.getString("email"),
                                jsonP.getString("nif"),
                                jsonP.getString("morada");
                        //Log.v("Animal: ", animal.getMarca());
                        clienteArrayList.add(cliente);
                    }
                }
            } catch (Exception e) {
                Log.v("JSON: ", e.getMessage());
            }

            AnimalAdapter animalAdapter;
            animalAdapter = new AnimalAdapter(PerfilClienteActivity.this, clienteArrayList);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(animalAdapter);
*/
    }
    }



