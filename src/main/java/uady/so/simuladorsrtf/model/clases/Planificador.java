package uady.so.simuladorsrtf.model.clases;

public class Planificador {
    /*
    Clase Palnificador -> hace uso de la cola de rafaga y de llegada,
    tiene un atributo Proceso que muestra al proceso que se esta ejecutando actualmente

    metodos:
    - ejecutar(Proceso){
        pone al proceso como proceso en ejecucion y le resta -1 al tiempo restante de dicho proceso
    - quitarProceso()-quita el proceso que esta en ejecucion -> retorna el proceso que se estaba finalizando
    - cambio de contexto
    }

     */
    private Proceso enEjecucion;

    public Planificador(){
        enEjecucion = null;
    }

    public Proceso quitarProceso(){
        Proceso p = null;
        if(enEjecucion!=null) {
            p = enEjecucion;
            enEjecucion = null;
        }
        return p;
    }

    public void ponerProceso(Proceso proceso){
        enEjecucion = proceso;
    }

    public Proceso cambioContexto(Proceso nuevo){
        Proceso anterior = quitarProceso();
        ponerProceso(nuevo);
        return anterior;
    }

    public boolean hayProcesoEnEjecucion(){
        return enEjecucion != null;
    }

    public void ejecutarProceso(){
        enEjecucion.setTiempoRestante(enEjecucion.getTiempoRestante() - 1);
    }

    public Proceso getEnEjecucion() {
        return enEjecucion;
    }
}
