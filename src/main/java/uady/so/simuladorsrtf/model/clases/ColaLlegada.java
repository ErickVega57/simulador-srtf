package uady.so.simuladorsrtf.model.clases;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ColaLlegada {

    private final PriorityQueue<Proceso> cola;
    private int tiempoTotal;

    public ColaLlegada(){
        // ordena los procesos por orden de llegada
        cola = new PriorityQueue<>(
                Comparator.comparingInt(Proceso::getTiempoLlegada)
        );
    }
    public void agregarProceso(Proceso proceso){
        cola.add(proceso);
        tiempoTotal += proceso.getTiempoRafaga();
    }

    public Proceso getSiguiente(){
        return cola.poll();
    }

    public boolean estaVacia(){
        return cola.isEmpty();
    }
    public boolean llegoProceso(int tiempo){

        return !cola.isEmpty() && cola.peek().getTiempoLlegada() == tiempo;
    }

    public int size(){
        return cola.size();
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

}
