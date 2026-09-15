package br.com.pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaInicial extends JFrame {

    private final Pokedex pokedex;

    private JTextField campoNome;
    private JComboBox<String> seletorCenario;
    private JLabel descricaoCenario;

    public TelaInicial() {

        pokedex = new Pokedex();

        pokedex.registrar(
                new PokemonFogo("Ignivulp", 100, 20)
        );

        pokedex.registrar(
                new PokemonAgua("Aquafin", 100, 18)
        );

        pokedex.registrar(
                new PokemonPlanta("Florabbit", 100, 19)
        );

        criarInterface();
    }

    private void criarInterface() {

        setTitle("Pokemon Battle");
        setSize(920, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fundo = new PainelFundo();
        fundo.setLayout(new BorderLayout());
        fundo.setBorder(new EmptyBorder(26, 40, 24, 40));

        fundo.add(criarCabecalho(), BorderLayout.NORTH);
        fundo.add(criarCentro(), BorderLayout.CENTER);
        fundo.add(criarRodape(), BorderLayout.SOUTH);

        setContentPane(fundo);
    }

    private JPanel criarCabecalho() {

        JPanel cabecalho = new JPanel();
        cabecalho.setOpaque(false);
        cabecalho.setLayout(new BoxLayout(
                cabecalho,
                BoxLayout.Y_AXIS
        ));

        JLabel titulo = new JLabel("POKEMON BATTLE");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 38));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel(
                "Escolha seu treinador e prepare sua aventura"
        );

        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(201, 211, 245));

        cabecalho.add(titulo);
        cabecalho.add(Box.createVerticalStrut(8));
        cabecalho.add(subtitulo);

        return cabecalho;
    }

    private JPanel criarCentro() {

        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(
                centro,
                BoxLayout.Y_AXIS
        ));

        JPanel cards = new JPanel(new FlowLayout(
                FlowLayout.CENTER,
                22,
                8
        ));

        cards.setOpaque(false);

        cards.add(criarCardPokemon(
                "Ignivulp",
                "TIPO FOGO"
        ));

        cards.add(criarCardPokemon(
                "Aquafin",
                "TIPO AGUA"
        ));

        cards.add(criarCardPokemon(
                "Florabbit",
                "TIPO PLANTA"
        ));

        JLabel rotuloNome = criarRotulo(
                "NOME DO TREINADOR"
        );

        campoNome = new JTextField("Treinador");

        campoNome.setMaximumSize(new Dimension(310, 40));
        campoNome.setPreferredSize(new Dimension(310, 40));
        campoNome.setHorizontalAlignment(SwingConstants.CENTER);
        campoNome.setFont(new Font("Arial", Font.BOLD, 16));
        campoNome.setForeground(new Color(28, 41, 70));
        campoNome.setBackground(new Color(244, 247, 255));

        campoNome.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(137, 111, 225),
                        2
                ),
                new EmptyBorder(8, 12, 8, 12)
        ));

        JLabel rotuloCenario = criarRotulo(
                "ESCOLHA O CENARIO DA BATALHA"
        );

        seletorCenario = new JComboBox<>(
                new String[]{
                        "Santuário Vulcânico",
                        "Lagoa de Cristal",
                        "Floresta Encantada"
                }
        );

        seletorCenario.setMaximumSize(new Dimension(310, 40));
        seletorCenario.setPreferredSize(new Dimension(310, 40));
        seletorCenario.setFont(new Font("Arial", Font.BOLD, 15));
        seletorCenario.setForeground(new Color(29, 43, 71));
        seletorCenario.setBackground(new Color(244, 247, 255));

        descricaoCenario = new JLabel(
                "",
                SwingConstants.CENTER
        );

        descricaoCenario.setAlignmentX(Component.CENTER_ALIGNMENT);
        descricaoCenario.setFont(new Font("Arial", Font.PLAIN, 13));
        descricaoCenario.setForeground(new Color(208, 223, 250));
        descricaoCenario.setMaximumSize(new Dimension(650, 30));

        seletorCenario.addActionListener(
                e -> atualizarDescricaoCenario()
        );

        atualizarDescricaoCenario();

        JButton iniciar = criarBotao("INICIAR AVENTURA");

        iniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        iniciar.setMaximumSize(new Dimension(310, 48));

        iniciar.addActionListener(e -> iniciarBatalha());

        JButton abrirPokedex = criarBotao("VER POKEDEX");

        abrirPokedex.setAlignmentX(Component.CENTER_ALIGNMENT);
        abrirPokedex.setMaximumSize(new Dimension(310, 40));

        abrirPokedex.addActionListener(e -> {

            TelaPokedex tela = new TelaPokedex(pokedex);
            tela.setVisible(true);
        });

        JButton carregarJogo = criarBotao("CARREGAR JOGO");
        carregarJogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        carregarJogo.setMaximumSize(new Dimension(310, 40));
        carregarJogo.addActionListener(e -> {
            if (!GerenciadorSave.existeSave()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nenhum jogo salvo foi encontrado.",
                        "Sem progresso salvo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                dispose();
                InterfacePokemon.abrirJogoSalvo();
            }
        });

        centro.add(Box.createVerticalStrut(10));
        centro.add(cards);
        centro.add(Box.createVerticalStrut(18));
        centro.add(rotuloNome);
        centro.add(Box.createVerticalStrut(7));
        centro.add(campoNome);
        centro.add(Box.createVerticalStrut(14));
        centro.add(rotuloCenario);
        centro.add(Box.createVerticalStrut(7));
        centro.add(seletorCenario);
        centro.add(Box.createVerticalStrut(7));
        centro.add(descricaoCenario);
        centro.add(Box.createVerticalStrut(15));
        centro.add(iniciar);
        centro.add(Box.createVerticalStrut(9));
        centro.add(abrirPokedex);
        centro.add(Box.createVerticalStrut(9));
        centro.add(carregarJogo);

        return centro;
    }

    private JLabel criarRotulo(String texto) {

        JLabel rotulo = new JLabel(texto);

        rotulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        rotulo.setFont(new Font("Arial", Font.BOLD, 13));
        rotulo.setForeground(new Color(218, 228, 255));

        return rotulo;
    }

    private void atualizarDescricaoCenario() {

        String cenario = (String) seletorCenario.getSelectedItem();

        if ("Santuário Vulcânico".equals(cenario)) {
            descricaoCenario.setText(
                    "Lava, fumaça e faíscas em uma arena de fogo."
            );
        } else if ("Lagoa de Cristal".equals(cenario)) {
            descricaoCenario.setText(
                    "Cachoeiras, ondas e bolhas em uma lagoa brilhante."
            );
        } else {
            descricaoCenario.setText(
                    "Árvores, folhas e luz suave em uma floresta mágica."
            );
        }
    }

    private JPanel criarRodape() {

        JPanel rodape = new JPanel();
        rodape.setOpaque(false);

        JLabel texto = new JLabel(
                "Monte sua equipe, encontre Pokemon selvagens e complete sua Pokedex."
        );

        texto.setFont(new Font("Arial", Font.PLAIN, 13));
        texto.setForeground(new Color(180, 194, 230));

        rodape.add(texto);

        return rodape;
    }

    private JPanel criarCardPokemon(
            String nomePokemon,
            String tipoPokemon
    ) {

        CardInicial card = new CardInicial();

        card.setLayout(new BoxLayout(
                card,
                BoxLayout.Y_AXIS
        ));

        card.setPreferredSize(new Dimension(190, 206));
        card.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel imagem = new JLabel();
        imagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagem.setHorizontalAlignment(SwingConstants.CENTER);

        ImageIcon icone = carregarImagem(nomePokemon);

        if (icone != null) {
            imagem.setIcon(icone);
        }

        JLabel nome = new JLabel(nomePokemon);

        nome.setAlignmentX(Component.CENTER_ALIGNMENT);
        nome.setFont(new Font("Arial", Font.BOLD, 18));
        nome.setForeground(Color.WHITE);

        JLabel tipo = new JLabel(tipoPokemon);

        tipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        tipo.setFont(new Font("Arial", Font.BOLD, 12));
        tipo.setForeground(new Color(182, 211, 249));

        card.add(imagem);
        card.add(Box.createVerticalStrut(4));
        card.add(nome);
        card.add(Box.createVerticalStrut(5));
        card.add(tipo);

        return card;
    }

    private JButton criarBotao(String texto) {
        return new BotaoInicial(texto);
    }

    private void iniciarBatalha() {

        String nome = campoNome.getText().trim();

        if (nome.isEmpty()) {
            nome = "Treinador";
        }

        String cenario = (String) seletorCenario.getSelectedItem();

        dispose();

        InterfacePokemon jogo = new InterfacePokemon(
                nome,
                cenario
        );

        jogo.setVisible(true);
    }

    private ImageIcon carregarImagem(String nomePokemon) {

        String nomeArquivo = nomePokemon.toLowerCase() + ".png";

        java.net.URL url = TelaInicial.class
                .getClassLoader()
                .getResource("imagens/" + nomeArquivo);

        if (url == null) {
            return null;
        }

        ImageIcon imagemOriginal = new ImageIcon(url);

        Image imagemRedimensionada = imagemOriginal.getImage()
                .getScaledInstance(115, 115, Image.SCALE_SMOOTH);

        return new ImageIcon(imagemRedimensionada);
    }

    private class PainelFundo extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            GradientPaint gradiente = new GradientPaint(
                    0,
                    0,
                    new Color(10, 15, 35),
                    getWidth(),
                    getHeight(),
                    new Color(45, 20, 80)
            );

            g2.setPaint(gradiente);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(130, 103, 225, 30));

            for (int x = -40; x < getWidth(); x += 120) {
                g2.fillOval(x, 85, 170, 170);
            }

            g2.dispose();
        }
    }

    private class CardInicial extends JPanel {

        public CardInicial() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(24, 34, 69, 210));

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    24,
                    24
            );

            g2.setColor(new Color(139, 108, 230));
            g2.setStroke(new BasicStroke(2f));

            g2.drawRoundRect(
                    1,
                    1,
                    getWidth() - 3,
                    getHeight() - 3,
                    24,
                    24
            );

            g2.dispose();
        }
    }

    private class BotaoInicial extends JButton {

        private boolean hover = false;

        public BotaoInicial(String texto) {

            super(texto);

            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 14));
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);

            addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(
                    hover
                            ? new Color(128, 91, 220, 225)
                            : new Color(72, 55, 135, 185)
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    16,
                    16
            );

            g2.setColor(new Color(202, 188, 255));
            g2.setStroke(new BasicStroke(1.5f));

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    16,
                    16
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaInicial tela = new TelaInicial();
            tela.setVisible(true);
        });
    }
}