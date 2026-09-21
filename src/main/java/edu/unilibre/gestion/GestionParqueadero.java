package edu.unilibre.gestion;

import edu.unilibre.datos.Bicicleta;
import edu.unilibre.datos.Parqueadero;
import edu.unilibre.datos.RegistroParqueo;
import edu.unilibre.datos.Tarifa;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GestionParqueadero {
    private Parqueadero parqueadero;
    private Tarifa tarifa;

    private static LocalDateTime tiempoSimulado = LocalDateTime.now();

    public GestionParqueadero() {
        this.parqueadero = new Parqueadero();
        this.tarifa = new Tarifa();
    }

    public static void avanzarTiempo(int dias) {
        tiempoSimulado = tiempoSimulado.plusDays(dias);
    }

    public static LocalDateTime getTiempoSimulado() {
        return tiempoSimulado;
    }

    public boolean registrarBicicleta(Bicicleta bicicleta) {
        if (parqueadero.hayEspacioDisponible() == false) {
            return false;
        }

        for (int i = 0; i < parqueadero.getRegistros().size(); i++) {
            RegistroParqueo reg = parqueadero.getRegistros().get(i);
            if (reg.getBicicleta().getSerial().equals(bicicleta.getSerial())) {
                if (reg.estaActivo() == true) {
                    return false;
                }
            }
        }

        RegistroParqueo nuevoRegistro = new RegistroParqueo(bicicleta);
        return parqueadero.agregarRegistro(nuevoRegistro);
    }

    public double retirarBicicleta(String serial) {
        RegistroParqueo registro = parqueadero.retirarBicicleta(serial);

        if (registro != null) {
            long minutos = registro.calcularMinutos();
            double costo = tarifa.calcularCosto(minutos);
            return costo;
        } else {
            return -1.0;
        }
    }

    public double calcularIngresosDelDia() {
        double ingresos = 0.0;
        LocalDate hoy = tiempoSimulado.toLocalDate();

        for (int i = 0; i < parqueadero.getRegistros().size(); i++) {
            RegistroParqueo reg = parqueadero.getRegistros().get(i);

            if (reg.isPagado() == true) {
                if (reg.getFechaRetiro() != null) {
                    LocalDate fechaPago = reg.getFechaRetiro().toLocalDate();
                    if (fechaPago.equals(hoy) == true) {
                        long minutos = reg.calcularMinutos();
                        double costo = tarifa.calcularCosto(minutos);
                        ingresos = ingresos + costo;
                    }
                }
            }
        }
        return ingresos;
    }

    public double consultarValorParqueo(String serial) {
        for (int i = 0; i < parqueadero.getRegistros().size(); i++) {
            RegistroParqueo reg = parqueadero.getRegistros().get(i);

            if (reg.getBicicleta().getSerial().equals(serial)) {
                if (reg.estaActivo() == true) {
                    long minutos = reg.calcularMinutos();
                    double costo = tarifa.calcularCosto(minutos);
                    return costo;
                }
            }
        }
        return -1.0;
    }

    public boolean verificarDisponibilidad() {
        return parqueadero.hayEspacioDisponible();
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }
}