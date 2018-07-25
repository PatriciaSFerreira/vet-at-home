package pt.iscac.pdi.vet_at_home;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class AdicionarPet extends AppCompatActivity {

    public static EditText nome;
    public static EditText raca;
    public static EditText idade;
    //public static Button foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pet);
    }
    public void adicionar(View view) {
        new AdicionarAnimal().execute();
    }

    private class AdicionarAnimal extends AsyncTask<String, String, String> {

        String autStatus;
        private String strURL = "http://maelis.atwebpages.com/AdicionarAnimal.php";

        ProgressDialog pDialog = new ProgressDialog(AdicionarPet.this);
        HttpURLConnection urlConnection;
        SharedPreferences sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        String userLogged = sharedPreferences.getString("username", "defaultValue");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdicionarPet.this);
            pDialog.setMessage("A adicionar animal...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         */
        protected String doInBackground(String... params) {

            nome = (EditText) findViewById(R.id.nome);
            idade = (EditText) findViewById(R.id.idade);
            raca = (EditText) findViewById(R.id.raca);
            //btn5 = (Button) findViewById(R.id.button4);
            StringBuilder result = new StringBuilder();

            if (nome.getText().length() == 0 || idade.getText().length() == 0 || raca.getText().length() == 0) {
                pDialog.dismiss();
                autStatus = "2";
                return "0";
            }


            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nome", nome.getText().toString());
                jsonObject.put("raca", raca.getText().toString());
                jsonObject.put("idade", idade.getText().toString());

                Log.v("json_send:", jsonObject.toString());

                //URL url = new URL(params[0]);
                URL url = new URL(strURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("POST");

                OutputStream os = urlConnection.getOutputStream();
                os.write(jsonObject.toString().getBytes("UTF-8"));
                os.close();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                Log.v("json_received:", result.toString());

                jsonObject = new JSONObject(result.toString());

                autStatus = jsonObject.getString("status");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product updated
            pDialog.dismiss();

            if (autStatus.equals("0")) {
                String text = "Erro. Volte a tentar.";
                Context context = getApplicationContext();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            } else if (autStatus.equals("2")) {
                String text = "Campos todos obrigatorios.";
                Context context = getApplicationContext();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            } else{
                String text = "Animal adicionado com sucesso";
                Context context = getApplicationContext();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

}