package pt.iscac.pdi.vet_at_home.pedidos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class MarcarConsultaTarefa extends AsyncTask<String, String, String> {

    private String valorOpcaoAnimal;
    private String valorOpcaoLocalidade;
    private String valorOpcaoMedico;
    private Context context;
    private long selectedDate;

    String autStatus;
        private String strURL = "http://patricia-pdi.atwebpages.com/Consulta.php";

        public MarcarConsultaTarefa(String valorOpcaoAnimal, String valorOpcaoLocalidade,
                                    String valorOpcaoMedico, String userLogged, long selectedDate, Context context)
        {
            this.valorOpcaoAnimal = valorOpcaoAnimal;
            this.valorOpcaoLocalidade = valorOpcaoLocalidade;
            this.valorOpcaoMedico = valorOpcaoMedico;
            this.selectedDate = selectedDate;
            this.context = context;

        }

        //context: serve para passar classes
        ProgressDialog pDialog = new ProgressDialog(context);
        HttpURLConnection urlConnection;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("A marcar consulta...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Saving product
         */
        protected String doInBackground(String... params) {

            StringBuilder result = new StringBuilder();

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("clienteId", 3);
                jsonObject.put("animalId", valorOpcaoAnimal);
                jsonObject.put("dataHora", new Date(selectedDate));
                jsonObject.put("localidadeId", valorOpcaoLocalidade);
                jsonObject.put("vetId", valorOpcaoMedico);

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

            if (autStatus != null && autStatus.equals("0")) {
                String text = "Erro na marcação. Volte a tentar.";
                //Context context = getApplicationContext();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            } else {
                String text = "Marcação efetuada com sucesso.";
                //Context context = getApplicationContext();
                Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }
