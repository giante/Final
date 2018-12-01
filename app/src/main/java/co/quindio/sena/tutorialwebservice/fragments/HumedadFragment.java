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
 * {@link HumedadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HumedadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HumedadFragment extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //VALORES
    private SensorManager mSensorManager;
    private Sensor mHumiditySensor;
    private Sensor mTemperaturaSensor;
    private boolean sensordehumedadpresente;
    private boolean isTemperatureSensorPresent;
    private TextView valorRelativoHumedad;
    private TextView valorAbsolutoHumedad;
    private TextView Vpuntorocio;
    private float ultimoValorhumedad = 0;

    private OnFragmentInteractionListener mListener;

    public HumedadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HumedadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HumedadFragment newInstance(String param1, String param2) {
        HumedadFragment fragment = new HumedadFragment();
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
        View view= inflater.inflate(R.layout.fragment_humedad, container, false);
        return inflater.inflate(R.layout.fragment_humedad, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        valorRelativoHumedad = ( TextView )getView().findViewById ( R .id .txtHrelativa);
       valorAbsolutoHumedad = ( TextView ) getView().findViewById ( R .id.txtHabsoluta);
        Vpuntorocio= ( TextView )getView().findViewById ( R .id .txtProcio);
        mSensorManager = ( SensorManager ) getActivity(). getSystemService ( Context . SENSOR_SERVICE );

        if (mSensorManager . getDefaultSensor ( Sensor . TYPE_RELATIVE_HUMIDITY ) != null ) {
            mHumiditySensor = mSensorManager . getDefaultSensor ( Sensor . TYPE_RELATIVE_HUMIDITY );
            sensordehumedadpresente =  true ;
        }
		else {
            valorRelativoHumedad . setText ( "¡ El sensor de humedad relativa no está disponible! " );
            valorAbsolutoHumedad . setText ( "¡ No se puede calcular la humedad absoluta, ya que el sensor de humedad relativa no está disponible! " );
            Vpuntorocio . setText ( "¡ No se puede calcular el punto de rocío, ya que el sensor de humedad relativa no está disponible! " );
            sensordehumedadpresente =  false ;
        }

        if (mSensorManager . getDefaultSensor ( Sensor . TYPE_AMBIENT_TEMPERATURE )!=  null ) {
            mTemperaturaSensor = mSensorManager . getDefaultSensor ( Sensor . TYPE_AMBIENT_TEMPERATURE );
            isTemperatureSensorPresent =  true ;
        } else {
            valorAbsolutoHumedad . setText ( "¡ No se puede calcular la humedad absoluta, ya que el sensor de temperatura no está disponible! " );
            Vpuntorocio. setText ( "¡ No se puede calcular el punto de rocío, ya que el sensor de temperatura no está disponible! " );
            isTemperatureSensorPresent =  false ;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensordehumedadpresente) {
            mSensorManager . registerListener ( this , mHumiditySensor, SensorManager . SENSOR_DELAY_NORMAL );
        }
        if (isTemperatureSensorPresent) {
            mSensorManager . registerListener ( this ,mTemperaturaSensor, SensorManager . SENSOR_DELAY_NORMAL );
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isTemperatureSensorPresent|| isTemperatureSensorPresent) {
            mSensorManager . unregisterListener ( this );
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSensorManager =  null ;
        mHumiditySensor =  null ;
        mTemperaturaSensor =  null ;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY) {
           valorRelativoHumedad.setText("La humedad Relativa en % es : " + event.values[0]);
            ultimoValorhumedad = event.values[0];
        } else if(event.sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE) {
            if(ultimoValorhumedad !=0) {
                float temperature = event.values[0];
                float absoluteHumidity = calcularhumedadabsoluta(temperature, ultimoValorhumedad);
                valorAbsolutoHumedad.setText("La humedad absoluta a la temperatura: " + temperature + " is: " + absoluteHumidity);
                float dewPoint = calcularpuntoderocio(temperature, ultimoValorhumedad);
                Vpuntorocio.setText("El punto de rocío a la temperatura: " + temperature + " is: " + dewPoint);
            }
        }

    }
    ///metodos para calcular la humedad


    /* Meaning of the constants
     Dv: Absolute humidity in grams/meter3
     m: Mass constant
     Tn: Temperature constant
     Ta: Temperature constant
     Rh: Actual relative humidity in percent (%) from phone’s sensor
     Tc: Current temperature in degrees C from phone’ sensor
     A: Pressure constant in hP
     K: Temperature constant for converting to kelvin
     */
    public float calcularhumedadabsoluta(float temperatura, float hrelativa)
    {
        float Dv = 0;
        float m = 17.62f;
        float Tn = 243.12f;
        float Ta = 216.7f;
        float Rh = hrelativa;
        float Tc = temperatura;
        float A = 6.112f;
        float K = 273.15f;

        Dv =   (float) (Ta * (Rh/100) * A * Math.exp(m*Tc/(Tn+Tc)) / (K + Tc));

        return Dv;
    }

    /* Meaning of the constants
    Td: Dew point temperature in degrees Celsius
    m: Mass constant
    Tn: Temperature constant
    Rh: Actual relative humidity in percent (%) from phone’s sensor
    Tc: Current temperature in degrees C from phone’ sensor
    */
    public float calcularpuntoderocio(float temperatura, float hrelativa)
    {
        float Td = 0;
        float m = 17.62f;
        float Tn = 243.12f;
        float Rh = hrelativa;
        float Tc = temperatura;

        Td = (float) (Tn * ((Math.log(Rh/100) + m*Tc/(Tn+Tc))/(m - (Math.log(Rh/100) + m*Tc/(Tn+Tc)))));

        return Td;
    }
    ///

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
