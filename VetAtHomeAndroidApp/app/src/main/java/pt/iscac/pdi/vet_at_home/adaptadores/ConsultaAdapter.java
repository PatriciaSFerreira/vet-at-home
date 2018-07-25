package pt.iscac.pdi.vet_at_home.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pt.iscac.pdi.vet_at_home.R;
import pt.iscac.pdi.vet_at_home.modelo.Consulta;

public class ConsultaAdapter extends BaseAdapter {

    private ArrayList<Consulta> listaConsultas;
    private static LayoutInflater inflater = null;
    private Context context;

    public ConsultaAdapter(Context context, ArrayList<Consulta> listaConsultas) {
        this.context = context;
        this.listaConsultas = listaConsultas;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listaConsultas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaConsultas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.layout_consultas_lista_item, null);
        }

        TextView textAnimal = (TextView) vi.findViewById(R.id.nomeAnimal);
        TextView textDataHora = (TextView) vi.findViewById(R.id.dataHora);
        TextView textVeterinario = (TextView) vi.findViewById(R.id.nomeVeterinario);

        Consulta consulta = (Consulta) getItem(position);

        textAnimal.setText(consulta.getAnimal());
        String dataStr = converteDataParaString( consulta.getDataHora() );
        textDataHora.setText( dataStr );
        textVeterinario.setText(consulta.getVeterinario());

        vi.setTag(((Consulta)getItem(position)));
        return vi;
    }

    private String converteDataParaString(Date data)
    {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(data);
    }
}
