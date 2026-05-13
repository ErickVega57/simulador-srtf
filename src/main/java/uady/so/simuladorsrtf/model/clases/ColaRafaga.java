package uady.so.simuladorsrtf.model.clases;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class ColaRafaga {
    /*
    Clase cola rafaga similar a cola llegada
    Esta Clase Ordena a los procesos por tiempo restante de menor a mayor
    usar Priority Queue.

    metodos :
    - agregarProceso(Proceso)
    - estaVacio() return (si la lista esta vacia)
    -
    - getSiguiente() return Proceso [saca el proceso que esta en la cabeza de la cola]
     */

    private final PriorityQueue<Proceso> cola;

    public ColaRafaga(){
        // ordena los procesos por orden de tiempo de rafaga restante
        cola = new PriorityQueue<>(
                Comparator.comparingInt(Proceso::getTiempoRestante).
                        thenComparingInt(Proceso::getTiempoLlegada)
                //si empatan compara llegada gana el que llega antes
        );
    }

    public void agregarProceso(Proceso proceso){
        cola.add(proceso);
    }

    public Proceso sacarFrente(){
        return cola.poll();
    }

    public boolean estaVacia(){
        return cola.isEmpty();
    }
    public boolean llegoProceso(int tiempo){

        return !cola.isEmpty() && cola.peek().getTiempoLlegada() == tiempo;
    }


    public Proceso verFrente(){
        return cola.peek();
    }

    public Iterator<Proceso> getIterador() {
        return cola.iterator();
    }

}
