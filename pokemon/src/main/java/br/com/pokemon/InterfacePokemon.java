package br.com.pokemon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfacePokemon extends JFrame {

    private final Treinador treinador;
    private final Pokedex pokedex;
    private final String nomeTreinador;
    private final String cenario;

    private Pokemon pokemonEscolhido;
    private Pokemon selvagem;

    private JLabel nomeJogador;
    private JLabel vidaJogador;
    private JLabel imagemJogador;
    private JProgressBar barraVidaJogador;

    private JLabel nomeSelvagem;
    private JLabel vidaSelvagem;
    private JLabel imagemSelvagem;
    private JProgressBar barraVidaSelvagem;

    private JLabel mensagem;
    private JPanel botoesEquipe;
    private ArenaPanel arena;
    private JPanel painelGolpes;

    private JButton atacarButton;
private JButton capturarButton;
private JButton novoPokemonButton;
private JButton pokedexButton;
private JButton salvarButton;
private JButton voltarInicioButton;

// Botões de golpes
private JButton golpe1Button;
private JButton golpe2Button;
private JButton golpe3Button;
private JButton voltarGolpesButton;

    private boolean animando = false;
    private boolean turnoJogador = true;

    private static final Color AZUL = new Color(71, 153, 235);
    private static final Color VERMELHO = new Color(239, 94, 94);
    private static final Color VERDE = new Color(58, 196, 121);
    private static final Color AMARELO = new Color(255, 209, 84);

    public InterfacePokemon() {
        this("Ash", "Vale Ensolarado");
    }

    public InterfacePokemon(String nomeTreinador) {
        this(nomeTreinador, "Vale Ensolarado");
    }

    public InterfacePokemon(
            String nomeTreinador,
            String cenario
    ) {

        this.nomeTreinador = nomeTreinador;
        this.cenario = cenario;

        treinador = new Treinador(nomeTreinador);
        pokedex = new Pokedex();

        PokemonFogo ignivulp = new PokemonFogo(
                "Ignivulp",
                100,
                20
        );

        PokemonAgua aquafin = new PokemonAgua(
                "Aquafin",
                100,
                18
        );

        PokemonPlanta florabbit = new PokemonPlanta(
                "Florabbit",
                100,
                19
        );

        // GOLPES DO IGNIVULP
ignivulp.adicionarGolpe(
        new Golpe("Brasa", "Fogo", 20)
);

ignivulp.adicionarGolpe(
        new Golpe("Lanca-Chamas", "Fogo", 30)
);

ignivulp.adicionarGolpe(
        new Golpe("Investida", "Normal", 15)
);


// GOLPES DO AQUAFIN
aquafin.adicionarGolpe(
        new Golpe("Jato de Agua", "Agua", 18)
);

aquafin.adicionarGolpe(
        new Golpe("Hidro Bomba", "Agua", 30)
);

aquafin.adicionarGolpe(
        new Golpe("Investida", "Normal", 15)
);


// GOLPES DO FLORABBIT
florabbit.adicionarGolpe(
        new Golpe("Folha Navalha", "Planta", 19)
);

florabbit.adicionarGolpe(
        new Golpe("Chicote de Vinha", "Planta", 27)
);

florabbit.adicionarGolpe(
        new Golpe("Investida", "Normal", 15)
);

        treinador.adicionarPokemon(ignivulp);
        treinador.adicionarPokemon(aquafin);
        treinador.adicionarPokemon(florabbit);

        pokedex.registrar(ignivulp);
        pokedex.registrar(aquafin);
        pokedex.registrar(florabbit);

        pokemonEscolhido = treinador.getEquipe().get(0);
        selvagem = PokemonFactory.criarPokemonAleatorio();

        criarInterface();
        atualizarTela();
        mostrarMensagem( "SEU TURNO — Escolha uma ação!"
);
    }

    private void criarInterface() {

        setTitle("Pokemon Battle");
        setSize(1000, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel principal = new JPanel(new BorderLayout());
        principal.setBackground(new Color(13, 25, 49));

        principal.add(criarCabecalho(), BorderLayout.NORTH);
        principal.add(criarArena(), BorderLayout.CENTER);
        principal.add(criarAreaInferior(), BorderLayout.SOUTH);

        setContentPane(principal);
    }

    private JPanel criarCabecalho() {

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(new Color(11, 24, 48));
        cabecalho.setBorder(new EmptyBorder(12, 26, 12, 26));

        JLabel titulo = new JLabel("POKEMON BATTLE");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        JLabel informacao = new JLabel(
                "TREINADOR: "
                        + nomeTreinador.toUpperCase()
                        + "   |   CENARIO: "
                        + cenario.toUpperCase()
        );

        informacao.setFont(new Font("Arial", Font.BOLD, 12));
        informacao.setForeground(new Color(186, 211, 245));

        cabecalho.add(titulo, BorderLayout.WEST);
        cabecalho.add(informacao, BorderLayout.EAST);

        return cabecalho;
    }

    private JPanel criarArena() {

        arena = new ArenaPanel();
        arena.setLayout(null);

        StatusPanel statusSelvagem = criarStatusPanel(false);
        StatusPanel statusJogador = criarStatusPanel(true);

        nomeSelvagem = statusSelvagem.nome;
        vidaSelvagem = statusSelvagem.vida;
        barraVidaSelvagem = statusSelvagem.barraVida;

        nomeJogador = statusJogador.nome;
        vidaJogador = statusJogador.vida;
        barraVidaJogador = statusJogador.barraVida;

        imagemSelvagem = criarImagemPokemon();
        imagemJogador = criarImagemPokemon();

        arena.add(statusSelvagem);
        arena.add(statusJogador);
        arena.add(imagemSelvagem);
        arena.add(imagemJogador);

        arena.adicionarComponentes(
                statusSelvagem,
                statusJogador,
                imagemSelvagem,
                imagemJogador
        );

        return arena;
    }

    private JPanel criarAreaInferior() {

        JPanel inferior = new JPanel();
        inferior.setBackground(new Color(11, 24, 48));
        inferior.setBorder(new EmptyBorder(14, 24, 18, 24));
        inferior.setLayout(new BoxLayout(
                inferior,
                BoxLayout.Y_AXIS
        ));

        mensagem = new JLabel(
                "Um Pokemon selvagem apareceu!",
                SwingConstants.CENTER
        );

        mensagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        mensagem.setOpaque(true);
        mensagem.setBackground(new Color(232, 240, 249));
        mensagem.setForeground(new Color(25, 40, 62));
        mensagem.setFont(new Font("Arial", Font.BOLD, 16));
        mensagem.setBorder(new EmptyBorder(14, 20, 14, 20));
        mensagem.setMaximumSize(new Dimension(900, 52));

        JPanel botoesAcao = new JPanel(
                new GridLayout(1, 6, 10, 0)
        );

        botoesAcao.setOpaque(false);
        botoesAcao.setMaximumSize(new Dimension(900, 44));

        atacarButton = criarBotao("ATACAR", false);
        capturarButton = criarBotao("CAPTURAR", false);
        novoPokemonButton = criarBotao("NOVO SELVAGEM", false);
        pokedexButton = criarBotao("POKEDEX", false);
        salvarButton = criarBotao("SALVAR JOGO", false);
        voltarInicioButton = criarBotao("TELA INICIAL", false);
        golpe1Button = criarBotao("GOLPE 1", false);
golpe2Button = criarBotao("GOLPE 2", false);
golpe3Button = criarBotao("GOLPE 3", false);
voltarGolpesButton = criarBotao("VOLTAR", false);

        atacarButton.addActionListener(e -> mostrarGolpes());
        voltarGolpesButton.addActionListener(e -> esconderGolpes());
        golpe1Button.addActionListener(e -> atacarComGolpe(0));
        golpe2Button.addActionListener(e -> atacarComGolpe(1));
        golpe3Button.addActionListener(e -> atacarComGolpe(2));
        capturarButton.addActionListener(e -> capturar());
        novoPokemonButton.addActionListener(
                e -> encontrarPokemonSelvagem()
        );
        pokedexButton.addActionListener(e -> abrirPokedex());
        salvarButton.addActionListener(e -> salvarJogo());
        voltarInicioButton.addActionListener(e -> voltarParaTelaInicial());

        botoesAcao.add(atacarButton);
        botoesAcao.add(capturarButton);
        botoesAcao.add(novoPokemonButton);
        botoesAcao.add(pokedexButton);
        botoesAcao.add(salvarButton);
        botoesAcao.add(voltarInicioButton);
        

        botoesEquipe = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 0)
        );

        botoesEquipe.setOpaque(false);
        botoesEquipe.setMaximumSize(new Dimension(900, 42));

        painelGolpes = new JPanel(
        new GridLayout(1, 4, 10, 0)
);

painelGolpes.setOpaque(false);
painelGolpes.setMaximumSize(
        new Dimension(900, 44)
);

painelGolpes.add(golpe1Button);
painelGolpes.add(golpe2Button);
painelGolpes.add(golpe3Button);
painelGolpes.add(voltarGolpesButton);

painelGolpes.setVisible(false);

        inferior.add(mensagem);
        inferior.add(Box.createVerticalStrut(12));
        inferior.add(painelGolpes);
        inferior.add(Box.createVerticalStrut(12));
        inferior.add(botoesAcao);
        inferior.add(Box.createVerticalStrut(10));
        inferior.add(botoesEquipe);

        atualizarEquipe();

        return inferior;
    }

    private StatusPanel criarStatusPanel(boolean jogador) {

        StatusPanel painel = new StatusPanel(jogador);

        painel.nome = new JLabel();
        painel.nome.setFont(new Font("Arial", Font.BOLD, 20));
        painel.nome.setForeground(Color.WHITE);

        painel.vida = new JLabel();
        painel.vida.setFont(new Font("Arial", Font.BOLD, 13));
        painel.vida.setForeground(new Color(212, 228, 245));

        painel.barraVida = new JProgressBar();
        painel.barraVida.setStringPainted(true);
        painel.barraVida.setFont(new Font("Arial", Font.BOLD, 11));
        painel.barraVida.setForeground(VERDE);
        painel.barraVida.setBackground(new Color(30, 47, 71));
        painel.barraVida.setBorder(BorderFactory.createLineBorder(
                new Color(173, 200, 226),
                1
        ));

        painel.add(painel.nome);
        painel.add(Box.createVerticalStrut(4));
        painel.add(painel.vida);
        painel.add(Box.createVerticalStrut(5));
        painel.add(painel.barraVida);

        return painel;
    }

    private JLabel criarImagemPokemon() {

        JLabel imagem = new JLabel();
        imagem.setHorizontalAlignment(SwingConstants.CENTER);
        imagem.setVerticalAlignment(SwingConstants.CENTER);

        return imagem;
    }

    private JButton criarBotao(
            String texto,
            boolean selecionado
    ) {

        BotaoTransparente botao = new BotaoTransparente(
                texto,
                selecionado
        );

        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(new EmptyBorder(10, 10, 10, 10));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return botao;
    }

    private void escolherPokemon(Pokemon pokemon) {

    if (animando || !turnoJogador) {
        return;
    }

    if (pokemon == null) {
        return;
    }

    if (pokemon.getVida() <= 0) {
        mostrarMensagem(
                pokemon.getNome() + " está sem vida!"
        );
        return;
    }

    if (pokemon == pokemonEscolhido) {
        mostrarMensagem(
                pokemon.getNome() + " já está em batalha!"
        );
        return;
    }

    // Guarda o Pokémon que estava em batalha
    Pokemon pokemonAnterior = pokemonEscolhido;

    pokemonEscolhido = pokemon;

    atualizarTela();
    atualizarEquipe();

    mostrarMensagem(
            "Você trocou "
                    + pokemonAnterior.getNome()
                    + " por "
                    + pokemon.getNome()
                    + "!"
    );

    // A troca consome o turno
    turnoJogador = false;
    animando = true;
    bloquearBotoes(true);

    // Pequena pausa antes do contra-ataque
    Timer timer = new Timer(900, null);

    timer.addActionListener(e -> {

        timer.stop();

        if (selvagem.getVida() > 0) {
            contraAtacar();
        }
    });

    timer.setRepeats(false);
    timer.start();
}

private void mostrarGolpes() {

    if (animando || !turnoJogador) {
        return;
    }

    atualizarBotoesGolpes();

    painelGolpes.setVisible(true);

    atacarButton.setVisible(false);
    capturarButton.setVisible(false);
    novoPokemonButton.setVisible(false);
    pokedexButton.setVisible(false);
    salvarButton.setVisible(false);
    voltarInicioButton.setVisible(false);

    mensagem.setText(
            "Escolha um golpe para atacar!"
    );

    painelGolpes.getParent().revalidate();
    painelGolpes.getParent().repaint();
}

private void atualizarBotoesGolpes() {

    java.util.List<Golpe> golpes =
            pokemonEscolhido.getGolpes();

    golpe1Button.setText(
            golpes.size() > 0
                    ? golpes.get(0).getNome()
                    : "-"
    );

    golpe2Button.setText(
            golpes.size() > 1
                    ? golpes.get(1).getNome()
                    : "-"
    );

    golpe3Button.setText(
            golpes.size() > 2
                    ? golpes.get(2).getNome()
                    : "-"
    );

    golpe1Button.setEnabled(golpes.size() > 0);
    golpe2Button.setEnabled(golpes.size() > 1);
    golpe3Button.setEnabled(golpes.size() > 2);
}

private void esconderGolpes() {

    painelGolpes.setVisible(false);

    atacarButton.setVisible(true);
    capturarButton.setVisible(true);
    novoPokemonButton.setVisible(true);
    pokedexButton.setVisible(true);
    salvarButton.setVisible(true);
    voltarInicioButton.setVisible(true);

    mensagem.setText(
            "Escolha uma ação!"
    );

    painelGolpes.getParent().revalidate();
    painelGolpes.getParent().repaint();
}

    private void atacarComGolpe(int indiceGolpe) {

        if (!turnoJogador
                || animando
                || pokemonEscolhido.getVida() <= 0
                || selvagem.getVida() <= 0) {
            return;
        }

        java.util.List<Golpe> golpes = pokemonEscolhido.getGolpes();

        if (indiceGolpe < 0 || indiceGolpe >= golpes.size()) {
            return;
        }

        Golpe golpe = golpes.get(indiceGolpe);

        esconderGolpes();
        executarAtaque(golpe.getNome(), golpe.getDano(), golpe.getTipo());
    }

    private void executarAtaque(String nomeGolpe, int danoBase, String tipoGolpe) {

        turnoJogador = false;
        animando = true;
        bloquearBotoes(true);

        int vidaAntes = selvagem.getVida();

        mostrarMensagem(
                pokemonEscolhido.getNome()
                        + " usou " + nomeGolpe + "!"
        );

        animarAtaque(true, nomeGolpe, () -> {

            int dano = danoBase;

            if (TipoPokemon.temVantagem(
                    tipoGolpe,
                    selvagem.getTipo())) {
                dano *= 2;
            }

            selvagem.receberDano(dano);
            int danoReal = vidaAntes - selvagem.getVida();

            boolean superEfetivo = TipoPokemon.temVantagem(
                    tipoGolpe,
                    selvagem.getTipo());

            String mensagemAtaque =
                    pokemonEscolhido.getNome()
                            + " causou "
                            + danoReal
                            + " de dano!";

            if (superEfetivo) {
                mensagemAtaque += " SUPER EFETIVO!";
            }

            mostrarMensagem(mensagemAtaque);

            mostrarDanoAnimado(
                    danoReal,
                    superEfetivo,
                    imagemSelvagem.getX()
                            + imagemSelvagem.getWidth() / 2,
                    imagemSelvagem.getY() + 40
            );

            atualizarDadosPokemon(
                    selvagem,
                    nomeSelvagem,
                    vidaSelvagem,
                    imagemSelvagem
            );

            animarBarraVida(
                    barraVidaSelvagem,
                    vidaAntes,
                    selvagem.getVida(),
                    () -> {
                        if (selvagem.getVida() <= 0) {
                            mostrarTelaResultado(true);
                        } else {
                            contraAtacar();
                        }
                    }
            );
        });
    }

    private void atacar() {

        java.util.List<Golpe> golpes = pokemonEscolhido.getGolpes();

        if (golpes.isEmpty()) {
            executarAtaque("Ataque", pokemonEscolhido.getAtaque(), pokemonEscolhido.getTipo());
        } else {
            atacarComGolpe(0);
        }
    }

    private void contraAtacar() {

    if (selvagem.getVida() <= 0) {
        return;
    }

    animando = true;
    bloquearBotoes(true);

    // Guarda a vida antes do ataque
    int vidaAntes = pokemonEscolhido.getVida();

    mostrarMensagem(
            selvagem.getNome()
                    + " esta preparando o contra-ataque..."
    );

    animarAtaque(false, "Ataque", () -> {

        // Pokémon selvagem ataca
        selvagem.atacar(pokemonEscolhido);

        // Calcula o dano causado
        int dano = vidaAntes - pokemonEscolhido.getVida();

        // Verifica vantagem de tipo
        boolean superEfetivo = TipoPokemon.temVantagem(
                selvagem.getTipo(),
                pokemonEscolhido.getTipo()
        );

        // Monta mensagem
        String mensagemAtaque =
                selvagem.getNome()
                        + " causou "
                        + dano
                        + " de dano!";

        if (superEfetivo) {
            mensagemAtaque += " SUPER EFETIVO!";
        }

        mostrarMensagem(mensagemAtaque);

        // Mostra dano na tela
        mostrarDanoAnimado(
                dano,
                superEfetivo,
                imagemJogador.getX()
                        + imagemJogador.getWidth() / 2,
                imagemJogador.getY() + 40
        );

        // Atualiza informações do Pokémon
        atualizarDadosPokemon(
                pokemonEscolhido,
                nomeJogador,
                vidaJogador,
                imagemJogador
        );

        // Anima a barra de HP
        animarBarraVida(
                barraVidaJogador,
                vidaAntes,
                pokemonEscolhido.getVida(),
                () -> {

                    if (pokemonEscolhido.getVida() <= 0) {

                        mostrarTelaResultado(false);

                    } else {

                        // Volta para o turno do jogador
                        turnoJogador = true;

                        finalizarTurno(
                                "SEU TURNO — Escolha uma ação!"
                        );
                    }
                }
        );
    });
}

    private void animarAtaque(
            boolean jogadorAtaca,
            String nomeGolpe,
            Runnable aoTerminar
    ) {

        JLabel atacante = jogadorAtaca
                ? imagemJogador
                : imagemSelvagem;

        JLabel alvo = jogadorAtaca
                ? imagemSelvagem
                : imagemJogador;

        Rectangle origemAtacante = atacante.getBounds();
        Rectangle origemAlvo = alvo.getBounds();

        int direcao = jogadorAtaca ? 1 : -1;
        int[] quadro = {0};

        EfeitoAtaque efeito = new EfeitoAtaque(nomeGolpe);
        arena.add(efeito);
        arena.setComponentZOrder(efeito, 0);

        Timer timer = new Timer(24, null);

        timer.addActionListener(e -> {
            quadro[0]++;
            efeito.setQuadro(quadro[0]);

            double movimento;
            if (quadro[0] <= 18) {
                movimento = quadro[0] / 18.0;
            } else if (quadro[0] <= 36) {
                movimento = 1.0;
            } else {
                movimento = (54 - quadro[0]) / 18.0;
            }

            int deslocamento = (int) (75 * movimento * direcao);

            atacante.setBounds(
                    origemAtacante.x + deslocamento,
                    origemAtacante.y,
                    origemAtacante.width,
                    origemAtacante.height
            );

            if (quadro[0] >= 18 && quadro[0] <= 40) {
                double viagem = (quadro[0] - 18) / 22.0;

                int inicioX = origemAtacante.x + 105;
                int inicioY = origemAtacante.y + 92;
                int fimX = origemAlvo.x + 110;
                int fimY = origemAlvo.y + 90;

                int efeitoX = (int) (
                        inicioX + (fimX - inicioX) * viagem
                );

                int efeitoY = (int) (
                        inicioY + (fimY - inicioY) * viagem
                                - Math.sin(viagem * Math.PI) * 55
                );

                efeito.setBounds(efeitoX, efeitoY, 110, 110);
                efeito.setVisible(true);
                efeito.repaint();
            }

            if (quadro[0] >= 40 && quadro[0] <= 47) {
                int tremida = quadro[0] % 2 == 0 ? 12 : -12;

                alvo.setBounds(
                        origemAlvo.x + tremida,
                        origemAlvo.y,
                        origemAlvo.width,
                        origemAlvo.height
                );
            }

            if (quadro[0] >= 54) {
                atacante.setBounds(origemAtacante);
                alvo.setBounds(origemAlvo);

                arena.remove(efeito);
                arena.repaint();

                timer.stop();
                aoTerminar.run();
            }
        });

        timer.start();
    }

    private void piscarPokemon(
        JLabel pokemon,
        int quantidade
) {

    int[] contador = {0};

    Timer timer = new Timer(80, null);

    timer.addActionListener(e -> {

        contador[0]++;

        pokemon.setVisible(
                contador[0] % 2 != 0
        );

        if (contador[0] >= quantidade * 2) {

            pokemon.setVisible(true);

            timer.stop();
        }
    });

    timer.start();
}

    private void efeitoImpacto(
        JLabel alvo
) {

    Rectangle origem = alvo.getBounds();

    int[] quadro = {0};

    Timer timer = new Timer(35, null);

    timer.addActionListener(e -> {

        quadro[0]++;

        // Primeira parte: sacudida forte
        if (quadro[0] <= 12) {

            int intensidade;

            if (quadro[0] <= 6) {
                intensidade = 14;
            } else {
                intensidade = 8;
            }

            int deslocamentoX =
                    (quadro[0] % 2 == 0)
                            ? intensidade
                            : -intensidade;

            int deslocamentoY =
                    (quadro[0] % 3 == 0)
                            ? 5
                            : -5;

            alvo.setBounds(
                    origem.x + deslocamentoX,
                    origem.y + deslocamentoY,
                    origem.width,
                    origem.height
            );

        } else {

            // Volta para a posição original
            alvo.setBounds(origem);
        }

        if (quadro[0] >= 16) {

            alvo.setBounds(origem);

            timer.stop();
        }
    });

    timer.start();
}

private void animarBarraVida(
            JProgressBar barra,
            int vidaInicial,
            int vidaFinal,
            Runnable aoTerminar
    ) {

        int inicial = Math.max(0, vidaInicial);
        int finalVida = Math.max(0, vidaFinal);

        int maximo = Math.max(
                100,
                Math.max(inicial, finalVida)
        );

        barra.setMaximum(maximo);

        int[] exibida = {inicial};

        Timer timer = new Timer(18, null);

        timer.addActionListener(e -> {

            if (exibida[0] > finalVida) {
                exibida[0]--;
            }

            barra.setValue(exibida[0]);
            barra.setString("HP " + exibida[0]);

            atualizarCorBarra(barra, exibida[0], maximo);

            if (exibida[0] <= finalVida) {
                timer.stop();
                aoTerminar.run();
            }
        });

        timer.start();
    }

    private void capturar() {

        if (animando || selvagem.getVida() <= 0) {
            return;
        }

        animando = true;
        bloquearBotoes(true);

        mostrarMensagem("Pokebola, vai!");

        int chance = (int) Math.round(
                treinador.calcularChanceCaptura(selvagem) * 100
        );

mostrarMensagem(
        "Chance de captura: "
                + chance
                + "% - Pokebola, vai!"
);

        animarPokebola(() -> {

            Captura captura = new Captura(treinador);
            boolean conseguiu = captura.tentarCapturar(selvagem);

            animando = false;
            bloquearBotoes(false);
            atualizarBotoes();

            if (conseguiu) {
                pokedex.registrar(selvagem);
                atualizarEquipe();

                mostrarMensagem(
                        selvagem.getNome()
                                + " foi capturado!"
                );
            } else {
                mostrarMensagem(
                        selvagem.getNome()
                                + " escapou da Pokebola!"
                );
            }
        });
    }

    private void animarPokebola(Runnable aoTerminar) {

        Pokebola pokebola = new Pokebola();

        int inicioX = 310;
        int inicioY = 315;
        int destinoX = arena.getWidth() - 285;
        int destinoY = 205;

        pokebola.setBounds(inicioX, inicioY, 52, 52);

        arena.add(pokebola);
        arena.setComponentZOrder(pokebola, 0);

        int[] quadro = {0};

        Timer timer = new Timer(25, null);

        timer.addActionListener(e -> {

            quadro[0]++;

            if (quadro[0] <= 20) {

                double progresso = quadro[0] / 20.0;

                pokebola.setLocation(
                        (int) (
                                inicioX
                                        + (destinoX - inicioX)
                                        * progresso
                        ),
                        (int) (
                                inicioY
                                        + (destinoY - inicioY)
                                        * progresso
                        )
                );
            } else if (quadro[0] <= 55) {

                int tremida = quadro[0] % 2 == 0 ? 8 : -8;

                pokebola.setLocation(
                        destinoX + tremida,
                        destinoY
                );
            } else {

                arena.remove(pokebola);
                arena.repaint();

                timer.stop();
                aoTerminar.run();
            }
        });

        timer.start();
    }

    private void encontrarPokemonSelvagem() {

        if (animando) {
            return;
        }

        selvagem = PokemonFactory.criarPokemonAleatorio();

        atualizarTela();

        mostrarMensagem(
                "Um "
                        + selvagem.getNome()
                        + " selvagem apareceu!"
        );
    }

    private void atualizarTela() {

        atualizarPokemon(
                pokemonEscolhido,
                nomeJogador,
                vidaJogador,
                barraVidaJogador,
                imagemJogador
        );

        atualizarPokemon(
                selvagem,
                nomeSelvagem,
                vidaSelvagem,
                barraVidaSelvagem,
                imagemSelvagem
        );

        atualizarBotoes();
    }

    private void atualizarPokemon(
            Pokemon pokemon,
            JLabel nome,
            JLabel vida,
            JProgressBar barra,
            JLabel imagem
    ) {

        int vidaAtual = Math.max(0, pokemon.getVida());
        int vidaMaxima = Math.max(100, vidaAtual);

        nome.setText(pokemon.getNome());
        vida.setText("HP: " + vidaAtual);

        barra.setMaximum(vidaMaxima);
        barra.setValue(vidaAtual);
        barra.setString("HP " + vidaAtual);

        atualizarCorBarra(barra, vidaAtual, vidaMaxima);

        imagem.setIcon(carregarImagem(pokemon.getNome()));
    }

    private void atualizarDadosPokemon(
            Pokemon pokemon,
            JLabel nome,
            JLabel vida,
            JLabel imagem
    ) {

        nome.setText(pokemon.getNome());
        vida.setText("HP: " + Math.max(0, pokemon.getVida()));
        imagem.setIcon(carregarImagem(pokemon.getNome()));
    }

    private void atualizarCorBarra(
            JProgressBar barra,
            int vida,
            int maximo
    ) {

        if (vida > maximo * 0.5) {
            barra.setForeground(VERDE);
        } else if (vida > maximo * 0.2) {
            barra.setForeground(AMARELO);
        } else {
            barra.setForeground(VERMELHO);
        }
    }

    private void atualizarBotoes() {

    boolean podeJogar =
            !animando
            && turnoJogador
            && pokemonEscolhido.getVida() > 0
            && selvagem.getVida() > 0;

    atacarButton.setEnabled(podeJogar);

    capturarButton.setEnabled(
            !animando
            && turnoJogador
            && selvagem.getVida() > 0
    );

    novoPokemonButton.setEnabled(
            !animando
            && turnoJogador
    );

    pokedexButton.setEnabled(
            !animando
            && turnoJogador
    );

    salvarButton.setEnabled(!animando);
    voltarInicioButton.setEnabled(!animando);

    for (Component componente : botoesEquipe.getComponents()) {
        componente.setEnabled(
                !animando && turnoJogador
        );
    }
}

    private void atualizarEquipe() {

        if (botoesEquipe == null) {
            return;
        }

        botoesEquipe.removeAll();

        for (Pokemon pokemon : treinador.getEquipe()) {

            JButton botao = criarBotao(
                    pokemon.getNome(),
                    pokemon == pokemonEscolhido
            );

            botao.setPreferredSize(new Dimension(130, 36));
            botao.addActionListener(e -> escolherPokemon(pokemon));

            botoesEquipe.add(botao);
        }

        botoesEquipe.revalidate();
        botoesEquipe.repaint();
    }

    private void bloquearBotoes(boolean bloquear) {

        atacarButton.setEnabled(!bloquear);
        capturarButton.setEnabled(!bloquear);
        novoPokemonButton.setEnabled(!bloquear);
        pokedexButton.setEnabled(!bloquear);
        salvarButton.setEnabled(!bloquear);
        voltarInicioButton.setEnabled(!bloquear);

        for (Component componente : botoesEquipe.getComponents()) {
            componente.setEnabled(!bloquear);
        }
    }

    private void voltarParaTelaInicial() {

        if (animando) {
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja voltar para a tela inicial?\n\n"
                        + "As alterações desta partida só serão mantidas se você tiver salvado o jogo.",
                "Voltar para a tela inicial",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            dispose();

            SwingUtilities.invokeLater(() -> {
                TelaInicial tela = new TelaInicial();
                tela.setVisible(true);
            });
        }
    }

    private void finalizarTurno(String texto) {

        animando = false;
        bloquearBotoes(false);
        atualizarBotoes();
        mostrarMensagem(texto);
    }

    private void mostrarTelaResultado(boolean venceu) {

        animando = false;
        bloquearBotoes(false);
        atualizarBotoes();

        JDialog dialog = new JDialog(
                this,
                venceu ? "Vitoria" : "Derrota",
                true
        );

        JPanel fundo = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();

                GradientPaint gradiente = new GradientPaint(
                        0,
                        0,
                        venceu
                                ? new Color(27, 95, 78)
                                : new Color(105, 42, 55),
                        0,
                        getHeight(),
                        new Color(17, 28, 52)
                );

                g2.setPaint(gradiente);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.dispose();
            }
        };

        fundo.setLayout(new BoxLayout(
                fundo,
                BoxLayout.Y_AXIS
        ));

        fundo.setBorder(new EmptyBorder(35, 45, 35, 45));

        JLabel titulo = new JLabel(
                venceu ? "VITORIA!" : "DERROTA"
        );

        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 34));
        titulo.setForeground(Color.WHITE);

        JLabel texto = new JLabel(
                venceu
                        ? pokemonEscolhido.getNome()
                                + " venceu a batalha!"
                        : pokemonEscolhido.getNome()
                                + " nao consegue mais lutar."
        );

        texto.setAlignmentX(Component.CENTER_ALIGNMENT);
        texto.setFont(new Font("Arial", Font.BOLD, 16));
        texto.setForeground(new Color(225, 235, 250));

        JButton continuar = criarBotao(
                venceu
                        ? "NOVO POKEMON SELVAGEM"
                        : "ESCOLHER EQUIPE",
                false
        );

        continuar.setAlignmentX(Component.CENTER_ALIGNMENT);
        continuar.setMaximumSize(new Dimension(280, 45));

        continuar.addActionListener(e -> {

            dialog.dispose();

            if (venceu) {
                encontrarPokemonSelvagem();
            } else {
                mostrarMensagem(
                        "Escolha outro Pokemon da sua equipe."
                );
            }
        });

        JButton fechar = criarBotao("FECHAR", false);

        fechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        fechar.setMaximumSize(new Dimension(280, 40));
        fechar.addActionListener(e -> dialog.dispose());

        fundo.add(titulo);
        fundo.add(Box.createVerticalStrut(14));
        fundo.add(texto);
        fundo.add(Box.createVerticalStrut(28));
        fundo.add(continuar);
        fundo.add(Box.createVerticalStrut(10));
        fundo.add(fechar);

        dialog.setContentPane(fundo);
        dialog.setSize(460, 310);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private void mostrarMensagem(String texto) {
        mensagem.setText(texto);
    }
    
    private void mostrarDanoAnimado(
        int dano,
        boolean superEfetivo,
        int x,
        int y
) {

    JLabel danoLabel = new JLabel(
            "<html><center>"
                    + "<font size='6'><b>-" + dano + " HP</b></font>"
                    + (superEfetivo
                    ? "<br><font size='4'><b>SUPER EFETIVO!</b></font>"
                    : "")
                    + "</center></html>",
            SwingConstants.CENTER
    );

    danoLabel.setForeground(
            superEfetivo
                    ? new Color(255, 225, 80)
                    : Color.WHITE
    );

    danoLabel.setOpaque(false);

    danoLabel.setBounds(
            x - 100,
            y - 20,
            240,
            superEfetivo ? 90 : 60
    );

    arena.add(danoLabel);
    arena.setComponentZOrder(danoLabel, 0);

    int[] quadro = {0};

    Timer timer = new Timer(30, null);

    timer.addActionListener(e -> {

        quadro[0]++;

        // Movimento para cima
        int movimento = quadro[0] * 2;

        danoLabel.setLocation(
                x - 100,
                y - 20 - movimento
        );

        // Tamanho do efeito
        if (quadro[0] <= 8) {

            danoLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            24 + quadro[0]
                    )
            );

        } else {

            danoLabel.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            32
                    )
            );
        }

        // Desaparecimento gradual
        int alpha;

        if (quadro[0] < 12) {
            alpha = 255;
        } else {
            alpha = Math.max(
                    0,
                    255 - (quadro[0] - 12) * 11
            );
        }

        danoLabel.setForeground(
                new Color(
                        superEfetivo ? 255 : 255,
                        superEfetivo ? 225 : 255,
                        superEfetivo ? 80 : 255,
                        alpha
                )
        );

        if (quadro[0] >= 36) {

            timer.stop();

            arena.remove(danoLabel);
            arena.repaint();
        }
    });

    timer.start();
}
    private void salvarJogo() {
        try {
            GameSave save = new GameSave(
                    treinador,
                    pokedex,
                    pokemonEscolhido,
                    selvagem,
                    nomeTreinador,
                    cenario
            );

            GerenciadorSave.salvar(save);
            mostrarMensagem("Jogo salvo com sucesso!");

            JOptionPane.showMessageDialog(
                    this,
                    "Seu progresso foi salvo!\n\nVocê poderá continuar de onde parou usando CARREGAR JOGO.",
                    "Jogo salvo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível salvar o jogo: " + e.getMessage(),
                    "Erro ao salvar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void abrirJogoSalvo() {
        try {
            GameSave save = GerenciadorSave.carregar();
            InterfacePokemon tela = new InterfacePokemon(
                    save.getNomeTreinador(),
                    save.getCenario()
            );

            tela.treinador.getEquipe().clear();
            tela.treinador.getEquipe().addAll(save.getTreinador().getEquipe());
            tela.pokedex.limpar();
            for (Pokemon pokemon : save.getPokedex().getRegistros()) {
                tela.pokedex.registrar(pokemon);
            }

            tela.pokemonEscolhido = save.getPokemonEscolhido();
            tela.selvagem = save.getSelvagem();
            tela.turnoJogador = true;
            tela.animando = false;
            tela.atualizarTela();
            tela.mostrarMensagem("Progresso carregado! Seu turno — escolha uma ação!");
            tela.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível carregar o jogo.\nVerifique se existe um save.",
                    "Erro ao carregar",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void abrirPokedex() {

        TelaPokedex tela = new TelaPokedex(pokedex);
        tela.setVisible(true);
    }

    private ImageIcon carregarImagem(String nomePokemon) {

        String nomeArquivo = nomePokemon.toLowerCase() + ".png";

        java.net.URL url = InterfacePokemon.class
                .getClassLoader()
                .getResource("imagens/" + nomeArquivo);

        if (url == null) {
            return null;
        }

        ImageIcon imagemOriginal = new ImageIcon(url);

        Image imagemRedimensionada = imagemOriginal.getImage()
                .getScaledInstance(220, 220, Image.SCALE_SMOOTH);

        return new ImageIcon(imagemRedimensionada);
    }

    private Color obterCorTipo(Pokemon pokemon) {

        if (pokemon instanceof PokemonFogo) {
            return new Color(255, 141, 60);
        }

        if (pokemon instanceof PokemonAgua) {
            return new Color(67, 165, 255);
        }

        if (pokemon instanceof PokemonPlanta) {
            return new Color(91, 214, 121);
        }

        return new Color(255, 230, 120);
    }

    private class ArenaPanel extends JPanel {

        private Component statusSelvagem;
        private Component statusJogador;
        private Component pokemonSelvagem;
        private Component pokemonJogador;

        private double tempo = 0;

        public ArenaPanel() {

            Timer timerCenario = new Timer(40, e -> {

                tempo += 0.08;

                if (!animando && statusSelvagem != null) {
                    posicionarComponentes();
                }

                repaint();
            });

            timerCenario.start();
        }

        public void adicionarComponentes(
                Component statusSelvagem,
                Component statusJogador,
                Component pokemonSelvagem,
                Component pokemonJogador
        ) {

            this.statusSelvagem = statusSelvagem;
            this.statusJogador = statusJogador;
            this.pokemonSelvagem = pokemonSelvagem;
            this.pokemonJogador = pokemonJogador;
        }

        @Override
        public void doLayout() {
            posicionarComponentes();
        }

        private void posicionarComponentes() {

            int largura = getWidth();
            int altura = getHeight();

            int jogadorY = (int) (Math.sin(tempo) * 6);
            int selvagemY = (int) (
                    Math.sin(tempo + 1.8) * 6
            );

            statusSelvagem.setBounds(
                    largura - 365,
                    28,
                    305,
                    84
            );

            pokemonSelvagem.setBounds(
                    largura - 390,
                    104 + selvagemY,
                    280,
                    210
            );

            pokemonJogador.setBounds(
                    88,
                    138 + jogadorY,
                    280,
                    220
            );

            statusJogador.setBounds(
                    58,
                    altura - 105,
                    305,
                    84
            );
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            if (cenario.equals("Santuário Vulcânico")) {
                desenharCenarioVulcanico(g2);
            } else if (cenario.equals("Lagoa de Cristal")) {
                desenharCenarioLagoa(g2);
            } else {
                desenharCenarioFloresta(g2);
            }

            desenharPlataforma(
                    g2,
                    90,
                    350,
                    290 + (int) (Math.sin(tempo) * 8),
                    66,
                    obterCorTipo(pokemonEscolhido)
            );

            desenharPlataforma(
                    g2,
                    610,
                    278,
                    250 + (int) (Math.sin(tempo + 1.8) * 8),
                    58,
                    obterCorTipo(selvagem)
            );

            g2.dispose();
        }

        private void desenharCenarioVulcanico(Graphics2D g2) {

            GradientPaint ceu = new GradientPaint(
                    0,
                    0,
                    new Color(47, 27, 50),
                    0,
                    getHeight(),
                    new Color(216, 76, 50)
            );

            g2.setPaint(ceu);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(81, 55, 64));

            Polygon montanha = new Polygon(
                    new int[]{0, 240, 470, 690, 1000},
                    new int[]{280, 65, 275, 95, 280},
                    5
            );

            g2.fillPolygon(montanha);

            g2.setColor(new Color(52, 39, 49));
            g2.fillRect(0, 260, getWidth(), getHeight());

            g2.setColor(new Color(255, 100, 40));

            for (int i = 0; i < 10; i++) {
                int x = (i * 108 + (int) (tempo * 30)) % getWidth();
                int y = 110 + (i * 37) % 170;
                g2.fillOval(x, y, 5, 5);
            }

            g2.setColor(new Color(255, 132, 40, 140));
            g2.fillOval(-90, 355, 570, 140);
            g2.fillOval(510, 330, 590, 165);
        }

        private void desenharCenarioLagoa(Graphics2D g2) {

            GradientPaint ceu = new GradientPaint(
                    0,
                    0,
                    new Color(86, 190, 241),
                    0,
                    getHeight(),
                    new Color(190, 244, 255)
            );

            g2.setPaint(ceu);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(245, 252, 255));
            desenharNuvem(g2, 155, 52);
            desenharNuvem(g2, 670, 86);

            g2.setColor(new Color(54, 132, 167));
            g2.fillRoundRect(90, 80, 155, 235, 55, 55);
            g2.fillRoundRect(690, 66, 125, 250, 55, 55);

            g2.setColor(new Color(32, 139, 192));
            g2.fillRect(0, 255, getWidth(), getHeight());

            g2.setColor(new Color(115, 216, 244, 130));

            for (int y = 280; y < getHeight(); y += 36) {
                int deslocamento = (int) (
                        Math.sin(tempo + y) * 18
                );

                g2.drawArc(
                        20 + deslocamento,
                        y,
                        260,
                        28,
                        0,
                        180
                );

                g2.drawArc(
                        580 + deslocamento,
                        y + 12,
                        280,
                        30,
                        0,
                        180
                );
            }

            g2.setColor(new Color(215, 251, 255, 110));

            for (int i = 0; i < 14; i++) {
                int x = (i * 76) % getWidth();
                int y = 250 + (int) (
                        Math.sin(tempo + i) * 30
                );

                g2.fillOval(x, y, 5, 5);
            }
        }

        private void desenharCenarioFloresta(Graphics2D g2) {

            GradientPaint ceu = new GradientPaint(
                    0,
                    0,
                    new Color(49, 91, 85),
                    0,
                    getHeight(),
                    new Color(125, 187, 109)
            );

            g2.setPaint(ceu);
            g2.fillRect(0, 0, getWidth(), getHeight());

            desenharArvore(g2, 50, 60, 140);
            desenharArvore(g2, 765, 38, 160);
            desenharArvore(g2, 555, 105, 105);

            g2.setColor(new Color(48, 124, 70));
            g2.fillRect(0, 255, getWidth(), getHeight());

            g2.setColor(new Color(73, 158, 84));
            g2.fillOval(-150, 285, 650, 250);
            g2.fillOval(440, 275, 650, 245);

            for (int i = 0; i < 20; i++) {
                int x = (int) (
                        (i * 77 + tempo * 18) % getWidth()
                );

                int y = 100 + (int) (
                        Math.sin(tempo + i) * 45
                );

                g2.setColor(new Color(225, 250, 130, 150));
                g2.fillOval(x, y, 6, 10);
            }
        }

        private void desenharNuvem(
                Graphics2D g2,
                int x,
                int y
        ) {

            g2.fillOval(x, y + 14, 64, 30);
            g2.fillOval(x + 26, y, 62, 44);
            g2.fillOval(x + 62, y + 14, 65, 30);
        }

        private void desenharArvore(
                Graphics2D g2,
                int x,
                int y,
                int tamanho
        ) {

            g2.setColor(new Color(74, 53, 40));
            g2.fillRect(
                    x + tamanho / 2 - 13,
                    y + tamanho / 2,
                    26,
                    tamanho
            );

            g2.setColor(new Color(36, 100, 59));
            g2.fillOval(x, y, tamanho, tamanho);
            g2.fillOval(
                    x - tamanho / 4,
                    y + tamanho / 4,
                    tamanho,
                    tamanho
            );

            g2.fillOval(
                    x + tamanho / 3,
                    y + tamanho / 4,
                    tamanho,
                    tamanho
            );
        }

        private void desenharPlataforma(
                Graphics2D g2,
                int x,
                int y,
                int largura,
                int altura,
                Color corBrilho
        ) {

            g2.setColor(new Color(20, 45, 38, 150));
            g2.fillOval(x, y + 14, largura, altura);

            g2.setColor(new Color(
                    corBrilho.getRed(),
                    corBrilho.getGreen(),
                    corBrilho.getBlue(),
                    150
            ));

            g2.fillOval(x, y, largura, altura - 10);

            g2.setColor(new Color(240, 255, 220, 180));
            g2.setStroke(new BasicStroke(2f));
            g2.drawOval(x, y, largura, altura - 10);
        }
    }

    private class StatusPanel extends JPanel {

        private JLabel nome;
        private JLabel vida;
        private JProgressBar barraVida;
        private final boolean jogador;

        public StatusPanel(boolean jogador) {

            this.jogador = jogador;

            setOpaque(false);
            setLayout(new BoxLayout(
                    this,
                    BoxLayout.Y_AXIS
            ));

            setBorder(new EmptyBorder(12, 16, 12, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(13, 31, 58, 215));

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    18,
                    18
            );

            g2.setStroke(new BasicStroke(2f));
            g2.setColor(jogador ? AZUL : VERMELHO);

            g2.drawRoundRect(
                    1,
                    1,
                    getWidth() - 3,
                    getHeight() - 3,
                    18,
                    18
            );

            g2.dispose();
        }
    }

    private class BotaoTransparente extends JButton {

        private final boolean selecionado;
        private boolean hover = false;

        public BotaoTransparente(
                String texto,
                boolean selecionado
        ) {

            super(texto);

            this.selecionado = selecionado;

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

            Color fundo;

            if (!isEnabled()) {
                fundo = new Color(90, 107, 130, 80);
            } else if (selecionado) {
                fundo = new Color(255, 202, 71, hover ? 220 : 180);
            } else if (hover) {
                fundo = new Color(138, 182, 235, 180);
            } else {
                fundo = new Color(30, 64, 105, 165);
            }

            g2.setColor(fundo);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    14,
                    14
            );

            g2.setColor(new Color(186, 218, 250, 180));
            g2.setStroke(new BasicStroke(1.5f));

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    14,
                    14
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    private class Pokebola extends JPanel {

        public Pokebola() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int tamanho = Math.min(getWidth(), getHeight());

            g2.setColor(new Color(35, 35, 40, 100));
            g2.fillOval(5, tamanho - 8, tamanho - 8, 12);

            g2.setColor(new Color(220, 62, 62));
            g2.fillArc(
                    2,
                    2,
                    tamanho - 5,
                    tamanho - 5,
                    0,
                    180
            );

            g2.setColor(Color.WHITE);
            g2.fillArc(
                    2,
                    2,
                    tamanho - 5,
                    tamanho - 5,
                    180,
                    180
            );

            g2.setColor(new Color(38, 38, 45));
            g2.setStroke(new BasicStroke(4f));
            g2.drawOval(2, 2, tamanho - 5, tamanho - 5);

            g2.drawLine(
                    4,
                    tamanho / 2,
                    tamanho - 5,
                    tamanho / 2
            );

            g2.setColor(Color.WHITE);
            g2.fillOval(
                    tamanho / 2 - 8,
                    tamanho / 2 - 8,
                    16,
                    16
            );

            g2.setColor(new Color(38, 38, 45));
            g2.setStroke(new BasicStroke(3f));

            g2.drawOval(
                    tamanho / 2 - 8,
                    tamanho / 2 - 8,
                    16,
                    16
            );

            g2.dispose();
        }
    }

    private class EfeitoAtaque extends JPanel {

        private final String nomeGolpe;
        private int quadro;

        public EfeitoAtaque(String nomeGolpe) {
            this.nomeGolpe = nomeGolpe == null ? "Ataque" : nomeGolpe;
            this.quadro = 0;
            setOpaque(false);
            setVisible(false);
        }

        public void setQuadro(int quadro) {
            this.quadro = quadro;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            String golpe = nomeGolpe.toLowerCase();

            if (golpe.contains("brasa")) {
                desenharBrasa(g2);
            } else if (golpe.contains("lança") || golpe.contains("lanca")) {
                desenharLancaChamas(g2);
            } else if (golpe.contains("jato")) {
                desenharJatoAgua(g2);
            } else if (golpe.contains("hidro")) {
                desenharHidroBomba(g2);
            } else if (golpe.contains("folha")) {
                desenharFolhas(g2);
            } else if (golpe.contains("vinha")) {
                desenharVinhas(g2);
            } else if (golpe.contains("investida")) {
                desenharInvestida(g2);
            } else {
                desenharImpacto(g2);
            }

            g2.dispose();
        }

        private void desenharBrasa(Graphics2D g2) {
            double pulso = 1.0 + Math.sin(quadro * 0.35) * 0.12;
            int tamanho = (int) (58 * pulso);
            int x = 55 - tamanho / 2;
            int y = 55 - tamanho / 2;

            g2.setColor(new Color(255, 80, 30, 90));
            g2.fillOval(x - 8, y - 8, tamanho + 16, tamanho + 16);

            g2.setColor(new Color(255, 145, 25));
            g2.fillOval(x, y, tamanho, tamanho);

            g2.setColor(new Color(255, 235, 110));
            g2.fillOval(x + tamanho / 4, y + tamanho / 5, tamanho / 2, tamanho / 2);

            g2.setColor(new Color(255, 70, 25));
            g2.fillOval(18, 25, 13, 13);
            g2.fillOval(82, 68, 10, 10);
        }

        private void desenharLancaChamas(Graphics2D g2) {
            int pulso = (int) (Math.sin(quadro * 0.45) * 7);

            g2.setColor(new Color(255, 65, 20, 80));
            g2.fillOval(5, 35 - pulso / 2, 100, 40 + pulso);

            g2.setColor(new Color(255, 120, 20));
            g2.fillOval(5, 40, 92, 30);
            g2.fillOval(25, 30, 60, 45);

            g2.setColor(new Color(255, 235, 100));
            g2.fillOval(20, 45, 70, 18);
        }

        private void desenharJatoAgua(Graphics2D g2) {
            g2.setColor(new Color(60, 170, 255, 90));
            g2.fillOval(5, 32, 100, 42);

            g2.setColor(new Color(90, 205, 255));
            g2.setStroke(new BasicStroke(8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawArc(2, 25, 100, 55, 200, 140);

            g2.setColor(new Color(225, 250, 255));
            g2.setStroke(new BasicStroke(4f));
            g2.drawLine(12, 55, 95, 55);

            g2.fillOval(20, 20, 9, 9);
            g2.fillOval(75, 78, 12, 12);
        }

        private void desenharHidroBomba(Graphics2D g2) {
            int pulso = (int) (Math.sin(quadro * 0.4) * 8);
            int tamanho = 65 + pulso;
            int x = 55 - tamanho / 2;
            int y = 55 - tamanho / 2;

            g2.setColor(new Color(30, 130, 255, 70));
            g2.fillOval(x - 12, y - 12, tamanho + 24, tamanho + 24);

            g2.setColor(new Color(45, 165, 255, 190));
            g2.fillOval(x, y, tamanho, tamanho);

            g2.setColor(new Color(210, 250, 255));
            g2.fillOval(x + 17, y + 12, tamanho / 3, tamanho / 3);

            g2.setColor(new Color(255, 255, 255, 150));
            g2.drawOval(x - 5, y - 5, tamanho + 10, tamanho + 10);
        }

        private void desenharFolhas(Graphics2D g2) {
            int deslocamento = (quadro % 4) * 4;
            desenharFolha(g2, 15 + deslocamento, 20, 34, 16, -25);
            desenharFolha(g2, 52 - deslocamento, 42, 38, 17, 30);
            desenharFolha(g2, 18 + deslocamento, 70, 32, 15, 18);
            desenharFolha(g2, 68 - deslocamento, 12, 27, 14, -45);
        }

        private void desenharFolha(Graphics2D g2, int x, int y, int w, int h, double angulo) {
            Graphics2D folha = (Graphics2D) g2.create();
            folha.rotate(Math.toRadians(angulo), x + w / 2.0, y + h / 2.0);
            folha.setColor(new Color(70, 190, 80, 210));
            folha.fillOval(x, y, w, h);
            folha.setColor(new Color(190, 245, 120, 220));
            folha.setStroke(new BasicStroke(2f));
            folha.drawLine(x + 5, y + h / 2, x + w - 5, y + h / 2);
            folha.dispose();
        }

        private void desenharVinhas(Graphics2D g2) {
            int movimento = (int) (Math.sin(quadro * 0.3) * 10);

            g2.setColor(new Color(55, 160, 70, 220));
            g2.setStroke(new BasicStroke(9f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            int[] xs = {10, 30, 52, 75, 100};
            int[] ys = {75, 45 + movimento, 70, 35 - movimento, 58};

            for (int i = 0; i < xs.length - 1; i++) {
                g2.drawLine(xs[i], ys[i], xs[i + 1], ys[i + 1]);
            }

            g2.setColor(new Color(130, 225, 90, 210));
            g2.setStroke(new BasicStroke(3f));
            g2.drawLine(10, 78, 98, 58);
        }

        private void desenharInvestida(Graphics2D g2) {
            int movimento = quadro % 18;

            g2.setColor(new Color(255, 255, 255, 170));
            g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            g2.drawLine(15, 25 + movimento, 70, 45 + movimento);
            g2.drawLine(8, 50 + movimento, 62, 55 + movimento);
            g2.drawLine(20, 75 - movimento, 70, 65 - movimento);

            g2.setColor(new Color(255, 220, 90, 190));
            g2.fillOval(70, 40, 30, 30);
        }

        private void desenharImpacto(Graphics2D g2) {
            g2.setColor(new Color(255, 230, 90, 210));
            g2.setStroke(new BasicStroke(6f));

            for (int i = 0; i < 8; i++) {
                double angulo = Math.toRadians(i * 45 + quadro * 5);
                int x1 = 55 + (int) (15 * Math.cos(angulo));
                int y1 = 55 + (int) (15 * Math.sin(angulo));
                int x2 = 55 + (int) (48 * Math.cos(angulo));
                int y2 = 55 + (int) (48 * Math.sin(angulo));
                g2.drawLine(x1, y1, x2, y2);
            }
        }

    }

        public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            InterfacePokemon tela = new InterfacePokemon();
            tela.setVisible(true);
        });
    }
}
