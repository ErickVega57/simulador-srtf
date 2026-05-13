package uady.so.simuladorsrtf.model.clases;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class AsignarColor {

    ArrayList<Color> listaColores;

    public AsignarColor(){
        listaColores = new ArrayList<>();
        listaColores.add(Color.CHOCOLATE);
        listaColores.add(Color.LIGHTBLUE);
        listaColores.add(Color.LIGHTGREEN);
        listaColores.add(Color.ORANGE);
        listaColores.add(Color.MEDIUMVIOLETRED);
        listaColores.add(Color.SIENNA);
        listaColores.add(Color.BROWN);
        listaColores.add(Color.DARKBLUE);
    }

    public Color asignaColor(){
        Color color = listaColores.remove(0);
        listaColores.add(color);

        return color;
    }
}
