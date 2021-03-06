
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import interfaz.BoardFrame;
import interfaz.ElegirNivelFrame;
import interfaz.Login;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author sokobanUQ
 */
public class Board extends Thread implements KeyListener {

    /**
     * matriz de botones que es la que conforma el tablero
     */
    private JButton[][] botones;
    private char[][] imagenes;
    private final ImageIcon avatarIcon, cajaIcon, caminoIcon, muroIcon, llegadaIcon, estrellaIcon, llegadaAvatarIcon;
    private int a, b, x, y;
    private ArrayList<Jugador> jugadores;
    private String nombreJugador;
    private Utilidades utilidades;
    private BoardFrame boardNivel;
    private JLabel lblTablero;
    /**
     * pila en la que guardamos las teclas que fueron presionadas para el
     * movimento del munieco
     */
    private Stack<Integer> pasos = new Stack<>();
    /**
     * pila en la que guardamos lo que se saca de la pila de pasos para cuando
     * vamos a rehace los pasos del munieco
     */
    private Stack<Integer> pasosRehacer = new Stack<>();
    /**
     * pila en la que guardamos la posicion en y de la caja en caso de que esta
     * se mueva de lo contrario ponemos -1
     */
    private Stack<Integer> pasosY = new Stack<>();
    /**
     * pila en la que guardamos la posicion en y de la caja en caso de que esta
     * se mueva de lo contrario ponemos -1
     */
    private Stack<Integer> pasosX = new Stack<>();

    /**
     * Instancia de la clase robot que nos sirve para simular que se presiona
     * una tecla.
     */
    private Robot robot;
    /**
     * variable bandera que nos sirve para regular el ingreso a las pilas, no
     * ayuda a controlar cuando se deshace un paso para no tenerlo que agregar a
     * ninguna pila.
     */
    
    public boolean agregar = true;

    private ArchivoLeer leer;
    private String nombreArchivo;
    int puntaje;
    private Login login;

    private JLabel lblpuntajeMovimientos;

    public Stack<Integer> getPasos() {
        return pasos;
    }

    public void setPasos(Stack<Integer> pasos) {
        this.pasos = pasos;
    }

    public boolean isAgregar() {
        return agregar;
    }

    public void setAgregar(boolean agregar) {
        this.agregar = agregar;
    }

    public Board(String nombreArchivo, JLabel puntajeJugador, ArrayList<Jugador> jugadores, String nombre, Login login, BoardFrame elegirNivel, JLabel tablero) {
        this.jugadores = jugadores;
        this.boardNivel = elegirNivel;
        this.login = login;
        this.nombreArchivo = nombreArchivo;
        lblpuntajeMovimientos = puntajeJugador;
        nombreJugador = nombre;
        lblTablero = tablero;
        leer = new ArchivoLeer();
        this.botones = new JButton[20][20];
        utilidades = new Utilidades();
        this.a = 0;
        this.b = 0;

        this.imagenes = new char[20][20];
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
        avatarIcon = new ImageIcon(getClass().getResource("/recursos/avatarIcon.png"));
        muroIcon = new ImageIcon(getClass().getResource("/recursos/muroIcon.png"));
        cajaIcon = new ImageIcon(getClass().getResource("/recursos/cajaIcon.png"));
        llegadaIcon = new ImageIcon(getClass().getResource("/recursos/llegadaIcon.png"));
        estrellaIcon = new ImageIcon(getClass().getResource("/recursos/estrellaIcon.png"));
        llegadaAvatarIcon = new ImageIcon(getClass().getResource("/recursos/llegadaAvatarIcon.png"));
        caminoIcon = new ImageIcon(getClass().getResource("/recursos/caminoIcon.png"));
    }

    // se parametrizan las imágenes
    public void matrizDeBotonesBloqueado(JPanel panel, String ruta) {
        
        
        
        for (int i = 0; i < 20; i++) {
            a = i * 30;
            for (int j = 0; j < 20; j++) {
                b = j * 30;
                botones[i][j] = new JButton();
                botones[i][j].setBounds(a, b, 30, 30);
                botones[i][j].setEnabled(true);
                botones[i][j].addKeyListener(this);
                panel.add(botones[i][j]);
            }

        }
        imagenes = leer.leerArchivo(nombreArchivo, ruta);
        cambiarIconos();
    }

    /**
     * metodo que nos permite guardar la partida actuals
     *
     * @return una matriz de char con los elementos para cuando sea guardo quede
     * de una forma legible
     */
    public char[][] retonarMatrizJuegoActual() {
        for (int i = 0; i < imagenes.length; i++) {
            for (int j = 0; j < imagenes.length; j++) {

                if (botones[i][j].getIcon() == caminoIcon) {
                    imagenes[i][j] = 'C';
                } else if (botones[i][j].getIcon() == muroIcon) {
                    imagenes[i][j] = 'M';
                } else if (botones[i][j].getIcon() == cajaIcon) {
                    imagenes[i][j] = 'B';
                } else if (botones[i][j].getIcon() == llegadaIcon) {
                    imagenes[i][j] = 'L';

                } else if (botones[i][j].getIcon() == avatarIcon) {
                    imagenes[i][j] = 'A';

                }

            }
        }
        return imagenes;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * metodo que nos sirve para saber si podemos agregar a las pilas, se cumple
     * la condicion de que se pueda meter a las pilas solo si no se esta
     * deshaciendo un paso
     *
     * @param codigo, el codigo de la letra que se presiono esto se guarda en la
     * pila pasos.
     * @param x, la posicion en x en donde se movio la caja para guardar en la
     * pila
     * @param y, la posicion en y en donde se movio la caja para guardar en la
     * pila
     */
    public void agregarPila(int codigo, int x, int y) {
        if (agregar) {
            pasos.push(codigo);
            pasosX.push(x);
            pasosY.push(y);
        } else {
            agregar = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        posicionAvatar();
        run();
        if (buscarMuro()) {
            if (e.VK_W == e.getKeyCode() && y >= 1 && botones[x][y - 1].getIcon() != muroIcon) {
                if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                    botones[x][y - 1].setIcon(caminoIcon);
                    botones[x][y - 2].setIcon(cajaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y - 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() == llegadaIcon) {
                    botones[x][y - 1].setIcon(caminoIcon);
                    botones[x][y - 2].setIcon(estrellaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y - 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                    validarSiGano();
                } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                    botones[x][y - 1].setIcon(caminoIcon);
                    botones[x][y - 2].setIcon(cajaIcon);
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x][y - 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x][y - 1].getIcon() == cajaIcon) {

                } else if (botones[x][y - 1].getIcon() == llegadaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y - 1].setIcon(llegadaAvatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y - 1].getIcon() != cajaIcon && botones[x][y - 1].getIcon() != muroIcon && botones[x][y - 1].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x][y - 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x][y - 1].getIcon() == estrellaIcon && y >= 2 && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                    botones[x][y - 1].setIcon(llegadaAvatarIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y - 2].setIcon(cajaIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x][y - 1].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y - 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                }
                puntaje = Integer.parseInt(lblpuntajeMovimientos.getText());
                puntaje++;
                lblpuntajeMovimientos.setText(String.valueOf(puntaje));
                System.out.println("El puntaje arriba es: " + puntaje);

            } else if (e.VK_S == e.getKeyCode() && y < 19 && botones[x][y + 1].getIcon() != muroIcon) {
                if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                    botones[x][y + 1].setIcon(caminoIcon);
                    botones[x][y + 2].setIcon(cajaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y + 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() == llegadaIcon) {
                    botones[x][y + 1].setIcon(caminoIcon);
                    botones[x][y + 2].setIcon(estrellaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y + 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                    validarSiGano();
                } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                    botones[x][y + 1].setIcon(caminoIcon);
                    botones[x][y + 2].setIcon(cajaIcon);
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x][y + 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x][y + 1].getIcon() == cajaIcon) {

                } else if (botones[x][y + 1].getIcon() == llegadaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y + 1].setIcon(llegadaAvatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y + 1].getIcon() != cajaIcon && botones[x][y + 1].getIcon() != muroIcon && botones[x][y + 1].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x][y + 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x][y + 1].getIcon() == estrellaIcon && y <= 17 && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                    botones[x][y + 1].setIcon(llegadaAvatarIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y + 2].setIcon(cajaIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x][y + 1].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x][y + 1].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                }
                puntaje = Integer.parseInt(lblpuntajeMovimientos.getText());
                puntaje++;
                lblpuntajeMovimientos.setText(String.valueOf(puntaje));
                System.out.println("El puntaje abajo es: " + puntaje);

            } else if (KeyEvent.VK_A == e.getKeyCode() && x >= 1 && botones[x - 1][y].getIcon() != muroIcon) {
                if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x - 2][y].getIcon() != cajaIcon && botones[x - 2][y].getIcon() != estrellaIcon) {
                    botones[x - 1][y].setIcon(caminoIcon);
                    botones[x - 2][y].setIcon(cajaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x - 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() == llegadaIcon) {
                    botones[x - 1][y].setIcon(caminoIcon);
                    botones[x - 2][y].setIcon(estrellaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x - 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                    validarSiGano();
                } else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x - 2][y].getIcon() != cajaIcon && botones[x - 2][y].getIcon() != estrellaIcon) {
                    botones[x - 1][y].setIcon(caminoIcon);
                    botones[x - 2][y].setIcon(cajaIcon);
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x - 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x - 1][y].getIcon() == cajaIcon) {

                } else if (botones[x - 1][y].getIcon() == llegadaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x - 1][y].setIcon(llegadaAvatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x - 1][y].getIcon() != cajaIcon && botones[x - 1][y].getIcon() != muroIcon && botones[x - 1][y].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x - 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x - 1][y].getIcon() == estrellaIcon && x >= 2 && botones[x - 2][y].getIcon() != cajaIcon && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != estrellaIcon) {
                    botones[x - 1][y].setIcon(llegadaAvatarIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x - 2][y].setIcon(cajaIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x - 1][y].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x - 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                }
                puntaje = Integer.parseInt(lblpuntajeMovimientos.getText());
                puntaje++;
                lblpuntajeMovimientos.setText(String.valueOf(puntaje));
                System.out.println("El puntaje izquierda es: " + puntaje);
            } else if (e.VK_D == e.getKeyCode() && x < 19 && botones[x + 1][y].getIcon() != muroIcon) {
                if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x + 2][y].getIcon() != cajaIcon && botones[x + 2][y].getIcon() != estrellaIcon) {
                    botones[x + 1][y].setIcon(caminoIcon);
                    botones[x + 2][y].setIcon(cajaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x + 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() == llegadaIcon) {
                    botones[x + 1][y].setIcon(caminoIcon);
                    botones[x + 2][y].setIcon(estrellaIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x + 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                    validarSiGano();
                } else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x + 2][y].getIcon() != cajaIcon && botones[x + 2][y].getIcon() != estrellaIcon) {
                    botones[x + 1][y].setIcon(caminoIcon);
                    botones[x + 2][y].setIcon(cajaIcon);
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x + 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), x, y);
                } else if (botones[x + 1][y].getIcon() == cajaIcon) {

                } else if (botones[x + 1][y].getIcon() == llegadaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x + 1][y].setIcon(llegadaAvatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x + 1][y].getIcon() != cajaIcon && botones[x + 1][y].getIcon() != muroIcon && botones[x + 1][y].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(llegadaIcon);
                    botones[x + 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                } else if (botones[x + 1][y].getIcon() == estrellaIcon && x <= 17 && botones[x + 2][y].getIcon() != cajaIcon && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != estrellaIcon) {
                    botones[x + 1][y].setIcon(llegadaAvatarIcon);
                    botones[x][y].setIcon(caminoIcon);
                    botones[x + 2][y].setIcon(cajaIcon);
                    agregarPila(e.getKeyCode(), x, y);

                } else if (botones[x + 1][y].getIcon() != estrellaIcon) {
                    botones[x][y].setIcon(caminoIcon);
                    botones[x + 1][y].setIcon(avatarIcon);
                    agregarPila(e.getKeyCode(), -1, -1);
                }
                puntaje = Integer.parseInt(lblpuntajeMovimientos.getText());
                puntaje++;
                lblpuntajeMovimientos.setText(String.valueOf(puntaje));
                System.out.println("El puntaje derecha es: " + puntaje);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * metodo que sirve para asignarle a las variables x, y; la posicion en la
     * que se encuentra el avatar al momento de que este metodo se llama
     */
    public void posicionAvatar() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (botones[i][j].getIcon() == avatarIcon || botones[i][j].getIcon() == llegadaAvatarIcon) {
                    x = i;
                    y = j;
                    return;
                }
            }
        }
    }

    /**
     * metodo que nos sirve para deshacer los pasos del avatar lo que hacemos es
     * comparar los valores que hay en las pilas, en el caso de que pasoX y
     * pasoY sea -1 solamente se mueve el avatar, de lo contrario de acuerdo a
     * la posicion del avatar reorganizamos las cajas, para que se deshaga los
     * pasos de manera correcta
     */
    public void deshacerPaso() {
        try {
            int paso = pasos.empty() != true ? pasos.pop() : 0;
            int pasoX = pasosX.size() >= 0 ? pasosX.pop() : -1;
            int pasoY = pasosY.size() >= 0 ? pasosY.pop() : -1;
            pasosRehacer.push(paso);
            posicionAvatar();
            if (paso != 0) {
                if (paso == 68) {
                    if ((pasoX != -1) && (pasoY != -1)) {
                        posicionAvatar();
                        if (!ponerLlegada()) {
                            if (botones[x][y].getIcon() == llegadaAvatarIcon) {
                                botones[x + 1][y].setIcon(caminoIcon);
                                botones[x][y].setIcon(estrellaIcon);
                                botones[x - 1][y].setIcon(avatarIcon);
                                return;
                            } else if (botones[x - 1][y].getIcon() == llegadaIcon) {
                                botones[x + 1][y].setIcon(caminoIcon);
                                botones[x][y].setIcon(cajaIcon);
                                botones[x - 1][y].setIcon(llegadaAvatarIcon);
                                return;
                            }
                            botones[x + 1][y].setIcon(caminoIcon);
                        }
                        botones[x][y].setIcon(cajaIcon);
                        botones[x - 1][y].setIcon(avatarIcon);
                    } else {
                        robot.keyPress(KeyEvent.VK_A);
                    }
                    return;
                }

                if (paso == 65) {
                    if ((pasoX != -1) && (pasoY != -1)) {
                        posicionAvatar();
                        if (!ponerLlegada()) {
                            if (botones[x][y].getIcon() == llegadaAvatarIcon) {
                                botones[x - 1][y].setIcon(caminoIcon);
                                botones[x][y].setIcon(estrellaIcon);
                                botones[x + 1][y].setIcon(avatarIcon);
                                return;
                            } else if (botones[x + 1][y].getIcon() == llegadaIcon) {
                                botones[x - 1][y].setIcon(caminoIcon);
                                botones[x][y].setIcon(cajaIcon);
                                botones[x + 1][y].setIcon(llegadaAvatarIcon);
                                return;
                            }
                            botones[x - 1][y].setIcon(caminoIcon);
                        }
                        botones[x][y].setIcon(cajaIcon);
                        botones[x + 1][y].setIcon(avatarIcon);
                    } else {
                        robot.keyPress(KeyEvent.VK_D);
                    }

                    return;
                }

                if (paso == 83) {
                    if ((pasoX != -1) && (pasoY != -1)) {
                        posicionAvatar();
                        if (!ponerLlegada()) {
                            if (botones[x][y].getIcon() == llegadaAvatarIcon) {
                                botones[x][y + 1].setIcon(caminoIcon);
                                botones[x][y].setIcon(estrellaIcon);
                                botones[x][y - 1].setIcon(avatarIcon);
                                return;
                            } else if (botones[x][y - 1].getIcon() == llegadaIcon) {
                                botones[x][y + 1].setIcon(caminoIcon);
                                botones[x][y].setIcon(cajaIcon);
                                botones[x][y - 1].setIcon(llegadaAvatarIcon);
                                return;
                            }
                            botones[x][y + 1].setIcon(caminoIcon);
                        }
                        botones[x][y].setIcon(cajaIcon);
                        botones[x][y - 1].setIcon(avatarIcon);

                    } else {
                        robot.keyPress(KeyEvent.VK_W);
                    }
                    return;
                }

                if (paso == 87) {
                    if ((pasoX != -1) && (pasoY != -1)) {
                        posicionAvatar();
                        if (!ponerLlegada()) {
                            if (botones[x][y].getIcon() == llegadaAvatarIcon) {
                                botones[x][y - 1].setIcon(caminoIcon);
                                botones[x][y].setIcon(estrellaIcon);
                                botones[x][y + 1].setIcon(avatarIcon);
                                return;
                            } else if (botones[x][y + 1].getIcon() == llegadaIcon) {
                                botones[x][y - 1].setIcon(caminoIcon);
                                botones[x][y].setIcon(cajaIcon);
                                botones[x][y + 1].setIcon(llegadaAvatarIcon);
                                return;
                            }
                            botones[x][y - 1].setIcon(caminoIcon);
                        }
                        botones[x][y].setIcon(cajaIcon);
                        botones[x][y + 1].setIcon(avatarIcon);
                    } else {
                        robot.keyPress(KeyEvent.VK_S);
                    }
                }
            }
        } catch (EmptyStackException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * metodo que nos permite rehacer el paso del avatar. de acuerdo a los pasos
     * que se deshacen los guardamos en una pila y validamos la tecla que fue
     * rehecha para decirle a la clase robot que nos invoque el metodo para que
     * el avatar se mueva en la direccion correcta
     */
    public void rehacerPaso() {
        try {
            int paso = pasosRehacer.empty() != true ? pasosRehacer.pop() : 0;
            if (paso != 0) {
                if (paso == 68) {
                    robot.keyPress(KeyEvent.VK_D);
                }

                if (paso == 65) {
                    robot.keyPress(KeyEvent.VK_A);
                }

                if (paso == 83) {
                    robot.keyPress(KeyEvent.VK_S);
                }

                if (paso == 87) {
                    robot.keyPress(KeyEvent.VK_W);
                }
            }
        } catch (EmptyStackException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
    }

    /**
     * metodo que nos permite de acuerdo a los items que tiene una matriz de
     * imagenes de tipo char reemplazar la imagen de acuerdo al caracter leido
     */
    public void cambiarIconos() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (imagenes[i][j] == 'C') {
                    botones[i][j].setIcon(caminoIcon);
                } else if (imagenes[i][j] == 'M') {
                    botones[i][j].setIcon(muroIcon);
                } else if (imagenes[i][j] == 'B') {
                    botones[i][j].setIcon(cajaIcon);
                } else if (imagenes[i][j] == 'L') {
                    botones[i][j].setIcon(llegadaIcon);
                } else if (imagenes[i][j] == 'A') {
                    botones[i][j].setIcon(avatarIcon);
                }
            }
        }
    }

    /**
     * metodo que nos devuelve verdadero si el avatar puso en la caja en la
     * meta, llegando por debajo
     *
     * @return verdadero si el avatar puso en la caja en la meta, llegando por
     * debajo
     */
    public boolean menosEnY() {
        try {
            if (botones[x][y - 1].getIcon() == estrellaIcon) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println(a.fillInStackTrace());
        }
        return false;
    }

    /**
     * metodo que nos devuelve verdadero si el avatar puso en la caja en la
     * meta, llegando por arriba
     *
     * @return verdadero si el avatar puso en la caja en la meta, llegando por
     * arriba
     */
    public boolean masEnY() {
        try {
            if (botones[x][y + 1].getIcon() == estrellaIcon) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println(a.fillInStackTrace());
        }
        return false;
    }

    /**
     * metodo que nos devuelve verdadero si el avatar puso en la caja en la
     * meta, llegando por la izquierda
     *
     * @return verdadero si el avatar puso en la caja en la meta, llegando la
     * izquierda
     */
    public boolean masEnX() {
        try {
            if (botones[x + 1][y].getIcon() == estrellaIcon) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println(a.fillInStackTrace());
        }
        return false;
    }

    /**
     * metodo que nos devuelve verdadero si el avatar puso en la caja en la
     * meta, llegando por la derecha
     *
     * @return verdadero si el avatar puso en la caja en la meta, llegando la
     * derecha
     */
    public boolean menosEnX() {
        try {
            if (botones[x - 1][y].getIcon() == estrellaIcon) {
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            System.out.println(a.fillInStackTrace());
        }
        return false;
    }

    /**
     * metodo que invoca los metodos anteriores(menosEnX, menosEnY, masEnX,
     * masEnY) y de acuerdo al retorno de unos de estos nos deja el punto de
     * llegada vacio "sin la caja", siempre y cuando sea un paso que se deshaga
     *
     * @return verdadero si pone el icono de llegada
     */
    public boolean ponerLlegada() {
        if (menosEnY()) {
            botones[x][y - 1].setIcon(llegadaIcon);
            return true;
        } else if (masEnY()) {
            botones[x][y + 1].setIcon(llegadaIcon);
            return true;
        } else if (menosEnX()) {
            botones[x - 1][y].setIcon(llegadaIcon);
            return true;
        } else if (masEnX()) {
            botones[x + 1][y].setIcon(llegadaIcon);
            return true;
        }
        return false;
    }

    /**
     * metodo que nos valida si el jugador ya puso todas las cajas en los puntos
     * de llegada
     */
    public void validarSiGano() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (botones[i][j].getIcon() == llegadaIcon || botones[i][j].getIcon() == llegadaAvatarIcon) {
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "¡Felicitaciones!\nEste estuvo muy fácil, prueba con otro más dificil :)", "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("sddsfhfdshfdshfh" + jugadores.get(utilidades.retornarPosicion(jugadores, nombreJugador)).getNombreJugador());
        jugadores.get(utilidades.retornarPosicion(jugadores, nombreJugador)).setJugadas(Integer.parseInt(lblpuntajeMovimientos.getText()));
        jugadores.get(utilidades.retornarPosicion(jugadores, nombreJugador)).setTablero(lblTablero.getText().concat(".txt").toLowerCase());
        utilidades.guardarJugador(jugadores);
        lblpuntajeMovimientos.setText("-1");
        //cambiarIconos();
        ElegirNivelFrame elegir = new ElegirNivelFrame(nombreJugador, login);
        elegir.setVisible(true);
        boardNivel.setVisible(false);

    }

    /**
     * metodo que nos permite identificar si una moneda se encuentra en una
     * pared y que todo la hilera de esa pared no haya un lugar donde se pueda
     * depositar la moneda, para decirnos que en ese momento la partida no tiene
     * solucion
     *
     * @return verdadero si la partida tiene aun solucion false si es lo
     * contrario
     */
    public boolean buscarMuro() {

        if ((botones[x][y - 2 < 0 ? 0 : y - 2].getIcon() == muroIcon && botones[x][y - 1].getIcon() == cajaIcon)) {
            if (buscarLlegadaIcon(y - 1)) {
                return true;
            } else if (buscarEspacio()) {
                return true;
            } else {
                return false;
            }
        }

        if ((botones[x][y + 2 > 19 ? 19 : y + 2].getIcon() == muroIcon && botones[x][y + 1 == -1 ? 19 : y + 1].getIcon() == cajaIcon)) {
            if (buscarLlegadaIcon(y + 1)) {
                return true;
            } else if (buscarEspacio()) {
                return true;
            } else {
                return false;
            }
        }

        if ((botones[x - 2 < 0 ? 0 : x - 2][y].getIcon() == muroIcon && botones[x - 1][y].getIcon() == cajaIcon)) {
            if (buscarLlegadaIconX(x - 1)) {
                return true;
            } else if (buscarEspacio()) {
                return true;
            } else {
                return false;
            }
        }

        if ((botones[x + 2 > 19 ? 19 : x + 2][y].getIcon() == muroIcon && botones[x + 1][y].getIcon() == cajaIcon)) {
            if (buscarLlegadaIconX(x + 1)) {
                return true;
            } else if (buscarEspacio()) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * metodo que nos permite saber si entre un muro y la moneda hay espacios
     * este es uno de los items que se evaluan para saber si la partida en el
     * momento tiene solucion
     *
     * @return verdadero si entre la moneda y el muro hay espacios
     */
    public boolean buscarEspacio() {
        if ((botones[x - 1][y].getIcon() == caminoIcon) && (botones[x - 1][y - 1].getIcon() == caminoIcon)) {
            return true;
        } else if ((botones[x - 1][y].getIcon() == caminoIcon) && (botones[x - 1][y + 1].getIcon() == caminoIcon)) {
            return true;
        } else if ((botones[x][y - 1].getIcon() == caminoIcon) && (botones[x - 1][y - 1].getIcon() == caminoIcon)) {
            return true;
        }

        return false;
    }

    /**
     * metodo que nos permite evaluar otro item para saber si la partida que se
     * esta jugando tiene solucion horizontalmente, lo que nos permite saber es
     * que si debajo de un muro esta la moneda y hay un espacio de llegada.
     *
     * @param y, la posicion en y que se encuentra el mario
     * @return verdadero si hay un espacio de llegada de la moneda y falso si es
     * lo contrario
     */
    public boolean buscarLlegadaIcon(int y) {
        for (int i = 0; i < botones.length; i++) {
            if (botones[i][y].getIcon() == llegadaIcon) {
                return true;
            }
        }
        return false;
    }

    /**
     * metodo que nos permite evaluar otro item para saber si la partida que se
     * esta jugando tiene solucion verticalmente, lo que nos permite saber es
     * que si debajo de un muro esta la moneda y hay un espacio de llegada.
     *
     * @param y, la posicion en y que se encuentra el mario
     * @return verdadero si hay un espacio de llegada de la moneda y falso si es
     * lo contrario
     */
    public boolean buscarLlegadaIconX(int y) {
        for (int i = 0; i < botones.length; i++) {
            if (botones[y][i].getIcon() == llegadaIcon) {
                return true;
            }
        }
        return false;
    }

    /**
     * metodo sobre escrito de la interfaz thread para llamar al metodo que nos
     * permite saber si la partida cada que se haga un movimiento tiene solucion
     */
    @Override
    public void run() {
        if (!buscarMuro()) {
            JOptionPane.showMessageDialog(null, "En este momento la partida no tiene solucion use la opcion de deshacer");
        }
    }

}
