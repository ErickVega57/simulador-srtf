package uady.so.simuladorsrtf.model.clases;

import java.util.ArrayList;

public class RegistroEstados {

    ArrayList<Estado> listaEstados;
    int estadoActual;

    public RegistroEstados(){
        listaEstados = new ArrayList<>();
        estadoActual = 0;
    }

    public void registrarEstado(Proceso enEjecucion, int tiempoInicio){
        Estado estado = new Estado(enEjecucion);
        listaEstados.add(estado);
        estado.setTiempoInicio(tiempoInicio);
        estadoActual ++;
    }

    public void setFinEstado(int tiempoFinal){
        listaEstados.get(estadoActual-1).setTiempoFinal(tiempoFinal);
    }

    public void recorrerEstados(){
        for (Estado e : listaEstados){
            System.out.println("Evento : Proceso" + e.getP().getId() +"["+e.getTiempoInicio() +"-"+e.getTiempoFinal()+"]");
        }
    }

    public Estado getEstado(int indice){
        return listaEstados.get(indice);
    }

    public int size(){
        return listaEstados.size();
    }

}
