package edu.unilibre.gestion;

import edu.unilibre.datos.Parqueadero;
import edu.unilibre.datos.Tarifa;

public class GestionParqueadero {

    private Parqueadero parqueadero;
    private Tarifa tarifa;

    public GestionParqueadero(Parqueadero parqueadero, Tarifa tarifa) {
        this.parqueadero = parqueadero;
        this.tarifa = tarifa;
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }
}