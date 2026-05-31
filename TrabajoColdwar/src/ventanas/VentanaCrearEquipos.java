package ventanas;

import javax.swing.*;
import javax.swing.SwingConstants;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import main.TrabajoColdwar;
import planeta.Planeta;
import trabajoColdWar_Utils.Validaciones;

public class VentanaCrearEquipos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JFrame ventanaPadre;

    private JButton btnAnadir, btnJugar, btnInfo;
    private JTextField[] cajasNombres;
    private JTextField[] cajasIds;
    private JComboBox<String>[] combosTipos;
    private JLabel[] etiqEquipos;
    private JLabel[] miniFotos;

    private String[] nombresPersonajes = {
        "Nobita", "Agallas", "Homer", "Peter Griffin", "Finn el Humano", "Fanboy & Chum Chum"
    };
    private String[] archPersonajes = {
        "nobita.png", "agallas.png", "homer.png", "peter.png", "finn.png", "fanboychumchum.png"
    };

    @SuppressWarnings("unchecked")
    public VentanaCrearEquipos(JFrame padre) {
        this.ventanaPadre = padre;

        setLayout(null);
        setBounds(0, 0, 900, 600);
        setOpaque(false);

        // ── Título ──────────────────────────────────────────────────────────
        JLabel labelTitulo = new JLabel(escalar("recurso/crearEquipos.png", 400, 80));
        labelTitulo.setBounds(250, 5, 400, 80);
        add(labelTitulo);

        // ── Cabeceras ────────────────────────────────────────────────────────
        JLabel lblNombre = new JLabel(escalar("recurso/nombreEquipo.png", 180, 30));
        lblNombre.setBounds(230, 90, 180, 30);
        add(lblNombre);

        JLabel lblPersonaje = new JLabel(escalar("recurso/personajes.png", 160, 30));
        lblPersonaje.setBounds(435, 90, 160, 30);
        add(lblPersonaje);

        // Cabecera ID
        JLabel lblId = new JLabel("ID (4núm+3let)");
        lblId.setForeground(new Color(0, 200, 80));
        lblId.setFont(new Font("Arial", Font.BOLD, 12));
        lblId.setBounds(620, 90, 130, 30);
        add(lblId);

        // ── Filas de equipos ─────────────────────────────────────────────────
        cajasNombres = new JTextField[6];
        cajasIds     = new JTextField[6];
        combosTipos  = new JComboBox[6];
        etiqEquipos  = new JLabel[6];
        miniFotos    = new JLabel[6];

        Color colorFondo = new Color(20, 20, 20);
        Color colorTexto = Color.WHITE;
        Color colorBorde = new Color(0, 200, 80);
        Font  fuenteInput = new Font("Arial", Font.PLAIN, 13);

        int yBase = 128;
        int paso  = 56;

        for (int i = 0; i < 6; i++) {
            int y = yBase + i * paso;

            // Etiqueta equipo
            etiqEquipos[i] = new JLabel(escalar("recurso/equipo" + (i + 1) + ".png", 120, 30));
            etiqEquipos[i].setBounds(95, y, 120, 30);
            add(etiqEquipos[i]);

            // Caja nombre
            cajasNombres[i] = new JTextField();
            cajasNombres[i].setFont(fuenteInput);
            cajasNombres[i].setBackground(colorFondo);
            cajasNombres[i].setForeground(colorTexto);
            cajasNombres[i].setCaretColor(colorTexto);
            cajasNombres[i].setBorder(BorderFactory.createLineBorder(colorBorde, 2));
            cajasNombres[i].setBounds(225, y, 190, 30);
            add(cajasNombres[i]);

            // ComboBox personaje
            combosTipos[i] = new JComboBox<>(nombresPersonajes);
            combosTipos[i].setFont(fuenteInput);
            combosTipos[i].setBackground(colorFondo);
            combosTipos[i].setForeground(colorTexto);
            combosTipos[i].setBounds(430, y, 155, 30);
            add(combosTipos[i]);

            // Mini foto personaje
            miniFotos[i] = new JLabel();
            miniFotos[i].setBounds(594, y - 2, 36, 36);
            actualizarMiniFoto(miniFotos[i], 0);
            add(miniFotos[i]);

            final int idx = i;
            combosTipos[i].addActionListener(e ->
                actualizarMiniFoto(miniFotos[idx], combosTipos[idx].getSelectedIndex())
            );

            // Caja ID
            cajasIds[i] = new JTextField();
            cajasIds[i].setFont(fuenteInput);
            cajasIds[i].setBackground(colorFondo);
            cajasIds[i].setForeground(colorTexto);
            cajasIds[i].setCaretColor(colorTexto);
            cajasIds[i].setBorder(BorderFactory.createLineBorder(colorBorde, 2));
            cajasIds[i].setBounds(638, y, 120, 30);
            add(cajasIds[i]);

            // Equipos 4-6 ocultos al inicio
            if (i >= 3) {
                etiqEquipos[i].setVisible(false);
                cajasNombres[i].setVisible(false);
                combosTipos[i].setVisible(false);
                miniFotos[i].setVisible(false);
                cajasIds[i].setVisible(false);
            }
        }

        // ── Botones ──────────────────────────────────────────────────────────
        btnAnadir = boton("recurso/añadirEquipo.png", 150, 490, 160, 45);
        btnJugar  = boton("recurso/jugar.png",        330, 490, 160, 45);
        btnInfo   = boton("recurso/Infopersonajes.png", 510, 490, 160, 45);

        add(btnAnadir);
        add(btnJugar);
        add(btnInfo);

        // ── Acción: añadir equipo ─────────────────────────────────────────────
        btnAnadir.addActionListener(e -> {
            for (int i = 3; i < 6; i++) {
                if (!etiqEquipos[i].isVisible()) {
                    etiqEquipos[i].setVisible(true);
                    cajasNombres[i].setVisible(true);
                    combosTipos[i].setVisible(true);
                    miniFotos[i].setVisible(true);
                    cajasIds[i].setVisible(true);
                    if (i == 5) btnAnadir.setVisible(false);
                    break;
                }
            }
        });

        // ── Acción: info personajes ───────────────────────────────────────────
        btnInfo.addActionListener(e ->
            new DialogoInfoPersonajes(ventanaPadre).setVisible(true)
        );

        // ── Acción: JUGAR ─────────────────────────────────────────────────────
        btnJugar.addActionListener(e -> iniciarPartida());

        // ── Botón volver ──────────────────────────────────────────────────────
        JButton btnVolver = boton("recurso/flechaizq.png", 20, 510, 60, 50);
        btnVolver.addActionListener(e -> {
            if (ventanaPadre instanceof mainFrame) {
                ((mainFrame) ventanaPadre).mostrarMenuPrincipal();
            }
        });
        add(btnVolver);
    }

    // ── Lógica de inicio de partida ───────────────────────────────────────────

    private void iniciarPartida() {
        ArrayList<Planeta> planetas = new ArrayList<>();
        ArrayList<String> nombresUsados = new ArrayList<>();
        ArrayList<String> idsUsados = new ArrayList<>();

        // Determinar cuántos equipos están visibles
        int numEquipos = 0;
        for (int i = 0; i < 6; i++) {
            if (etiqEquipos[i].isVisible()) numEquipos++;
        }

        if (numEquipos < 3) {
            JOptionPane.showMessageDialog(ventanaPadre,
                "Necesitas al menos 3 equipos para jugar.",
                "Equipos insuficientes", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar y construir planetas
        for (int i = 0; i < numEquipos; i++) {
            String nombre = cajasNombres[i].getText().trim();
            String id     = cajasIds[i].getText().trim().toUpperCase();
            int    tipo   = combosTipos[i].getSelectedIndex();

            // Validar nombre
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(ventanaPadre,
                    "El equipo " + (i + 1) + " no tiene nombre.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (nombresUsados.contains(nombre)) {
                JOptionPane.showMessageDialog(ventanaPadre,
                    "El nombre \"" + nombre + "\" está repetido.",
                    "Nombre duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar ID
            if (!Validaciones.validarIdentificador(id)) {
                JOptionPane.showMessageDialog(ventanaPadre,
                    "El ID del equipo " + (i + 1) + " debe tener formato 4 números + 3 letras mayúsculas (ej: 1234ABC).",
                    "ID inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (idsUsados.contains(id)) {
                JOptionPane.showMessageDialog(ventanaPadre,
                    "El ID \"" + id + "\" está repetido.",
                    "ID duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            nombresUsados.add(nombre);
            idsUsados.add(id);

            Planeta nuevo = TrabajoColdwar.crearPlanetaPorIndice(nombre, tipo);
            nuevo.setIdentificador(id);
            planetas.add(nuevo);
        }

        // Lanzar ventana de turno
        if (ventanaPadre instanceof mainFrame) {
            ((mainFrame) ventanaPadre).mostrarTurno(planetas, false);
        }
    }

    // ── Paint fondo ───────────────────────────────────────────────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon ic = new ImageIcon("recurso/fondo.png");
        g.drawImage(ic.getImage(), 0, 0, 900, 600, this);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private ImageIcon escalar(String ruta, int w, int h) {
        Image img = new ImageIcon(ruta).getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private JButton boton(String ruta, int x, int y, int w, int h) {
        ImageIcon icono = new ImageIcon(ruta);
        Image imgOriginal = icono.getImage();
        ImageIcon iconoFinal = (imgOriginal.getWidth(null) > 0)
            ? new ImageIcon(imgOriginal.getScaledInstance(w, h, Image.SCALE_SMOOTH))
            : icono;

        JButton btn = new JButton(iconoFinal);
        btn.setBounds(x, y, w, h);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        return btn;
    }

    private void actualizarMiniFoto(JLabel label, int indice) {
        Image img = new ImageIcon("recurso/" + archPersonajes[indice])
                        .getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    // ── Diálogo info personajes ───────────────────────────────────────────────

    class DialogoInfoPersonajes extends JDialog {
        private static final long serialVersionUID = 1L;
        private int indiceActual = 0;
        private JLabel fotoGrande;
        private LabelTextoBordeado textoInfo;

        private String[] detalles = {
            "NOBITA\n\nPlaneta: Enano\nVida: 100 ♥\nPasiva: Esquiva 50%\nTorpe pero escurridizo.",
            "AGALLAS\n\nPlaneta: Azul\nDaño: x2 Rojo, /2 Verde\nCobarde pero con ventaja.",
            "HOMER\n\nPlaneta: Gaseoso\nVida: 400 ♥\nMisiles crecen por ronda.",
            "PETER GRIFFIN\n\nPlaneta: Rojo\nDaño: x2 Verde, /2 Azul\nAgresivo y bruto.",
            "FINN EL HUMANO\n\nPlaneta: Verde\nDaño: x2 Azul, /2 Rojo\nHéroe aventurero.",
            "FANBOY & CHUM CHUM\n\nPlaneta: Normal\nVida: 200 ♥ | Energía: 50 ⚡\nEquilibrados."
        };

        public DialogoInfoPersonajes(JFrame parent) {
            super(parent, "Info Personajes", true);
            setSize(600, 400);
            setLocationRelativeTo(parent);
            setLayout(null);
            setResizable(false);

            JLabel fondo = new JLabel(escalar("recurso/fondo.png", 600, 400));
            fondo.setBounds(0, 0, 600, 400);
            fondo.setLayout(null);
            setContentPane(fondo);

            fotoGrande = new JLabel();
            fotoGrande.setBounds(50, 50, 200, 200);
            fondo.add(fotoGrande);

            textoInfo = new LabelTextoBordeado();
            textoInfo.setBounds(280, 50, 280, 250);
            fondo.add(textoInfo);

            JButton btnIzq = boton("recurso/flechaizq.png", 70, 270, 70, 50);
            btnIzq.addActionListener(e -> { indiceActual = (indiceActual - 1 + 6) % 6; actualizarVista(); });
            fondo.add(btnIzq);

            JButton btnDer = boton("recurso/flechader.png", 160, 270, 70, 50);
            btnDer.addActionListener(e -> { indiceActual = (indiceActual + 1) % 6; actualizarVista(); });
            fondo.add(btnDer);

            actualizarVista();
        }

        private void actualizarVista() {
            Image img = new ImageIcon("recurso/" + archPersonajes[indiceActual])
                            .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoGrande.setIcon(new ImageIcon(img));
            textoInfo.setText(detalles[indiceActual]);
            textoInfo.repaint();
        }
    }

    class LabelTextoBordeado extends JTextArea {
        private static final long serialVersionUID = 1L;

        public LabelTextoBordeado() {
            setOpaque(false);
            setEditable(false);
            setFocusable(false);
            setFont(new Font("Arial", Font.BOLD, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            String[] lineas = getText().split("\n");
            FontMetrics fm = g2.getFontMetrics();
            int y = fm.getAscent() + 10;
            int grosor = 2;

            for (String linea : lineas) {
                g2.setColor(Color.BLACK);
                for (int i = -grosor; i <= grosor; i++)
                    for (int j = -grosor; j <= grosor; j++)
                        if (i != 0 || j != 0)
                            g2.drawString(linea, 10 + i, y + j);
                g2.setColor(Color.WHITE);
                g2.drawString(linea, 10, y);
                y += fm.getHeight();
            }
        }
    }
}
