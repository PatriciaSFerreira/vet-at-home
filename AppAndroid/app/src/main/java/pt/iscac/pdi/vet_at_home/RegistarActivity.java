package pt.iscac.pdi.vet_at_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


public class RegistarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
    }

    public void criar(View view){
        Log.v("accao","Autenticar utilizador na Web.");
        new CriarCliente().execute();
    }

    //Esta classe vai chamar o servico web que permite a autenticacao
    private class CriarCliente extends AsyncTask<String, String, String> {

        private String strURL = "http://maelis.atwebpages.com/Registar.php";

        ProgressDialog pDialog = new ProgressDialog(RegistarActivity.this);
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegistarActivity.this);
            pDialog.setMessage("A criar utilizador ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         * */
        protected String doInBackground(String... params) {

            EditText username = (EditText) findViewById(R.id.editText5);
            EditText email = (EditText) findViewById(R.id.editText6);
            EditText password = (EditText) findViewById(R.id.editText7);

            StringBuilder result = new StringBuilder();

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", username.getText().toString());
                jsonObject.put("email", email.getText().toString());
                jsonObject.put("password", password.getText().toString());

                Log.v("json_send:",jsonObject.toString());

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

                Log.v("json_received:",result.toString());

                jsonObject = new JSONObject(result.toString());

                String autStatus = jsonObject.getString("status");
                String autContent = jsonObject.getString("content");

            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }


        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product uupdated
            pDialog.dismiss();

        }


    }
}
