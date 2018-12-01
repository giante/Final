package co.quindio.sena.tutorialwebservice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.quindio.sena.tutorialwebservice.R;
import co.quindio.sena.tutorialwebservice.entidades.Usuario;

/**
 * Created by CHENAO on 6/08/2017.
 */

public class UsuariosImagenAdapter extends RecyclerView.Adapter<UsuariosImagenAdapter.UsuariosHolder>{

    List<Usuario> listaUsuarios;

    public UsuariosImagenAdapter(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    @Override
    public UsuariosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarios_list_image,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new UsuariosHolder(vista);
    }

    @Override
    public void onBindViewHolder(UsuariosHolder holder, int position) {
        holder.txtDni.setText(listaUsuarios.get(position).getDni().toString());
        holder.txtUsuario.setText(listaUsuarios.get(position).getUsuario().toString());
        holder.txtSexo.setText(listaUsuarios.get(position).getSexo().toString());
        holder.txtedad.setText(listaUsuarios.get(position).getEdad().toString());
        holder.txtpasswd.setText(listaUsuarios.get(position).getPasswd().toString());

      //  if (listaUsuarios.get(position).getImagen()!=null){
       //     holder.imagen.setImageBitmap(listaUsuarios.get(position).getImagen());
      //  }else{
            holder.imagen.setImageResource(R.drawable.img_base);
      //  }
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuariosHolder extends RecyclerView.ViewHolder{

        TextView txtDni,txtUsuario,txtSexo,txtedad,txtpasswd;
        ImageView imagen;

        public UsuariosHolder(View itemView) {
            super(itemView);
            txtDni= (TextView) itemView.findViewById(R.id.campoDni);
            txtUsuario= (TextView) itemView.findViewById(R.id.campoUsuario);
            txtSexo= (TextView) itemView.findViewById(R.id.campoSexo);
            txtedad=(TextView)itemView.findViewById(R.id.campoEdad);
            txtpasswd=(TextView)itemView.findViewById(R.id.campoPass);

            imagen=(ImageView) itemView.findViewById(R.id.idImagen);
        }
    }
}
