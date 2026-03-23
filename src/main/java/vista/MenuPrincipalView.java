package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPrincipalView extends JFrame {

    private JButton btnIniciar;
    private JButton btnSalir;

    public MenuPrincipalView() {
        setTitle("Batalla Naval — Menú");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(10, 30, 60));

        JLabel titulo = new JLabel("BATALLA NAVAL", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnIniciar = new JButton("Iniciar partida");
        btnSalir   = new JButton("Salir");

        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(titulo);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnIniciar);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnSalir);
        panel.add(Box.createVerticalGlue());

        setContentPane(panel);
    }

    public void addBotonIniciarListener(ActionListener l) {
        btnIniciar.addActionListener(l);
    }

    public void addBotonSalirListener(ActionListener l) {
        btnSalir.addActionListener(l);
    }

    public void mostrarMenu() {
        setVisible(true);
    }

    public void ocultarMenu() {
        setVisible(false);
    }
}
