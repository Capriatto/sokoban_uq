
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
 * @author FabianGM
 */
public class Board implements KeyListener {

    private JButton[][] botones;
    private char[][] imagenes;
    private ImageIcon avatarIcon, cajaIcon, caminoIcon, muroIcon, llegadaIcon, estrellaIcon, llegadaAvatarIcon;
    private int a, b, x, y;
    private Stack<Integer> pasos = new Stack<>();
    private Stack<Integer> pasosRehacer = new Stack<>();
    Robot robot;
    public boolean agregar = true;
    private ArchivoLeer leer;
    private String nombreArchivo;
    private int puntaje = 0;
    private JLabel puntajeMovimientos;
    private int modificarPuntaje;

    /**
     * *
     * Obtener puntaje jugador
     *
     * @return
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * *
     * Modificar puntaje jugador
     *
     * @param puntaje
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public boolean isAgregar() {
        return agregar;
    }

    public void setAgregar(boolean agregar) {
        this.agregar = agregar;
    }

    public Board(String nombreArchivo, JLabel puntajeJugador) {
        this.nombreArchivo = nombreArchivo;
        puntajeMovimientos = puntajeJugador;
        leer = new ArchivoLeer();
        this.botones = new JButton[20][20];
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
    public void matrizDeBotonesBloqueado(JPanel panel) {
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
        imagenes = leer.leerArchivo(nombreArchivo);
        cambiarIconos();
    }

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

    @Override
    public void keyPressed(KeyEvent e) {

        if (agregar) {
            System.out.println(e.getKeyCode());
            pasos.push(e.getKeyCode());
        } else {
            agregar = true;
        }

        posicionAvatar();

        if (e.VK_W == e.getKeyCode() && y >= 1 && botones[x][y - 1].getIcon() != muroIcon) {
            if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() == llegadaIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x][y - 1].getIcon() == cajaIcon && y >= 2 && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                botones[x][y - 1].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == cajaIcon) {

            } else if (botones[x][y - 1].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y - 1].getIcon() != cajaIcon && botones[x][y - 1].getIcon() != muroIcon && botones[x][y - 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            } else if (botones[x][y - 1].getIcon() == estrellaIcon && y >= 2 && botones[x][y - 2].getIcon() != cajaIcon && botones[x][y - 2].getIcon() != muroIcon && botones[x][y - 2].getIcon() != estrellaIcon) {
                botones[x][y - 1].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 2].setIcon(cajaIcon);
            } else if (botones[x][y - 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y - 1].setIcon(avatarIcon);
            }
            puntaje++;
            puntajeMovimientos.setText(String.valueOf(puntaje));
            System.out.println("El puntaje arriba es: " + puntaje);

        } else if (e.VK_S == e.getKeyCode() && y < 19 && botones[x][y + 1].getIcon() != muroIcon) {
            if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() == llegadaIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x][y + 1].getIcon() == cajaIcon && y <= 17 && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                botones[x][y + 1].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == cajaIcon) {

            } else if (botones[x][y + 1].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x][y + 1].getIcon() != cajaIcon && botones[x][y + 1].getIcon() != muroIcon && botones[x][y + 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            } else if (botones[x][y + 1].getIcon() == estrellaIcon && y <= 17 && botones[x][y + 2].getIcon() != cajaIcon && botones[x][y + 2].getIcon() != muroIcon && botones[x][y + 2].getIcon() != estrellaIcon) {
                botones[x][y + 1].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 2].setIcon(cajaIcon);
            } else if (botones[x][y + 1].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x][y + 1].setIcon(avatarIcon);
            }
            puntaje++;
            puntajeMovimientos.setText(String.valueOf(puntaje));
            System.out.println("El puntaje abajo es: " + puntaje);

        } else if (KeyEvent.VK_A == e.getKeyCode() && x >= 1 && botones[x - 1][y].getIcon() != muroIcon) {
            if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x - 2][y].getIcon() != cajaIcon && botones[x - 2][y].getIcon() != estrellaIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            } else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() == llegadaIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x - 1][y].getIcon() == cajaIcon && x >= 2 && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x - 2][y].getIcon() != cajaIcon && botones[x - 2][y].getIcon() != estrellaIcon) {
                botones[x - 1][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            } else if (botones[x - 1][y].getIcon() == cajaIcon) {

            } else if (botones[x - 1][y].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x - 1][y].getIcon() != cajaIcon && botones[x - 1][y].getIcon() != muroIcon && botones[x - 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            } else if (botones[x - 1][y].getIcon() == estrellaIcon && x >= 2 && botones[x - 2][y].getIcon() != cajaIcon && botones[x - 2][y].getIcon() != muroIcon && botones[x - 2][y].getIcon() != estrellaIcon) {
                botones[x - 1][y].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x - 2][y].setIcon(cajaIcon);
            } else if (botones[x - 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x - 1][y].setIcon(avatarIcon);
            }
            puntaje++;
            puntajeMovimientos.setText(String.valueOf(puntaje));
            System.out.println("El puntaje izquierda es: " + puntaje);
        } else if (e.VK_D == e.getKeyCode() && x < 19 && botones[x + 1][y].getIcon() != muroIcon) {
            if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() != llegadaAvatarIcon && botones[x + 2][y].getIcon() != cajaIcon && botones[x + 2][y].getIcon() != estrellaIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() == llegadaIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(estrellaIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
                validarSiGano();
            } else if (botones[x + 1][y].getIcon() == cajaIcon && x <= 17 && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != llegadaIcon && botones[x][y].getIcon() == llegadaAvatarIcon && botones[x + 2][y].getIcon() != cajaIcon && botones[x + 2][y].getIcon() != estrellaIcon) {
                botones[x + 1][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(cajaIcon);
                botones[x][y].setIcon(llegadaIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if (botones[x + 1][y].getIcon() == cajaIcon) {

            } else if (botones[x + 1][y].getIcon() == llegadaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(llegadaAvatarIcon);
            } else if (botones[x][y].getIcon() == llegadaAvatarIcon && botones[x + 1][y].getIcon() != cajaIcon && botones[x + 1][y].getIcon() != muroIcon && botones[x + 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(llegadaIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            } else if (botones[x + 1][y].getIcon() == estrellaIcon && x <= 17 && botones[x + 2][y].getIcon() != cajaIcon && botones[x + 2][y].getIcon() != muroIcon && botones[x + 2][y].getIcon() != estrellaIcon) {
                botones[x + 1][y].setIcon(llegadaAvatarIcon);
                botones[x][y].setIcon(caminoIcon);
                botones[x + 2][y].setIcon(cajaIcon);

            } else if (botones[x + 1][y].getIcon() != estrellaIcon) {
                botones[x][y].setIcon(caminoIcon);
                botones[x + 1][y].setIcon(avatarIcon);
            }
            puntaje++;
            puntajeMovimientos.setText(String.valueOf(puntaje));
            System.out.println("El puntaje derecha es: " + puntaje);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

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

    public void deshacerPaso() {
        try {
            int paso = pasos.pop();
            pasosRehacer.push(paso);
            while (pasos.size() >= 0) {

                if (paso == 68) {
                    robot.keyPress(KeyEvent.VK_A);
                    return;
                }

                if (paso == 65) {
                    robot.keyPress(KeyEvent.VK_D);
                    return;
                }

                if (paso == 83) {
                    robot.keyPress(KeyEvent.VK_W);
                    return;
                }

                if (paso == 87) {
                    robot.keyPress(KeyEvent.VK_S);
                    return;
                }
            }
        } catch (EmptyStackException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
    }

    public void rehacerPaso() {
        try {
            int paso = pasosRehacer.pop();
            pasos.push(paso);
            while (!pasos.empty()) {
                if (paso == 68) {
                    robot.keyPress(KeyEvent.VK_D);
                    return;
                }

                if (paso == 65) {
                    robot.keyPress(KeyEvent.VK_A);
                    return;
                }

                if (paso == 83) {
                    robot.keyPress(KeyEvent.VK_S);
                    return;
                }

                if (paso == 87) {
                    robot.keyPress(KeyEvent.VK_W);
                    return;
                }
            }
        } catch (EmptyStackException e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
        }
    }

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

    public void validarSiGano() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (botones[i][j].getIcon() == llegadaIcon || botones[i][j].getIcon() == llegadaAvatarIcon) {
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "¡Felicitaciones!\nEste estuvo muy fácil, prueba con otro más dificil :)", "Juego Terminado", JOptionPane.INFORMATION_MESSAGE);
        cambiarIconos();
    }

}
