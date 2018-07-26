package pt.iscac.pdi.vet_at_home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringJoiner;

public class SeusPets extends AppCompatActivity {

    public static Button addanimal;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seuspets);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            Menu menu = navigation.getMenu();
            MenuItem menuItem = menu.getItem(2).setChecked(true);
            //menuItem.setChecked(true);

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent1 = new Intent(SeusPets.this, ConsultasMarcadasActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_add:
                        Intent intent2 = new Intent(SeusPets.this, MarcarConsultaActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.navigation_pets:
                        break;
                    case R.id.navigation_face:
                        //Intent intent4 = new Intent(MainActivity.this, ConsultasMarcadasActivity.class);
                        //startActivity(intent4);
                        break;
                }
                return false;
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "3");
        this.userId = userId;

        new AnimalListar().execute();

        addanimal = findViewById(R.id.addanimal);
    }

    public void adicionarAnimal(View view) {
        Intent intent = new Intent(this, AdicionarPet.class);
        startActivity(intent);
    }


    private class AnimalListar extends AsyncTask<String, Void, String> {
        private String strURL = "http://maelis.atwebpages.com/SeusPets.php";

        ProgressDialog pDialog = new ProgressDialog(SeusPets.this);
        HttpURLConnection urlConnection;
        ArrayList<Animal> animalArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SeusPets.this);
            pDialog.setMessage("A carregar os seus animais");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {
            StringBuilder result = new StringBuilder();
            try {
                //URL url = new URL(params[0]);
                String urlString = String.format("%s?userId=%s", strURL, userId);

                System.out.println("URL: " + urlString);

                URL url = new URL(urlString);
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

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            animalArrayList = new ArrayList<Animal>();

            try {
                //Do something with the JSON string
                JSONObject jsonObject = new JSONObject(result).getJSONObject("content");
                // get a JSONArray from inside an object
                JSONArray jsonAnimal = jsonObject.getJSONArray("animais");

                Log.v("Json Array animal: ", jsonAnimal.toString());

                if (jsonAnimal != null) {
                    for (int i = 0; i < jsonAnimal.length(); i++) {
                        JSONObject jsonP = jsonAnimal.getJSONObject(i);
                        Animal animal = new Animal(
                                Integer.parseInt(jsonP.getString("id")),
                                jsonP.getString("nome"),
                                Integer.parseInt(userId),
                                jsonP.getString("raca"),
                                jsonP.getInt("idade"));
                        //Log.v("Animal: ", animal.getMarca());
                        animalArrayList.add(animal);
                    }
                }
            } catch (Exception e) {
                Log.v("JSON: ", e.getMessage());
            }

            AnimalAdapter animalAdapter;
            animalAdapter = new AnimalAdapter(SeusPets.this, animalArrayList);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(animalAdapter);


        }


    }
}