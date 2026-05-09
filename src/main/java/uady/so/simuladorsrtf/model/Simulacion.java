package uady.so.simuladorsrtf.model;
import uady.so.simuladorsrtf.model.clases.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Simulacion {

    private final ColaLlegada colaLlegada;
    private final ColaRafaga colaRafaga;
    private final ArrayList<Proceso> finalizados;

    public Simulacion(){
        colaLlegada = new ColaLlegada();
        colaRafaga = new ColaRafaga();
        finalizados = new ArrayList<>();
    }

    public ResultadoSimulacion simulacionSRTF(){
        llenarColaLlegada();

        int tiempo = 0;
        double duracionCambioContexto = 0.2;
        Planificador planificador = new Planificador();
        RegistroEstados registroEstados = new RegistroEstados();
        int contCambios = 0;
        int numFinalizados = finalizados.size();
        int numProcesos = colaLlegada.size();

        while (numFinalizados < numProcesos ) {
            while (colaLlegada.llegoProceso(tiempo)){
                colaRafaga.agregarProceso(colaLlegada.getSiguiente());
            }

            if(planificador.hayProcesoEnEjecucion() && planificador.getEnEjecucion().getTiempoRestante() == 0){
                // si hay proceso en ejecucion y le queda 0, el proceso finalizo
                Proceso finalizado = planificador.quitarProceso();  // se quita el proceso
                finalizado.setTiempoFinalizado(tiempo);   //se marca el tiempo que finalizo
                finalizados.add(finalizado);  //se agrega a la lista de finalizados
                registroEstados.setFinEstado(tiempo);    //se guarda el fin del estado (timeline)
                numFinalizados++;

                if(colaRafaga.estaVacia() && numFinalizados < numProcesos){
                    // suma el cambio de contexto cuando se quita un proceso
                    // solo si no hay procesos esperando y no es ultimo
                    contCambios ++;
                }
            }

            if(!colaRafaga.estaVacia()){
                //cola no vacia
                if(planificador.hayProcesoEnEjecucion()){
                    //hay un proceso ejecutandose
                    if(compararTiempos(planificador.getEnEjecucion(),colaRafaga.verFrente())){
                        //comparo tiempos restantes procesoactual con frente de la cola
                        Proceso anterior = planificador.cambioContexto(colaRafaga.getSiguiente());
                        colaRafaga.agregarProceso(anterior);
                        registroEstados.setFinEstado(tiempo);
                        registroEstados.registrarEstado(planificador.getEnEjecucion(), tiempo);
                        contCambios ++; // cuenta el cambio de contexto
                        sumarEspera(duracionCambioContexto); //suma cc a los procesos que ya llegaron
                    }
                }else{
                   //no hay procesos en ejecucion
                    planificador.ponerProceso(colaRafaga.getSiguiente());  // se pone en ejecucion al frente de la cola
                    registroEstados.registrarEstado(planificador.getEnEjecucion(), tiempo);  // se inicia el estado(timeline)

                    if(tiempo != 0){
                        //suma cuando se pone un proceso si no es en tiempo 0
                        contCambios ++;
                        sumarEspera(duracionCambioContexto);
                    }
                }
            }

            if(planificador.hayProcesoEnEjecucion()){
                planificador.ejecutarProceso();
            }

            tiempo ++;
        }

        return new ResultadoSimulacion(finalizados,registroEstados,calcularTiempoEspera(),calcularTiempoTotal(tiempo-1,contCambios,duracionCambioContexto));
    }

    public boolean compararTiempos(Proceso p1, Proceso p2){
        //compara tiempos restantes de dos procesos
        //retorna true si p1 le queda mas tiempo que p2
        return p1.getTiempoRestante() > p2.getTiempoRestante();
    }


    public void llenarColaLlegada(){
        Proceso p1 = new Proceso(1,5,1);
        Proceso p2 = new Proceso(2,4,2);
        Proceso p3 = new Proceso(3,1,5);
        Proceso p4 = new Proceso(4,8,7);
        Proceso p5 = new Proceso(5,3,5);
        colaLlegada.agregarProceso(p1);
        colaLlegada.agregarProceso(p2);
        colaLlegada.agregarProceso(p3);
        colaLlegada.agregarProceso(p4);
        colaLlegada.agregarProceso(p5);
    }

    public void sumarEspera(double tiempoCambioContexto){
        //suma espera de los cambios de contexto a los procesos que hay esperando

        Iterator<Proceso> iterador = colaRafaga.getIterador();
        boolean haySiguiente = iterador.hasNext();
        while (haySiguiente){
            Proceso p = iterador.next();
            double tiempoEspera = p.getTiempoDeEspera();
            p.setTiempoDeEspera( tiempoEspera + tiempoCambioContexto);
            haySiguiente = iterador.hasNext();
        }
    }

    public double calcularTiempoEspera(){
        double tep = 0.0;
        for (Proceso p : finalizados) {
            //tiempo espera = tiempo que finalizo el proceso - tiempo que llego - tiempo de rafaga + los cambios de contexto que vivio
            p.setTiempoDeEspera(p.getTiempoFinalizado() - p.getTiempoLlegada() -
                    p.getTiempoRafaga() + p.getTiempoDeEspera());
            tep += p.getTiempoDeEspera();
        }
        return (tep / (double) finalizados.size());
    }

    public double calcularTiempoTotal(int t, int cc, double tiempoCambioContexto){
        return t + (cc * tiempoCambioContexto);
    }


    public static void main(String[] args) {
        Simulacion s = new Simulacion();
        ResultadoSimulacion r = s.simulacionSRTF();
        r.getRegistroEstados().recorrerEstados();
    }


}
