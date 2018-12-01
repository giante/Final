package co.quindio.sena.tutorialwebservice.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.quindio.sena.tutorialwebservice.R;
import co.quindio.sena.tutorialwebservice.entidades.Usuario;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultarUsuarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultarUsuarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarUsuarioFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    EditText campoDni;
    TextView txtNombre,txtEdad,txtSexo,txtusuario,txtcontaseña;
    Button btnConsultarUsuario;
    ProgressDialog progreso;
   // ImageView campoImagen;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    public ConsultarUsuarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultarUsuarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultarUsuarioFragment newInstance(String param1, String param2) {
        ConsultarUsuarioFragment fragment = new ConsultarUsuarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_consultar_usuario, container, false);

        campoDni= (EditText)vista.findViewById(R.id.campoDni);
        txtNombre= (TextView)vista.findViewById(R.id.txtNombre);
        txtEdad=(TextView)vista.findViewById(R.id.txtEdad);
        txtSexo=(TextView)vista.findViewById(R.id.txtSexo);
        txtusuario=(TextView)vista.findViewById(R.id.txtUsuario);
        txtcontaseña=(TextView)vista.findViewById(R.id.txtContraseña);
        btnConsultarUsuario= (Button) vista.findViewById(R.id.btnConsultarUsuario);
        //campoImagen=(ImageView) vista.findViewById(R.id.imagenId);

       request= Volley.newRequestQueue(getContext());

        btnConsultarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });


        return vista;
    }

    private void cargarWebService() {

        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();

        //String ip=getString(R.string.ip);
           String url ="http://192.168.1.43/sensores/consultar.php?dni_usuario=" +campoDni.getText().toString();


        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
        //VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();

      Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

      Usuario elusuario = new Usuario();
      JSONArray json= response.optJSONArray("usuario");
      JSONObject jsonObject =null;
        try {
            jsonObject=json.getJSONObject(0);
            elusuario.setSexo(jsonObject.optString("sexo "));
            elusuario.setNombre(jsonObject.optString("nombre"));
           // elusuario.setSexo(jsonObject.optString("sexo "));
            elusuario.setUsuario(jsonObject.optString("usuario"));
            elusuario.setPasswd(jsonObject.optString("password"));
           // elusuario.setEdad(jsonObject.optInt("edad"));

        } catch (JSONException e) {
            e.printStackTrace();
        }




       /*Usuario miUsuario=new Usuario();

       JSONArray json1=response.optJSONArray("usuario");
        //JSONArray json=response.optJSONArray("usuario");
        //JSONObject jsonObject=null;
         JSONObject jsonObject=null;
        try {
            jsonObject=json1.getJSONObject(0);
            miUsuario.setNombre(jsonObject.optString("nombre "));
            miUsuario.setEdad(jsonObject.optInt("edad"));
            miUsuario.setSexo(jsonObject.optString("sexo "));
            miUsuario.setUsuario(jsonObject.getString("usuario"));
            miUsuario.setPasswd(jsonObject.optString("password"));
           // miUsuario.setDato(jsonObject.optString("imagen"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

       txtNombre.setText("Nombre :"+elusuario.getNombre());
        //txtEdad.setText(elusuario.getEdad());
        txtSexo.setText("Sexo:"+elusuario.getSexo());
        txtusuario.setText("Nombre Usuario :"+elusuario.getUsuario());
        txtcontaseña.setText("Contraseña"+elusuario.getPasswd());/*


       /* miUsuario.setNombre(jsonObject.optString("nombre"));
        miUsuario.setUsuario(jsonObject.optString("Usuario"));
        miUsuario.setSexo(jsonObject.optString("Sexo"));
        miUsuario.setEdad(jsonObject.optInt("edad"));
        miUsuario.setPaswword(jsonObject.optString("passw"));*/

       // if (miUsuario.getImagen()!=null){
        //    campoImagen.setImageBitmap(miUsuario.getImagen());
       // }else{
          //  campoImagen.setImageResource(R.drawable.img_base);
       // }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
