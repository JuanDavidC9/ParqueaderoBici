package edu.unilibre.datos;

public class Tarifa {
    private double valorPorMinuto = 10.0;

    public Tarifa() {
    }

    public double calcularCosto(long minutos) {
        return minutos * valorPorMinuto;
    }

    public double getValorPorMinuto() {
        return valorPorMinuto;
    }

    public void setValorPorMinuto(double valorPorMinuto) {
        this.valorPorMinuto = valorPorMinuto;
    }
}