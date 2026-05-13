package uady.so.simuladorsrtf.model.clases;

import javafx.scene.paint.Color;

public class Proceso {

    private final int id;
    private final int tiempoRafaga ;
    private final int tiempoLlegada;
    private double tiempoDeInicio = -1;
    private int tiempoRestante;
    private double tiempoDeEspera = 0;
    private boolean finalizado = false;
    private int tiempoFinalizado;
    private Color colorProceso;
    private static final AsignarColor color = new AsignarColor();

    public Proceso(int id, int tiempoRafaga, int tiempoLlegada) {
        this.id = id;
        this.tiempoRafaga = tiempoRafaga;
        this.tiempoLlegada = tiempoLlegada;
        this.tiempoRestante = tiempoRafaga;
        this.colorProceso = color.asignaColor();
    }

    public int getId() {
        return id;
    }

    public int getTiempoRafaga() {
        return tiempoRafaga;
    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public double getTiempoDeInicio() {
        return tiempoDeInicio;
    }

    public void setTiempoDeInicio(double tiempoDeInicio) {
        this.tiempoDeInicio = tiempoDeInicio;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }

    public double getTiempoDeEspera() {
        return tiempoDeEspera;
    }

    public void setTiempoDeEspera(double tiempoDeEspera) {
        this.tiempoDeEspera = tiempoDeEspera;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public int getTiempoFinalizado() {
        return tiempoFinalizado;
    }

    public void setTiempoFinalizado(int tiempoFinalizado) {
        this.tiempoFinalizado = tiempoFinalizado;
    }

    public Color getColorProceso() {
        return colorProceso;
    }
}
