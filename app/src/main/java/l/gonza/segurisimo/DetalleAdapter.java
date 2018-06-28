package l.gonza.segurisimo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.Siniestros;

public class DetalleAdapter extends BaseAdapter {
    private Context context;

    public DetalleAdapter(Context context, ArrayList<Siniestros> s) {
        this.context = context;
        this.s = s;
    }

    private ArrayList<Siniestros> s;
    @Override


    public int getCount() {
        return s.size();
    }

    @Override
    public Siniestros getItem(int position) {
        return s.get(position);
    }

    @Override
    public long getItemId(int position) {
        return s.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.activity_fila, parent, false);
        Siniestros siniestro= getItem(position);
        TextView titulo= convertView.findViewById(R.id.TextViewFila);

        titulo.setText("hlaaaaa");
        return convertView;
    }
}
