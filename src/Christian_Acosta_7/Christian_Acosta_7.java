/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Christian_Acosta_7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 *
 * @author omarr
 */
public class Christian_Acosta_7 {

    private static String turno = "X";  
    private static String[][] tablero = new String[3][3]; 
    private static JButton[][] botones = new JButton[3][3];  
    private static JLabel labelTurno = new JLabel("Turno de X"); 
    private static String jugadorX = "";  
    private static String jugadorO = "";

    public static void main(String[] args) {
        JFrame pantallaInicial = new JFrame("Ingreso de Jugadores");
        pantallaInicial.setSize(300, 200);
        pantallaInicial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantallaInicial.setLayout(new GridLayout(3, 2));

        JLabel labelNombreX = new JLabel("Nombre de Jugador X: ");
        JTextField campoNombreX = new JTextField();
        JLabel labelNombreO = new JLabel("Nombre de Jugador O: ");
        JTextField campoNombreO = new JTextField();

        // Botón para iniciar el juego
        JButton botonIniciar = new JButton("Iniciar Juego");
        botonIniciar.addActionListener((ActionEvent e) -> {
            jugadorX = campoNombreX.getText().trim();  // Obtener el nombre del jugador X
            jugadorO = campoNombreO.getText().trim();  // Obtener el nombre del jugador O
            if (jugadorX.isEmpty() || jugadorO.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingresa ambos nombres");  // Mostrar advertencia si falta un nombre
            } else {
                pantallaInicial.dispose();  // Cerrar la pantalla de ingreso
                iniciarJuego();  // Iniciar el juego
            }
        });

        // Añadir los elementos a la pantalla inicial
        pantallaInicial.add(labelNombreX);
        pantallaInicial.add(campoNombreX);
        pantallaInicial.add(labelNombreO);
        pantallaInicial.add(campoNombreO);
        pantallaInicial.add(botonIniciar);

        pantallaInicial.setVisible(true); 
    }

    private static void iniciarJuego() {
        // Crear la pantalla del juego
        JFrame pantallaJuego = new JFrame("Juego X-0");
        pantallaJuego.setSize(400, 400);
        pantallaJuego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantallaJuego.setLayout(new BorderLayout());

        // Panel para el tablero de juego
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(3, 3));  // El tablero 3x3
        pantallaJuego.add(panelTablero, BorderLayout.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j] = new JButton("");
                botones[i][j].setFont(new Font("Arial", Font.PLAIN, 60));  
                botones[i][j].setFocusPainted(false);
                botones[i][j].setEnabled(true);  
                final int fila = i, columna = j;
                botones[i][j].addActionListener((ActionEvent e) -> {
                    colocarFicha(fila, columna);  
                });
                panelTablero.add(botones[i][j]);
                tablero[i][j] = ""; 
            }
        }

        // Esto muestra los turnos en la parte de arriba
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout());
        panelSuperior.add(labelTurno);  // Etiqueta del turno
        pantallaJuego.add(panelSuperior, BorderLayout.NORTH);

        // Botón para reiniciar el juego
        JButton botonReiniciar = new JButton("Reiniciar");
        botonReiniciar.addActionListener((ActionEvent e) -> reiniciarJuego()); 
        pantallaJuego.add(botonReiniciar, BorderLayout.SOUTH);

        pantallaJuego.setVisible(true);
    }

    private static void colocarFicha(int fila, int columna) {
        //Esto es para colocar la x o el 0 en el lugar
        if (tablero[fila][columna].equals("")) {
            tablero[fila][columna] = turno;
            botones[fila][columna].setText(turno);  
            botones[fila][columna].setEnabled(false);

            if (verificarGanador()) {
                JOptionPane.showMessageDialog(null, "El jugador " + (turno.equals("X") ? jugadorX : jugadorO) + " ha ganado");
                reiniciarJuego();  // Reiniciar el juego si hay un ganador
                return;
            }

            // Verificar si hay empate
            if (esEmpate()) {
                JOptionPane.showMessageDialog(null, "Empate");
                reiniciarJuego();  // Reinicir el juego 
                return;
            }

            // Cambiar el turno al siguiente jugador
            turno = turno.equals("X") ? "O" : "X";
            labelTurno.setText("Turno de " + (turno.equals("X") ? jugadorX : jugadorO));  // Actualizar el texto del turno
        } else {
            JOptionPane.showMessageDialog(null, "Sector ocupado, Escoge otra");  // Esto advierte si hay un lugar ocupado en el juego y le indica al jugador que elija otra por asi decirlo
        }
    }

    private static boolean verificarGanador() {
        // Esto verifica el ganador analizando las celdas del 3x3
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0].equals(turno) && tablero[i][1].equals(turno) && tablero[i][2].equals(turno)) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (tablero[0][i].equals(turno) && tablero[1][i].equals(turno) && tablero[2][i].equals(turno)) {
                return true;
            }
        }
        if (tablero[0][0].equals(turno) && tablero[1][1].equals(turno) && tablero[2][2].equals(turno)) {
            return true;
        }
        if (tablero[0][2].equals(turno) && tablero[1][1].equals(turno) && tablero[2][0].equals(turno)) {
            return true;
        }

        return false;
    }

    private static boolean esEmpate() {
        // Comprueba si hay un empate 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j].equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void reiniciarJuego() {
        // Reinicia el tablero para un nuevo juego
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = "";
                botones[i][j].setText("");
                botones[i][j].setEnabled(true);  // Vuelve a habilitar los botones 
            }
        }
        turno = "X";  
        labelTurno.setText("Turno de " + jugadorX);  // Actualiza el turno en la etiqueta y corre el siguiente
    }
}
