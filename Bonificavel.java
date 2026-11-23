package interfaces;

import excecoes.PontuacaoInsuficienteException;

public interface Bonificavel {
    void resgatarPontos(int pontos) throws PontuacaoInsuficienteException;

    default boolean possuiPontos(int saldo, int necessario) {
        return saldo >= necessario;
    }
}
