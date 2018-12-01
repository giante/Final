package co.quindio.sena.tutorialwebservice.fragments;

import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;

import co.quindio.sena.tutorialwebservice.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LogionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LogionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LogionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText campousuario, campopasswd;
    Button btnlogearse;
    JSONArray ja;
    int contador =3;
    TextView intentos;
    private OnFragmentInteractionListener mListener;

    public LogionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LogionFragment newInstance(String param1, String param2) {
        LogionFragment fragment = new LogionFragment();
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
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_logion, container, false);
        campousuario=(EditText) vista.findViewById(R.id.campoUsuario);
        campopasswd =(EditText) vista.findViewById(R.id.campoPass);
        btnlogearse=(Button)vista.findViewById(R.id.btnLogeo);
        btnlogearse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsultarPass("http://192.168.1.43//sensores/consultarusuario.php?usuario="+campousuario.getText().toString());
            }
        });


        return vista;
    }

    private  void ConsultarPass(String URL)
    {

        Log.i("url",""+URL);
        RequestQueue queue =Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    ja=new JSONArray(response);
                    String password=ja.getString(0);
                    if (password.equals(campopasswd.getText().toString()))
                    {

                        Toast.makeText(getContext().getApplicationContext(),"BIENVENIDO",Toast.LENGTH_SHORT).show();
                        Fragment miFragment=new BienvenidaFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
                        //getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();

                    }else
                    {
                        Toast.makeText(getContext().getApplicationContext(),"verifique su contraseña",Toast.LENGTH_SHORT).show();
                       intentos.setVisibility(View.VISIBLE);
                       /*
                        contador--;
                        intentos.setText(Integer.toString(contador));
                        if(contador==0)
                        {
                            btnlogearse.setEnabled(false);
                        }*/
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext().getApplicationContext(),"el usuario no exite en la base de datos",Toast.LENGTH_LONG).show();
                }

            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }



      //inflamos el metod e otro aldo

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
