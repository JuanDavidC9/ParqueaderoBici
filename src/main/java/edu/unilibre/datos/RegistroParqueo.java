package edu.unilibre.datos;

public class RegistroParqueo {

    private Bicicleta bicicleta;
    private String fechaIngreso;
    private String fechaRetiro;
    private String estado;

    public RegistroParqueo(Bicicleta bicicleta, String fechaIngreso) {
        this.bicicleta = bicicleta;
        this.fechaIngreso = fechaIngreso;
        this.estado = "Activo";
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getFechaRetiro() {
        return fechaRetiro;
    }

    public String getEstado() {
        return estado;
    }

    public void retirar(String fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
        this.estado = "Retirado";
    }
}