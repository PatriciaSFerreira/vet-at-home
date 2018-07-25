package pt.iscac.pdi.vet_at_home.pedidos;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import pt.iscac.pdi.vet_at_home.R;
import pt.iscac.pdi.vet_at_home.adaptadores.ConsultaAdapter;
import pt.iscac.pdi.vet_at_home.modelo.Consulta;
import pt.iscac.pdi.vet_at_home.modelo.Consultas;

public class GetConsultasMarcadasTarefa extends AsyncTask<String, String, String> {

    private static final String strURL = "http://patricia-pdi.atwebpages.com/ConsultasMarcadas.php";
    private Context context;
    private String autStatus;
    private String userId;
    //context: serve para passar classes
    //private ProgressDialog pDialog;
    private HttpURLConnection urlConnection;

    public GetConsultasMarcadasTarefa(Context context, String userId) {
        this.context = context;
        this.userId = userId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * Saving product
     */
    protected String doInBackground(String... params) {

        StringBuilder result = new StringBuilder();

        try {
            //URL url = new URL(params[0]);
            //String urlString = String.format("%s?userId=%s", strURL, this.userId);
            String urlString = String.format("%s?clienteId=%s", strURL, userId);
            URL url = new URL(urlString);
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
        //pDialog.dismiss();

        try {
            //Do something with the JSON string
            JSONObject jsonObject = new JSONObject(result).getJSONObject("content");
            Log.v("json array recebido: ", jsonObject.toString());

            // Converter string JSON em classe java.

            //Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Consultas consultas = gson.fromJson(jsonObject.toString(), Consultas.class);

            for (Consulta cons : consultas.getConsultas()) {
                Log.v("consulta recebida: ", cons.getAnimal()+":"+cons.getVeterinario()+":"+cons.getDataHora());
            }

            // Inserir valores no spinner da activity.
            Activity activity = (Activity) context;

            // TODO: vai buscar linearLayout.

            ListView listView = (ListView) activity.findViewById(R.id.listaConsultasMarcadas);

            // Ciclo que percorre valores recebidos.
                // TODO: Criar uma textView com a string "<nome-veterinario> <data> <nome-animal>".

            for (Consulta c : consultas.getConsultas())
            {
                //TextView textViewConsulta = new TextView(activity);
                String Stringtexto = String.format("%s %s %s", c.getAnimal(), c.getDataHora().toString(), c.getVeterinario());
                //textViewConsulta.setText(Stringtexto);
                Log.v("consulta", Stringtexto);
            }

            ConsultaAdapter consultaAdapter;
            consultaAdapter = new ConsultaAdapter(context, consultas.getConsultas());
            listView.setAdapter(consultaAdapter);


                // TODO: Adicionar textView ao LinearLayout
//                ((LinearLayout) linearLayout).addView(valueTV);

        } catch (JSONException e) {
            Log.v("ERRO: ", e.getMessage());
            e.printStackTrace();
        }

        if (autStatus != null && autStatus.equals("0")) {
            String text = "Erro na rede.";
            Toast t = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
