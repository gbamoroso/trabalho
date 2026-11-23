package modelo;

import interfaces.Bonificavel;
import excecoes.PontuacaoInsuficienteException;
import java.util.Objects;

public class MembroClube implements Comparable<MembroClube>, Bonificavel {
    private int id;
    private String nome;
    private int pontos;

    public MembroClube(int id, String nome, int pontos) {
        this.id = id;
        this.nome = nome;
        this.pontos = pontos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getPontos() { return pontos; }
    public void setPontos(int pontos) { this.pontos = pontos; }

    public void acumularPontos(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade para acumular deve ser maior que zero.");
        }
        if (quantidade > Integer.MAX_VALUE - pontos) {
            throw new IllegalArgumentException("O saldo ultrapassaria o limite permitido.");
        }
        pontos += quantidade;
    }

    @Override
    public void resgatarPontos(int quantidade) throws PontuacaoInsuficienteException {
        if (quantidade <= 0) {
            throw new PontuacaoInsuficienteException("Informe uma quantidade de pontos maior que zero.");
        }
        if (!possuiPontos(pontos, quantidade)) {
            throw new PontuacaoInsuficienteException("O membro não possui pontos suficientes.");
        }
        pontos -= quantidade;
    }

    @Override
    public int compareTo(MembroClube outro) {
        return Integer.compare(outro.pontos, this.pontos); // maior pontuação primeiro
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MembroClube)) return false;
        MembroClube outro = (MembroClube) obj;
        return id == outro.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Pontos: " + pontos;
    }
}
