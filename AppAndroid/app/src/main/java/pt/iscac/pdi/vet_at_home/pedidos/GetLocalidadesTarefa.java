package pt.iscac.pdi.vet_at_home.pedidos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pt.iscac.pdi.vet_at_home.R;
import pt.iscac.pdi.vet_at_home.modelo.Localidade;
import pt.iscac.pdi.vet_at_home.modelo.Localidades;

public class GetLocalidadesTarefa extends AsyncTask<String, String, String> {

        private static final String strURL = "http:///patricia-pdi.atwebpages.com/Localidade.php";
        private Context context;
        private String autStatus;
        //context: serve para passar classes
        // private ProgressDialog pDialog;
        private HttpURLConnection urlConnection;

        public GetLocalidadesTarefa(Context context) {
            this.context = context;
            // this.pDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//        pDialog = new ProgressDialog(context);
//        pDialog.setMessage("A marcar consulta...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(true);
//        pDialog.show();
        }

        /**
         * Saving product
         */
        protected String doInBackground(String... params) {

            StringBuilder result = new StringBuilder();

            try {
                //URL url = new URL(params[0]);
                URL url = new URL(strURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestProperty("Accept", "application/json");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                Log.v("json_received:", result.toString());

                JSONObject jsonObject = new JSONObject(result.toString());

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
        protected void onPostExecute(String result) {
            // dismiss the dialog once product updated
            // pDialog.dismiss();

            try {
                //Do something with the JSON string
                JSONObject jsonObject = new JSONObject(result).getJSONObject("content");
                Log.v("json array recebido: ", jsonObject.toString());

                Gson gson = new Gson();
                // Converter string JSON em classe java.
                Localidades localidades = gson.fromJson(jsonObject.toString(), Localidades.class);

                // Type listType = new TypeToken<List<Localidade>>() {}.getType();
                // List<Localidade> localidadesList = gson.fromJson(jsonObject.toString(), listType);



                for (Localidade local : localidades.getLocalidades()) {
                    Log.v("localidade recebida: ", local.getId()+":"+local.getNome());
                }

                Activity activity = (Activity) context;
                Spinner opcoesLocalidades=(Spinner) activity.findViewById(R.id.spinnerLocalidades);

                List<String> nomeLocalidades = new ArrayList<>();

                for (Localidade nomeLocal : localidades.getlocalidades())
                {
                    nomeLocalidades.add(nomeLocal.getNome());
                }

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, nomeLocalidades);
                //ArrayAdapter<Localidade> dataAdapter = new ArrayAdapter<Localidade>(activity, android.R.layout.simple_spinner_item, localidades.getLocalidades());

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                opcoesLocalidades.setAdapter(dataAdapter);

            } catch (JSONException e) {
                Log.v("ERRO: ", e.getMessage());
                e.printStackTrace();
            }

            if (autStatus != null && autStatus.equals("0")) {
                String text = "Erro na rede.";
                //Context context = getApplicationContext();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }