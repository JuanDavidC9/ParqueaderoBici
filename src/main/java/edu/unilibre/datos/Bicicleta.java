package edu.unilibre.datos;

public class Bicicleta {
    private String serial;
    private String color;
    private String identificacionPropietario;

    public Bicicleta(String serial, String color, String identificacionPropietario) {
        this.serial = serial;
        this.color = color;
        this.identificacionPropietario = identificacionPropietario;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getIdentificacionPropietario() {
        return identificacionPropietario;
    }

    public void setIdentificacionPropietario(String identificacionPropietario) {
        this.identificacionPropietario = identificacionPropietario;
    }
}