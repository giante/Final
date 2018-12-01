package co.quindio.sena.tutorialwebservice.interfaces;


import co.quindio.sena.tutorialwebservice.fragments.BienvenidaFragment;
import co.quindio.sena.tutorialwebservice.fragments.ConsultarListaUsuariosFragment;
import co.quindio.sena.tutorialwebservice.fragments.ConsultarUsuarioFragment;
import co.quindio.sena.tutorialwebservice.fragments.ConsutarListausuarioImagenFragment;
import co.quindio.sena.tutorialwebservice.fragments.DesarrolladorFragment;
import co.quindio.sena.tutorialwebservice.fragments.HumedadFragment;
import co.quindio.sena.tutorialwebservice.fragments.LogionFragment;
import co.quindio.sena.tutorialwebservice.fragments.Presion_y_AltitudFragment;
import co.quindio.sena.tutorialwebservice.fragments.RegistrarUsuarioFragment;


public interface IFragments extends BienvenidaFragment.OnFragmentInteractionListener,DesarrolladorFragment.OnFragmentInteractionListener,
        RegistrarUsuarioFragment.OnFragmentInteractionListener,ConsultarUsuarioFragment.OnFragmentInteractionListener,
        ConsultarListaUsuariosFragment.OnFragmentInteractionListener,ConsutarListausuarioImagenFragment.OnFragmentInteractionListener
  ,LogionFragment.OnFragmentInteractionListener,HumedadFragment.OnFragmentInteractionListener,Presion_y_AltitudFragment.OnFragmentInteractionListener

{

}
