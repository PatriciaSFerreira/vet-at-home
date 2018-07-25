package pt.iscac.pdi.vet_at_home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class AnimalAdapter extends BaseAdapter {


        Context context;
        ArrayList data;
        private static LayoutInflater inflater = null;

        public AnimalAdapter(Context context, ArrayList data) {
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.layout_animal_item, null);
            //ImageView foto = (ImageView) vi.findViewById(R.id.foto);
            TextView textNome = (TextView) vi.findViewById(R.id.nome);
            TextView textRaca = (TextView) vi.findViewById(R.id.raca);
            TextView textIdade = (TextView) vi.findViewById(R.id.idade);
            //Caso queira editar perfil do animal
            //Button btn8 = (Button) vi.findViewById(R.id.button8);
            //btn8.setFocusable(false);
            //btn8.setClickable(false);

            //O site awardspace nao deixa invocar as imagens diretamente
            //Foi preciso colocar as imagens noutro lado
            //String strURL = "http://res.cloudinary.com/dbcauiwaz/image/upload/v1521583715/"  + ((Animal)getItem(position)).getFoto();
            //Log.v("image url: ", strURL);

            //new DownloadImageTask((ImageView) vi.findViewById(R.id.imageView)).execute(strURL);

            textNome.setText(((Animal)getItem(position)).getNome());
            textRaca.setText(((Animal)getItem(position)).getRaca());
            textIdade.setText(((Animal)getItem(position)).getIdade());

            vi.setTag(((Animal)getItem(position)));
            return vi;
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

           public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
           }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                if (result!=null)
                    bmImage.setImageBitmap(result);
            }
        }


    }


