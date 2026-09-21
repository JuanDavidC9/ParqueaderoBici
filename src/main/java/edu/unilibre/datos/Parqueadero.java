package edu.unilibre.datos;

import java.util.ArrayList;
import java.util.List;

public class Parqueadero {
    private int capacidadMaxima = 20;
    private List<RegistroParqueo> registros;

    public Parqueadero() {
        this.registros = new ArrayList<>();
    }

    public boolean hayEspacioDisponible() {
        int activos = 0;
        for (int i = 0; i < registros.size(); i++) {
            RegistroParqueo reg = registros.get(i);
            if (reg.estaActivo() == true) {
                activos = activos + 1;
            }
        }

        if (activos < capacidadMaxima) {
            return true;
        } else {
            return false;
        }
    }

    public boolean agregarRegistro(RegistroParqueo registro) {
        if (hayEspacioDisponible() == true) {
            registros.add(registro);
            return true;
        } else {
            return false;
        }
    }

    public RegistroParqueo retirarBicicleta(String serial) {
        for (int i = 0; i < registros.size(); i++) {
            RegistroParqueo reg = registros.get(i);
            if (reg.getBicicleta().getSerial().equals(serial)) {
                if (reg.estaActivo() == true) {
                    reg.registrarRetiro();
                    return reg;
                }
            }
        }
        return null;
    }

    public int getCapacidadDisponible() {
        int activos = 0;
        for (int i = 0; i < registros.size(); i++) {
            RegistroParqueo reg = registros.get(i);
            if (reg.estaActivo() == true) {
                activos = activos + 1;
            }
        }
        return capacidadMaxima - activos;
    }

    public List<RegistroParqueo> getRegistros() {
        return registros;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }
}