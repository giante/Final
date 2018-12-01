package co.quindio.sena.tutorialwebservice.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.quindio.sena.tutorialwebservice.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Presion_y_AltitudFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Presion_y_AltitudFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Presion_y_AltitudFragment extends Fragment  implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//parametros

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean sensorpresente;
    private TextView Vpresion;
    private TextView Valtitud;
    private OnFragmentInteractionListener mListener;

    public Presion_y_AltitudFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Presion_y_AltitudFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Presion_y_AltitudFragment newInstance(String param1, String param2) {
        Presion_y_AltitudFragment fragment = new Presion_y_AltitudFragment();
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
    //inflamos el metodo aqui debido a la naturaleza del fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_humedad, container, false);
        return inflater.inflate(R.layout.fragment_presion_y_altitud, container, false);
    }
////
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Vpresion = (TextView)getView().findViewById(R.id.txtPresion);
        Valtitud = (TextView) getView().findViewById(R.id.txtAltitud);
        mSensorManager = (SensorManager)getActivity() . getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            sensorpresente = true;
        } else {
            sensorpresente = false;
            Vpresion.setText("¡El sensor de presión no está disponible!");
            Valtitud.setText("No se puede calcular la altitud, ya que el sensor de presión no está disponible.");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sensorpresente) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sensorpresente) {
            mSensorManager.unregisterListener(this);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float presion= event.values[0];
        Vpresion.setText("La presión es: " + presion    +" hpa");
        float altitud = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, presion);
       Valtitud.setText("La altitud actual es " + altitud);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mSensor = null;
    }
////////////////////////////////////////////////////


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
