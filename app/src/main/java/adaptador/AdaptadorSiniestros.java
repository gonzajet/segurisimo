package adaptador;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.ArrayList;

import l.gonza.segurisimo.R;
import model.UserSiniestro;

public class AdaptadorSiniestros extends RecyclerView.Adapter<AdaptadorSiniestros.SiniestrosViewHolder> implements View.OnClickListener{

    ArrayList<UserSiniestro> listaUsuariosSiniestro;
    private View.OnClickListener listener;

    public AdaptadorSiniestros(ArrayList<UserSiniestro> listaUsuariosSiniestro) {
        this.listaUsuariosSiniestro = listaUsuariosSiniestro;
    }

    @NonNull
    @Override
    public SiniestrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view,null,false);
        view.setOnClickListener(this);
        return new SiniestrosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiniestrosViewHolder holder, int position) {
        holder.textViewNombre.setText(listaUsuariosSiniestro.get(position).getNombre());
        holder.textViewEstado.setText(listaUsuariosSiniestro.get(position).getEstado());
    }

    @Override
    public int getItemCount() {
        return listaUsuariosSiniestro.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class SiniestrosViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPerfil;
        TextView textViewNombre,textViewEstado;

        public SiniestrosViewHolder(View itemView) {
            super(itemView);
            imageViewPerfil = itemView.findViewById(R.id.imageViewPerfil);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
}
