package uady.so.simuladorsrtf.model;
import uady.so.simuladorsrtf.model.clases.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Simulacion {

    private ColaLlegada colaLlegada;
    private ColaRafaga colaRafaga;
    private ArrayList<Proceso> finalizados;

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
        RegistroEstados re = new RegistroEstados();
        int contCambios = 0;
        int numFinalizados = finalizados.size();

        int numProcesos = colaLlegada.size();
        System.out.println(colaLlegada.size());

        while (numFinalizados < numProcesos ) {
            System.out.println("TIEMPO >" + tiempo);
            while (colaLlegada.llegoProceso(tiempo)){
                colaRafaga.agregarProceso(colaLlegada.getSiguiente());
                System.out.println("Llego proceso en tiempo " + tiempo);
            }

            if(planificador.hayProcesoEnEjecucion() && planificador.getEnEjecucion().getTiempoRestante() == 0){
                System.out.println("El proceso " + planificador.getEnEjecucion().getId() + " ha finalizado");
                Proceso finalizado = planificador.quitarProceso();
                finalizado.setTiempoFinalizado(tiempo);
                finalizados.add(finalizado);
                System.out.println("FIN ESTADO");
                re.setFinEstado(tiempo);

                numFinalizados++;

                if(colaRafaga.estaVacia() && numFinalizados < numProcesos){
                    // suma el cambio de contexto cuando se quita un proceso
                    // solo si no hay procesos esperando y no es ultimo
                    contCambios ++;
                    System.out.println("[1]==CAMBIO DE CONTEXTO==");
                }

            }


            if(!colaRafaga.estaVacia()){
                System.out.println("Frente de la cola = Proceso " + colaRafaga.verFrente().getId());
                //llego un proceso en este tiempo
                System.out.println("Hay procesos en la cola ");


                if(planificador.hayProcesoEnEjecucion()){
                    System.out.println("[inicio]se esta ejecutando el Proceso"
                            + planificador.getEnEjecucion().getId());

                    if(compararTiempos(planificador.getEnEjecucion(),colaRafaga.verFrente())){
                        Proceso anterior = planificador.cambioContexto(colaRafaga.getSiguiente());
                        colaRafaga.agregarProceso(anterior);

                        re.setFinEstado(tiempo);
                        System.out.println("FIN ESTADO");
                        re.registrarEstado(planificador.getEnEjecucion(), tiempo);
                        System.out.println("NUEVO ESTADO");


                        System.out.println("[ahora]se esta ejecutando el Proceso"
                                + planificador.getEnEjecucion().getId());
                        contCambios ++; // cuenta el cambio de contexto
                        sumarEspera(duracionCambioContexto);
                        System.out.println("\n[2]==CAMBIO DE CONTEXTO==\n");

                    }
                }else{
                    System.out.println("No se esta ejecutando ningun proceso");
                    planificador.ponerProceso(colaRafaga.getSiguiente());
                    System.out.println("[ahora]se esta ejecutando el Proceso"
                            + planificador.getEnEjecucion().getId());

                    System.out.println("INICIO ESTADO");
                    re.registrarEstado(planificador.getEnEjecucion(), tiempo);

                    if(tiempo != 0){
                        //suma cuando se pone un proceso si no es en tiempo 0
                        contCambios ++;
                        sumarEspera(duracionCambioContexto);
                        System.out.println("\n[3]==CAMBIO DE CONTEXTO==\n");
                    }
                }
            }


            if(planificador.hayProcesoEnEjecucion()){
                System.out.println("TIENE: " +planificador.getEnEjecucion().getTiempoRestante() + " restante");
                planificador.ejecutarProceso();
            }

            tiempo ++;
        }

        re.recorrerEstados();
        double tep = calcularTiempoEspera();
        System.out.println("TEP = " + tep);
        double tte = calcularTiempoTotal(tiempo-1,contCambios,duracionCambioContexto);
        System.out.println("TTE = " +tte);
        return new ResultadoSimulacion(finalizados,re,tep,tte);
    }

    public boolean compararTiempos(Proceso p1, Proceso p2){
        return p1.getTiempoRestante() > p2.getTiempoRestante();
    }


    public void llenarColaLlegada(){
        System.out.println("LENAR COLA");
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
            System.out.println("SUMO TE" + p.getTiempoDeEspera());
            haySiguiente = iterador.hasNext();
        }
    }

    public double calcularTiempoEspera(){
        double tep = 0.0;
        for (Proceso p : finalizados) {
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
        System.out.println("Tiempo espera p2");
        System.out.println(r.getTiempoEsperaProceso(2));
        System.out.println(r.getPorcentajeEjecucion());
    }


}
