package uady.so.simuladorsrtf.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import uady.so.simuladorsrtf.model.ResultadoSimulacion;
import uady.so.simuladorsrtf.model.Simulacion;
import uady.so.simuladorsrtf.model.clases.Estado;
import uady.so.simuladorsrtf.model.clases.Proceso;
import uady.so.simuladorsrtf.model.clases.RegistroEstados;


public class HelloController {

    @FXML
    private Canvas timing;

    private GraphicsContext gc;

    private double unidad;
    private ResultadoSimulacion resultado;
    private int contador;

    @FXML
    public void initialize() {
        System.out.println("Entre a initialize");
        Simulacion simulacion = new Simulacion();
        resultado = simulacion.simulacionSRTF();
        contador = 0;
        setUnidad(resultado.getTiempoTotalProceso());
    }
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void dibujarRectangulo(int inicio,int fin,Color color, String texto){
        int duracion = fin - inicio;
        gc = timing.getGraphicsContext2D();
        // Dibujar rectángulo
        gc.setFill(color);
        gc.fillRect(inicio*unidad, 0, duracion*unidad, 200);
        // Texto
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(texto,
                (inicio * unidad) + 5,
                20);
    }

    public void setUnidad(double tiempoTotal){
         this.unidad = 500 / tiempoTotal;
    }

    public void dibujaEstado(){
        RegistroEstados re = resultado.getRegistroEstados();
        Estado estado = re.getEstado(contador);
        String texto = "P" + estado.getP().getId();
        dibujarRectangulo(estado.getTiempoInicio(), estado.getTiempoFinal(), estado.getP().getColorProceso(), texto);
        contador++;
    }


    public void botonSiguiente(ActionEvent actionEvent) {
        dibujaEstado();
    }
}
