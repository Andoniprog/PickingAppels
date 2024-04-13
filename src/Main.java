import edu.princeton.cs.stdlib.StdDraw;

import java.awt.*;

public class Main {

    // Máximo y Mínimo valor del lienzo.
    static double min = -1.0;
    static double max = 1.0;

    // Radio de las manzanas y de la temporera.
    static double radio = 0.02;

    // Coordenadas de la temporera
    private static double xp = 0;
    private static double yp = 0;

    // Arreglo para almacenar las coordenadas de las manzanas

    // La tercera coordenada será una bandera para indicar si la manzana ha sido comida
    private static double[][] coordenadasManzanas = new double[12][3];
    private static int numManzanas = 0;


    // Variable para contar los puntos
    private static int puntos = 0;


    // Variable para controlar el estado del juego
    private static boolean juegoTerminado = false;

    public static void main(String[] args) {
        setupCanvas();

        while (numManzanas < 12) {
            generarManzana();
        }

        while (!juegoTerminado) {
            dibujar();
            moverTemporera();
            verificarColision();
            StdDraw.show(20);
        }

        // Dibujar "JUEGO TERMINADO" una vez que el juego ha finalizado
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 30));
        StdDraw.text(0, 0, "JUEGO TERMINADO");
        StdDraw.show();
    }

    public static void setupCanvas() {
        StdDraw.setXscale(min, max);
        StdDraw.setYscale(min, max);
        StdDraw.enableDoubleBuffering();
    }

    public static void generarManzana() {
        double x = min + Math.random() * (max - min);
        double y = min + Math.random() * (max - min);


        // Agregamos una bandera que indica si la manzana ha sido comida o no (0 significa no comida, 1 significa comida)
        coordenadasManzanas[numManzanas][0] = x;
        coordenadasManzanas[numManzanas][1] = y;


        // Inicialmente la manzana no ha sido comida
        coordenadasManzanas[numManzanas][2] = 0;

        numManzanas++;
    }

    public static void dibujar() {
        StdDraw.clear();
        dibujarManzanas();
        dibujarTemporera();
        dibujarPuntos();
    }

    public static void dibujarManzanas() {
        StdDraw.setPenColor(StdDraw.MAGENTA);
        for (int i = 0; i < numManzanas; i++) {
            double x = coordenadasManzanas[i][0];
            double y = coordenadasManzanas[i][1];
            double comida = coordenadasManzanas[i][2];
            if (comida == 0) {
                StdDraw.filledCircle(x, y, radio);
            }
        }
    }

    public static void dibujarTemporera() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledCircle(xp, yp, radio);
    }

    public static void dibujarPuntos() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        StdDraw.textLeft(min + 0.1 , min + 0.1, "Puntos: " + puntos);
    }

    public static void moverTemporera() {

        // Movimiento de la temporera con las teclas W, A, S y D

        // W
        if (StdDraw.isKeyPressed(87) && yp + radio <= 1.0) {
            yp += 0.01;
        }

        // S
        if (StdDraw.isKeyPressed(83) && yp - radio >= -1.0) {
            yp -= 0.01;
        }

        // A
        if (StdDraw.isKeyPressed(65) && xp - radio >= -1.0) {
            xp -= 0.01;
        }

        // D
        if (StdDraw.isKeyPressed(68) && xp + radio <= 1.0) {
            xp += 0.01;
        }
    }

    public static void verificarColision() {
        for (int i = 0; i < numManzanas; i++) {
            double xManzana = coordenadasManzanas[i][0];
            double yManzana = coordenadasManzanas[i][1];
            double distancia = Math.sqrt(Math.pow(xManzana - xp, 2) + Math.pow(yManzana - yp, 2));
            if (distancia <= 2 * radio && coordenadasManzanas[i][2] == 0) { // Si la distancia entre la temporera y la manzana es menor o igual al doble del radio (es decir, colisión) y la manzana no ha sido comida
                puntos++;

                // Marcamos la manzana como comida
                coordenadasManzanas[i][2] = 1;
                if (puntos >= 12) {
                    juegoTerminado = true;
                }


                // Importante: actualizar la pantalla para eliminar la manzana actual
                dibujar();
                break;
            }
        }
    }
}
