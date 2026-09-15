package br.com.pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaPokedex extends JFrame {

    private final Pokedex pokedex;
    private final List<Pokemon> pokemons;

    private int indiceAtual = 0;

    private JLabel imagem;
    private JLabel nome;
    private JLabel tipo;
    private JLabel vida;
    private JLabel ataque;
    private JLabel contador;
    private JLabel mensagemVazia;

    public TelaPokedex(Pokedex pokedex) {

        this.pokedex = pokedex;
        this.pokemons = pokedex.getRegistros();

        setTitle("Pokedex");
        setSize(760, 690);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        criarInterface();
        mostrarPokemon();
    }

    private void criarInterface() {

        JPanel fundo = new PainelFundo();
        fundo.setLayout(new BorderLayout(18, 18));
        fundo.setBorder(new EmptyBorder(22, 34, 24, 34));

        fundo.add(criarCabecalho(), BorderLayout.NORTH);
        fundo.add(criarCentro(), BorderLayout.CENTER);
        fundo.add(criarBotoes(), BorderLayout.SOUTH);

        setContentPane(fundo);
    }

    private JPanel criarCabecalho() {

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);

        JLabel titulo = new JLabel("POKEDEX");
        titulo.setFont(new Font("Arial", Font.BOLD, 34));
        titulo.setForeground(Color.WHITE);

        contador = new JLabel();
        contador.setFont(new Font("Arial", Font.BOLD, 14));
        contador.setForeground(new Color(194, 218, 245));
        contador.setHorizontalAlignment(SwingConstants.RIGHT);

        cabecalho.add(titulo, BorderLayout.WEST);
        cabecalho.add(contador, BorderLayout.EAST);

        return cabecalho;
    }

    private JPanel criarCentro() {

        CardPokedex card = new CardPokedex();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(24, 34, 24, 34));

        mensagemVazia = new JLabel(
                "Nenhum Pokemon registrado ainda.",
                SwingConstants.CENTER
        );

        mensagemVazia.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensagemVazia.setFont(new Font("Arial", Font.BOLD, 18));
        mensagemVazia.setForeground(Color.WHITE);

        imagem = new JLabel();
        imagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagem.setHorizontalAlignment(SwingConstants.CENTER);
        imagem.setPreferredSize(new Dimension(260, 245));
        imagem.setMaximumSize(new Dimension(260, 245));

        nome = new JLabel();
        nome.setAlignmentX(Component.CENTER_ALIGNMENT);
        nome.setFont(new Font("Arial", Font.BOLD, 30));
        nome.setForeground(Color.WHITE);

        tipo = new JLabel();
        tipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        tipo.setFont(new Font("Arial", Font.BOLD, 16));
        tipo.setForeground(new Color(183, 221, 255));

        vida = criarInformacao();
        ataque = criarInformacao();

        card.add(mensagemVazia);
        card.add(imagem);
        card.add(Box.createVerticalStrut(4));
        card.add(nome);
        card.add(Box.createVerticalStrut(8));
        card.add(tipo);
        card.add(Box.createVerticalStrut(18));
        card.add(vida);
        card.add(Box.createVerticalStrut(8));
        card.add(ataque);

        return card;
    }

    private JLabel criarInformacao() {

        JLabel label = new JLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(new Color(235, 244, 255));

        return label;
    }

    private JPanel criarBotoes() {

        JPanel botoes = new JPanel(new GridLayout(1, 3, 12, 0));
        botoes.setOpaque(false);

        JButton anterior = criarBotao("ANTERIOR");
        JButton voltar = criarBotao("VOLTAR");
        JButton proximo = criarBotao("PROXIMO");

        anterior.addActionListener(e -> pokemonAnterior());
        voltar.addActionListener(e -> dispose());
        proximo.addActionListener(e -> pokemonProximo());

        botoes.add(anterior);
        botoes.add(voltar);
        botoes.add(proximo);

        return botoes;
    }

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);

        botao.setBackground(new Color(55, 65, 110));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(new EmptyBorder(12, 14, 12, 14));

        botao.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(new Color(90, 70, 165));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(new Color(33, 71, 117));
            }
        });

        return botao;
    }

    private void mostrarPokemon() {

        if (pokemons.isEmpty()) {

            imagem.setVisible(false);
            nome.setVisible(false);
            tipo.setVisible(false);
            vida.setVisible(false);
            ataque.setVisible(false);

            mensagemVazia.setVisible(true);
            contador.setText("0 REGISTRADOS");

            return;
        }

        mensagemVazia.setVisible(false);

        imagem.setVisible(true);
        nome.setVisible(true);
        tipo.setVisible(true);
        vida.setVisible(true);
        ataque.setVisible(true);

        Pokemon pokemon = pokemons.get(indiceAtual);

        nome.setText(pokemon.getNome());
        tipo.setText(descobrirTipo(pokemon));
        vida.setText("HP: " + pokemon.getVida());
        ataque.setText("ATAQUE: " + pokemon.getAtaque());

        imagem.setIcon(carregarImagem(pokemon.getNome()));

        contador.setText(
                (indiceAtual + 1)
                        + " / "
                        + pokemons.size()
                        + " REGISTRADOS"
        );
    }

    private void pokemonProximo() {

        if (pokemons.isEmpty()) {
            return;
        }

        indiceAtual++;

        if (indiceAtual >= pokemons.size()) {
            indiceAtual = 0;
        }

        mostrarPokemon();
    }

    private void pokemonAnterior() {

        if (pokemons.isEmpty()) {
            return;
        }

        indiceAtual--;

        if (indiceAtual < 0) {
            indiceAtual = pokemons.size() - 1;
        }

        mostrarPokemon();
    }

    private String descobrirTipo(Pokemon pokemon) {

        if (pokemon instanceof PokemonFogo) {
            return "TIPO FOGO";
        }

        if (pokemon instanceof PokemonAgua) {
            return "TIPO AGUA";
        }

        if (pokemon instanceof PokemonPlanta) {
            return "TIPO PLANTA";
        }

        return "TIPO DESCONHECIDO";
    }

    private ImageIcon carregarImagem(String nomePokemon) {

        String nomeArquivo = nomePokemon.toLowerCase() + ".png";

        java.net.URL url = TelaPokedex.class
                .getClassLoader()
                .getResource("imagens/" + nomeArquivo);

        if (url == null) {
            return null;
        }

        ImageIcon imagemOriginal = new ImageIcon(url);

        Image imagemRedimensionada = imagemOriginal.getImage()
                .getScaledInstance(230, 230, Image.SCALE_SMOOTH);

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

            g2.setColor(new Color(132, 195, 240, 30));

            for (int i = 0; i < getWidth(); i += 70) {
                g2.fillOval(i, 60, 100, 100);
            }

            g2.dispose();
        }
    }

    private class CardPokedex extends JPanel {

        public CardPokedex() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(25, 30, 55, 220));

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    28,
                    28
            );

            g2.setColor(new Color(141, 108, 232));
            g2.setStroke(new BasicStroke(2f));

            g2.drawRoundRect(
                    1,
                    1,
                    getWidth() - 3,
                    getHeight() - 3,
                    28,
                    28
            );

            g2.dispose();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaPokedex tela = new TelaPokedex(new Pokedex());
            tela.setVisible(true);
        });
    }
}