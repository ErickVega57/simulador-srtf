package uady.so.simuladorsrtf.model;

import uady.so.simuladorsrtf.model.clases.Proceso;
import uady.so.simuladorsrtf.model.clases.RegistroEstados;

import java.util.ArrayList;

public class ResultadoSimulacion {

    private final ArrayList<Proceso> listaProcesos;
    private final RegistroEstados registroEstados;
    private final double tiempoEsperaPromedio;
    private final double tiempoTotalProceso;

    public ResultadoSimulacion(ArrayList<Proceso> listaProcesos, RegistroEstados registroEstados,
                               double tiempoEsperaPromedio, double tiempoTotalProceso) {

        this.listaProcesos = listaProcesos;
        this.registroEstados = registroEstados;
        this.tiempoEsperaPromedio = tiempoEsperaPromedio;
        this.tiempoTotalProceso= tiempoTotalProceso;
    }

    public ArrayList<Proceso> getListaProcesos() {
        return listaProcesos;
    }

    public RegistroEstados getRegistroEstados() {
        return registroEstados;
    }

    public double getTiempoEsperaPromedio() {
        return tiempoEsperaPromedio;
    }

    public double getTiempoTotalProceso() {
        return tiempoTotalProceso;
    }

    public double getPorcentajeEjecucion(){;
        return tiempoEsperaPromedio/tiempoTotalProceso;
    }

    public double getTiempoEsperaProceso(int id){
        boolean encontrado = false;
        Proceso buscado = null;
        int i = 0;
        while (!encontrado){
            if(listaProcesos.get(i).getId() == id){
                buscado = listaProcesos.get(i);
                encontrado = true;
            }
            i++;
        }
        return buscado.getTiempoDeEspera();
    }

}
