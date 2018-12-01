package co.quindio.sena.tutorialwebservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.quindio.sena.tutorialwebservice.R;
import co.quindio.sena.tutorialwebservice.entidades.Usuario;

/**
 * Created by CHENAO on 6/08/2017.
 */

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UsuariosHolder>{

    List<Usuario> listaUsuarios;

    public UsuariosAdapter(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_list,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {

        holder.txtDni.setText(listaUsuarios.get(position).getDni().toString());
        holder.txtnombre.setText(listaUsuarios.get(position).getNombre().toString());
        holder.txtUsuario.setText(listaUsuarios.get(position).getUsuario().toString());
        holder.txtSexo.setText(listaUsuarios.get(position).getSexo().toString());
        holder.txtedad.setText(listaUsuarios.get(position).getEdad().toString());
        holder.txtpasswd.setText(listaUsuarios.get(position).getPasswd().toString());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView txtDni,txtUsuario,txtSexo,txtedad,txtpasswd,txtnombre;

        public UsuariosHolder(View itemView) {
            super(itemView);

            txtDni= (TextView) itemView.findViewById(R.id.campoDni);
            txtnombre=(TextView)itemView.findViewById(R.id.campoNombre);
            txtUsuario= (TextView) itemView.findViewById(R.id.campoUsuario);
            txtSexo= (TextView) itemView.findViewById(R.id.campoSexo);
            txtedad=(TextView)itemView.findViewById(R.id.campoEdad);
            txtpasswd=(TextView)itemView.findViewById(R.id.campoPass);

        }
    }
}
