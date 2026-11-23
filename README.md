# Clube de Assinaturas e Fidelidade

**Disciplina:** Programação Orientada a Objetos  
**Tema escolhido:** 07 — Clube de Assinaturas e Fidelidade  
**Aluno:** Gabriel Ricete Amoroso  
**Turma:** 3º semestre de ADS  
**Modalidade pretendida:** individual — confirmar autorização com o professor, pois o enunciado original determina trabalho em dupla.

## Descrição
Aplicação desktop em Java Swing para cadastrar membros de um clube, consultar seus dados, acumular pontos, resgatar pontos e visualizar um ranking ordenado pela pontuação. Os dados ficam somente na memória RAM e são perdidos quando o programa é encerrado, conforme solicitado no enunciado.

## Funcionalidades
- Cadastro de membro com ID, nome e saldo inicial de pontos.
- Pesquisa de membro pelo ID.
- Acúmulo de pontos (quantidade positiva).
- Resgate de pontos (digite uma quantidade negativa).
- Ordenação do ranking da maior para a menor pontuação.
- Validação de dados e mensagens com `JOptionPane`.

## Organização das classes
- `modelo/MembroClube.java`: entidade principal; implementa `Comparable<MembroClube>` e `Bonificavel`, além de `equals`, `hashCode` e `toString`.
- `interfaces/Bonificavel.java`: contrato para resgate de pontos e método `default` de verificação.
- `excecoes/PontuacaoInsuficienteException.java`: exceção verificada personalizada.
- `visao/TelaClube.java`: interface Swing, eventos e lista `ArrayList` mantida em memória.
- `Principal.java`: ponto de entrada do programa.

## Como executar
É necessário ter o JDK instalado.

### Pelo terminal
Abra o terminal na pasta `src` e execute:

```bash
javac -encoding UTF-8 -d ../out Principal.java modelo/*.java interfaces/*.java excecoes/*.java visao/*.java
java -cp ../out Principal
```

### Pelo NetBeans ou Eclipse
1. Crie/abra um projeto Java.
2. Copie a pasta `src` para o projeto, mantendo os pacotes.
3. Execute a classe `Principal`.

## Como usar
1. Para cadastrar, preencha ID, nome e pontos iniciais e clique em **Cadastrar**.
2. Para pesquisar, informe o ID e clique em **Pesquisar**.
3. Para acumular pontos, informe o ID e uma quantidade positiva em **Pontos / quantidade** e clique em **Processar operação**.
4. Para resgatar, informe o ID e uma quantidade negativa (por exemplo, `-50`) e clique em **Processar operação**.
5. Clique em **Ordenar / Ranking** para ordenar pela maior pontuação.

## Requisitos do enunciado atendidos
- Interface gráfica com `javax.swing`.
- Eventos com `ActionListener` e fechamento com `WindowListener`.
- `ArrayList<MembroClube>` em memória.
- `Comparable`, `Collections.sort`, `equals` e `hashCode`.
- Interface própria e polimorfismo (`Bonificavel`).
- Checked Exception própria (`PontuacaoInsuficienteException`).
- Tratamento de `NumberFormatException` e mensagens com `JOptionPane`.
3. Pedido para montar o projeto.
   - Resultado: criação das classes Java, interface Swing, regras de pontos e README.
4. Pedido para revisar o projeto em busca de erros.
   - Resultado: compilação do código, revisão das classes e correção de validações de pontuação e documentação.
