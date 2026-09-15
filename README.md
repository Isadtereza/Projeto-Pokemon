Projeto-Pokemon-Java
Jogo Pokémon desktop desenvolvido em Java com Swing, aplicando conceitos de POO, batalha por turnos, captura, Pokédex, cenários, animações e salvamento de progresso.

Pokemon Battle

Estrutura principal:
- src/main/java/br/com/pokemon: classes do sistema
- src/main/resources/imagens: imagens dos Pokemon
- src/test/java/br/com/pokemon: testes JUnit
- pom.xml: configuracao Maven

Para compilar e testar no ambiente da faculdade:
1. Abra o terminal na pasta do projeto.
2. Execute: mvn clean test
3. Execute: mvn clean package

Para executar a interface:
- Execute: mvn exec:java
- Ou rode a classe br.com.pokemon.TelaInicial pela IDE.
