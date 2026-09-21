package edu.unilibre.datos;

import edu.unilibre.gestion.GestionParqueadero;
import java.time.Duration;
import java.time.LocalDateTime;

public class RegistroParqueo {
    private Bicicleta bicicleta;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaRetiro;
    private boolean pagado;

    public RegistroParqueo(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
        this.fechaIngreso = GestionParqueadero.getTiempoSimulado();
        this.fechaRetiro = null;
        this.pagado = false;
    }

    public void registrarRetiro() {
        this.fechaRetiro = GestionParqueadero.getTiempoSimulado();
        this.pagado = true;
    }

    public long calcularMinutos() {
        LocalDateTime fin;

        if (fechaRetiro != null) {
            fin = fechaRetiro;
        } else {
            fin = GestionParqueadero.getTiempoSimulado();
        }

        long minutos = Duration.between(fechaIngreso, fin).toMinutes();

        if (minutos <= 0) {
            minutos = 1;
        }

        return minutos;
    }

    public boolean estaActivo() {
        if (pagado == true) {
            return false;
        } else {
            return true;
        }
    }

    public Bicicleta getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(Bicicleta bicicleta) {
        this.bicicleta = bicicleta;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDateTime fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
}