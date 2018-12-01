package co.quindio.sena.tutorialwebservice.entidades;

public class Usuario1 {
    private Integer dni_usuario;
    private String nombre;
    private String usuario;
    private  Integer edad;

    public Integer getDni_usuario() {
        return dni_usuario;
    }

    public void setDni_usuario(Integer dni_usuario) {
        this.dni_usuario = dni_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPaswword() {
        return paswword;
    }

    public void setPaswword(String paswword) {
        this.paswword = paswword;
    }

    private String sexo;
    private  String paswword;
}
