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
import co.quindio.sena.tutorialwebservice.interfaces.IFragments;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TemperaturaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TemperaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperaturaFragment extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SensorManager mSensorManager;
    Sensor  mSensor;
    private boolean  sensoractivo;
    private TextView vTemperatura;

    private OnFragmentInteractionListener mListener;

    public TemperaturaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperaturaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperaturaFragment newInstance(String param1, String param2) {
        TemperaturaFragment fragment = new TemperaturaFragment();
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
      View view= inflater.inflate(R.layout.fragment_temperatura, container, false);
        return inflater.inflate(R.layout.fragment_temperatura, container, false);
    }
    //implementamos la lectura del sensor


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSensorManager = ( SensorManager ) getActivity().getSystemService(Context.SENSOR_SERVICE);
     vTemperatura = (TextView)getView().findViewById ( R.id.mtemperatura);
        if (mSensorManager . getDefaultSensor ( Sensor . TYPE_AMBIENT_TEMPERATURE ) !=  null ) {
            mSensor = mSensorManager . getDefaultSensor ( Sensor . TYPE_AMBIENT_TEMPERATURE );
            sensoractivo =  true ;
        } else {
            vTemperatura . setText ( "¡ El sensor de temperatura ambiente no está disponible! " );
            sensoractivo =  false ;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    vTemperatura.setText("la temperatura en grados °C es:" + event.values[0]);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(sensoractivo) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mSensor = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(sensoractivo) {
            mSensorManager.unregisterListener(this);
        }
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




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


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
