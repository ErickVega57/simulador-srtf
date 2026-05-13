package uady.so.simuladorsrtf.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import uady.so.simuladorsrtf.model.ResultadoSimulacion;
import uady.so.simuladorsrtf.model.Simulacion;
import uady.so.simuladorsrtf.model.clases.Estado;
import uady.so.simuladorsrtf.model.clases.Proceso;
import uady.so.simuladorsrtf.model.clases.RegistroEstados;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class SimulacionController {

    private GraphicsContext gc;
    private double unidad;
    private ResultadoSimulacion resultado;
    private int contador;

    @FXML private Rectangle timelineFondo;

    @FXML private Canvas timing;
    @FXML private Button boton;

    //Para la tabla
    @FXML private TableView<Proceso> tablaProcesos;
    @FXML private TableColumn<Proceso, Integer> colProceso;
    @FXML private TableColumn<Proceso, Integer> colLlegada;
    @FXML private TableColumn<Proceso, Integer> colRafaga;

    @FXML private Label tiempoEsperaIndividual;
    @FXML private Label TEP;
    @FXML private Label TTP;
    @FXML private Label porc;

    @FXML
    public void initialize() {
        System.out.println("Entre a initialize");
        Simulacion simulacion = new Simulacion();
        resultado = simulacion.simulacionSRTF();
        contador = 0;
        setUnidad(resultado.getTiempoEjecucion());
        iniciarTabla();


    }

    public void iniciarTabla(){
        // Vincular columnas con atributos de la clase Proceso
        colProceso.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLlegada.setCellValueFactory(new PropertyValueFactory<>("tiempoLlegada"));
        colRafaga.setCellValueFactory(new PropertyValueFactory<>("tiempoRafaga"));


        ObservableList<Proceso> procesos = FXCollections.observableArrayList(resultado.getListaProcesos());
        tablaProcesos.setItems(procesos);
    }



    public void dibujarRectangulo(int inicio,int fin,Color color, String texto){
        int duracion = fin - inicio;
        gc = timing.getGraphicsContext2D();
        // Dibujar rectángulo
        gc.setFill(color);
        gc.fillRect(inicio*unidad, 0, duracion*unidad, 50);
        // Texto
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(texto,
                (inicio * unidad) + 5,
                20);
        gc.setFill(Color.BLACK);
        gc.fillText(String.valueOf(fin),(fin*unidad) - 5, 61);
    }

    public void setUnidad(double tiempoTotal){
        this.unidad = 500.0 / tiempoTotal;
    }

    public void dibujaEstado(){
        RegistroEstados re = resultado.getRegistroEstados();
        Estado estado = re.getEstado(contador);
        String texto = "P" + estado.getP().getId();

        dibujarRectangulo(estado.getTiempoInicio(), estado.getTiempoFinal(), estado.getP().getColorProceso(), texto);
        contador++;
    }


    public void botonSiguiente(ActionEvent actionEvent) {
        boton.setText("Paso " + (contador+1));

        if(contador < resultado.getRegistroEstados().size()){
            dibujaEstado();
            if(contador == 1){
                gc.setFill(Color.BLACK);
                gc.fillText(String.valueOf(0),(0*unidad) , 61);
            }
            if(contador == resultado.getRegistroEstados().size()){
                boton.setText("Calcular");
            }
        }else{
            if(contador > resultado.getRegistroEstados().size()){
                limpiarScene();
                contador = 0;
                boton.setText("Iniciar");
                return;
            }
            actualizarDatos();
            boton.setText("Reiniciar");
            contador++;
        }
    }

    public void actualizarDatos(){
        double tep = resultado.getTiempoEsperaPromedio();
        double ttp = resultado.getTiempoTotalProceso();
        double porcentaje = resultado.getPorcentajeEjecucion() * 100;
        String tiemposDeIndividual = tiemposDeEspera(resultado.getListaProcesos());

        tiempoEsperaIndividual.setText("Tiempo de espera de cada proceso: " + tiemposDeIndividual);

        TEP.setText("Tiempo de espera promedio (TEP): " + String.format("%.2f",tep));
        TTP.setText("Tiempo total de procesamiento (TTP): " + ttp);
        porc.setText("Porcentaje del TTP que consume el TEP: " + String.format("%.2f",porcentaje) + "%");

    }

    public void limpiarScene(){

        // limpiar canvas
        gc = timing.getGraphicsContext2D();
        gc.clearRect(0, 0, timing.getWidth(), timing.getHeight());

        tiempoEsperaIndividual.setText("Tiempo de espera de cada proceso: ");
        TEP.setText("Tiempo de espera promedio (TEP): " );
        TTP.setText("Tiempo total de procesamiento (TTP): " );
        porc.setText("Porcentaje del TTP que consume el TEP: ");

    }

    public String tiemposDeEspera(ArrayList<Proceso> lista){
        String texto = "";
        for (Proceso p : lista){
            texto += "P" + p.getId() + "=" + p.getTiempoDeEspera() + " ";
        }
        return texto;
    }


}