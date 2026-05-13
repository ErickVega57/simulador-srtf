package uady.so.simuladorsrtf.model.clases;

public class Estado {
    private Proceso p;
    private int tiempoInicio;
    private int tiempoFinal;

    public Estado(Proceso p){
        this.p = p;
    }

    public Proceso getP() {
        return p;
    }

    public void setP(Proceso p) {
        this.p = p;
    }

    public int getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(int tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public int getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(int tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }
}
