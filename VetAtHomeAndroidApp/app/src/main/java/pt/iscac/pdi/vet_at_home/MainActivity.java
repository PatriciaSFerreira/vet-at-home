package pt.iscac.pdi.vet_at_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //public static Button buttonlogin;
    //public static Button buttonregistar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

    }

    public void autenticar(View view) {
        Log.v("accao", "Autenticar utilizador na Web.");
        new AutenticarUtilizador().execute();
    }

    public void loginregistar(View view) {
        Intent intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);
    }
    public void seuspets() {
        Intent intent = new Intent(this, SeusPets.class);
        startActivity(intent);
    }
    //Esta classe vai chamar o servico web que permite a autenticacao
    private class AutenticarUtilizador extends AsyncTask<String, String, String> {

        private String strURL = "http://maelis.atwebpages.com/Login.php";
        ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("A autenticar utilizador ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         */
        protected String doInBackground(String... params) {

            EditText username = (EditText) findViewById(R.id.editText);
            EditText password = (EditText) findViewById(R.id.editText4);

            StringBuilder result = new StringBuilder();

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", username.getText().toString());
                jsonObject.put("password", password.getText().toString());

                Log.v("json_send:", jsonObject.toString());

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
        protected void onPostExecute(String result) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();
            try {
                SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("status").equals("0")) {
                    sharedPreferences.edit().putString("loggedIn", "false");
                    String text = "Erro na autenticacao. Volte a tentar.";
                    Context context = getApplicationContext();
                    Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    JSONObject userInfo = jsonObject.getJSONObject("content");
                    sharedPreferences.edit().putString("loggedIn", "true");
                    sharedPreferences.edit().putString("id", userInfo.getString("ID"));
                    sharedPreferences.edit().putString("username", userInfo.getString("Username"));// usar o username em caso de querer aceder noutra ativ
                    String text = "Login efetuado com sucesso";
                    Context context = getApplicationContext();
                    Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                    t.show();
                    seuspets();

                    /*findViewById(R.id.imageView).setVisibility(View.GONE);
                    findViewById(R.id.editText).setVisibility(View.GONE);
                    findViewById(R.id.editText4).setVisibility(View.GONE);
                    findViewById(R.id.textView).setVisibility(View.GONE);
                    findViewById(R.id.textView3).setVisibility(View.GONE);
                    findViewById(R.id.login_login_btn).setVisibility(View.GONE);
                    findViewById(R.id.login_registar).setVisibility(View.GONE);
                    findViewById(R.id.textView5).setVisibility(View.VISIBLE);
                    findViewById(R.id.button4).setVisibility(View.VISIBLE);*/

                }
            } catch (JSONException e){
                e.printStackTrace();
        }
    }
}


    /*public void voltar(View view){
        Log.v("accao","Voltar ao ecra anterior.");
        Intent intent = new Intent(this, SeusPets.class);
        startActivity(intent);
    }*/
}