package pt.iscac.pdi.vet_at_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
    }

    public void autenticar(View view) {
        Log.v("accao", "Autenticar utilizador na Web.");
        new AutenticarUtilizador().execute();
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

                String autStatus = jsonObject.getString("status");
                String autContent = jsonObject.getString("content");

                if (autStatus.equals("0")) {
                    Log.v("aut_error:", autStatus);

                    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("loggedin", "false");
                    editor.putString("username", "none");
                    editor.commit();

                } else {
                    Log.v("aut_success:", autStatus);

                    //Criacao de duas variaveis par controlo da sessao do utilizador
                    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("loggedin", "true");
                    editor.putString("username", autContent);
                    editor.commit();

                    Log.v("aut_username:", sharedPreferences.getString("username", "defaultValue"));

                }

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
            // dismiss the dialog once product uupdated
            pDialog.dismiss();

            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

            Log.v("aut_username:", sharedPreferences.getString("username", "defaultValue"));

            if (sharedPreferences.getString("loggedin", "false").equals("true"))
            {
                findViewById(R.id.imageView).setVisibility(View.GONE);
                findViewById(R.id.editText).setVisibility(View.GONE);
                findViewById(R.id.editText4).setVisibility(View.GONE);
                findViewById(R.id.textView).setVisibility(View.GONE);
                findViewById(R.id.textView3).setVisibility(View.GONE);
                findViewById(R.id.button2).setVisibility(View.GONE);
                findViewById(R.id.textView4).setVisibility(View.GONE);
                findViewById(R.id.textView5).setVisibility(View.VISIBLE);
                findViewById(R.id.button4).setVisibility(View.VISIBLE);
            } else {
                String text = "Erro na autenticacao. Volte a tentar.";
                Context context = getApplicationContext();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
}