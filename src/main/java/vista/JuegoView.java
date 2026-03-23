package vista;

package batalla_naval.Vista;

import batalla_naval.modelo.*;

import javax.swing.*;
import java.awt.*;

public class JuegoView extends JFrame {

    private Tablero jugador;
    private Tablero bot;

    private JPanel panelJugador;
    private JPanel panelBot;

    private JButton[][] botonesBot;

    private JLabel lblTurno;
    private JTextArea historial;

    public interface DisparoListener {
        void onDisparo(int fila, int col);
    }

    private DisparoListener listener;

    public JuegoView(Tablero jugador, Tablero bot) {
        this.jugador = jugador;
        this.bot = bot;

        setTitle("Batalla Naval");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        construirUI();
    }

    private void construirUI() {

        setLayout(new BorderLayout());

        // ─── TURNO ───
        lblTurno = new JLabel("Turno", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Segoe UI", Font.BOLD, 16));
        add(lblTurno, BorderLayout.NORTH);

        // ─── PANEL CENTRAL ───
        JPanel panelCentral = new JPanel(new GridLayout(1,2));

        // ─── JUGADOR ───
        JPanel contJugador = new JPanel(new BorderLayout());

        JLabel tituloJugador = new JLabel("Tus barcos", SwingConstants.CENTER);
        tituloJugador.setOpaque(true);
        tituloJugador.setBackground(Color.LIGHT_GRAY);

        panelJugador = new JPanel(new GridLayout(10,10));
        panelJugador.setBackground(new Color(150,200,200));

        contJugador.add(tituloJugador, BorderLayout.NORTH);
        contJugador.add(panelJugador, BorderLayout.CENTER);

        // ─── BOT ───
        JPanel contBot = new JPanel(new BorderLayout());

        JLabel tituloBot = new JLabel("¡Ataca a tu oponente!", SwingConstants.CENTER);
        tituloBot.setOpaque(true);
        tituloBot.setBackground(new Color(200,20,60));
        tituloBot.setForeground(Color.WHITE);

        panelBot = new JPanel(new GridLayout(10,10));
        panelBot.setBackground(new Color(150,200,200));

        botonesBot = new JButton[10][10];

        for(int f=0; f<10; f++){
            for(int c=0; c<10; c++){

                int fila = f;
                int col  = c;

                JButton btn = new JButton("•");
                btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
                btn.setFocusPainted(false);

                btn.addActionListener(e -> {
                    if(listener != null){
                        listener.onDisparo(fila, col);
                    }
                });

                botonesBot[f][c] = btn;
                panelBot.add(btn);
            }
        }

        contBot.add(tituloBot, BorderLayout.NORTH);
        contBot.add(panelBot, BorderLayout.CENTER);

        panelCentral.add(contJugador);
        panelCentral.add(contBot);

        add(panelCentral, BorderLayout.CENTER);

        // ─── HISTORIAL ───
        historial = new JTextArea(6,20);
        historial.setEditable(false);
        historial.setLineWrap(true);
        historial.setWrapStyleWord(true);

        // 🔥 Tipografía buena
        historial.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        // 🔥 Estética mejorada (opcional)
        historial.setBackground(new Color(20,20,20));
        historial.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(historial);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial"));

        add(scroll, BorderLayout.SOUTH);
    }

    // ─── ACTUALIZAR TABLEROS ───

    public void actualizarTableros() {

        panelJugador.removeAll();

        Casilla[][] casillasJugador = jugador.getCasillas();
        Casilla[][] casillasBot = bot.getCasillas();

        // ─── JUGADOR ───
        for(int f=0; f<10; f++){
            for(int c=0; c<10; c++){

                JPanel celda = new JPanel();

                Estado est = casillasJugador[f][c].getEstado();

                if(est == Estado.BARCO){
                    celda.setBackground(new Color(100,100,100));
                }
                else if(est == Estado.GOLPE_BARCO){
                    celda.setBackground(Color.RED);
                }
                else if(est == Estado.GOLPE_AGUA){
                    celda.setBackground(Color.CYAN);
                }
                else {
                    celda.setBackground(new Color(150,200,200));
                }

                panelJugador.add(celda);
            }
        }

        // ─── BOT ───
        for(int f=0; f<10; f++){
            for(int c=0; c<10; c++){

                Estado est = casillasBot[f][c].getEstado();

                if(est == Estado.GOLPE_BARCO){
                    botonesBot[f][c].setText("💥");
                }
                else if(est == Estado.GOLPE_AGUA){
                    botonesBot[f][c].setText("💧");
                }
            }
        }

        panelJugador.revalidate();
        panelJugador.repaint();
    }

    // ─── MÉTODOS CONTROLADOR ───

    public void setDisparoListener(DisparoListener l){
        this.listener = l;
    }

    public void agregarHistorial(String s){
        historial.append(s + "\n");
        historial.setCaretPosition(historial.getDocument().getLength());
    }

    public void actualizarTurno(boolean t){
        lblTurno.setText(t ? "Tu turno" : "Turno del bot");
    }

    public void iniciarTemporizador(){}
    public void detenerTemporizador(){}

    public void mostrarGanador(String g){
        JOptionPane.showMessageDialog(this, "Ganador: " + g);
    }

    public void actualizarContadores(int j, int b){}
}
