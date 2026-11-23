package visao;

import modelo.MembroClube;
import excecoes.PontuacaoInsuficienteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TelaClube extends JFrame implements ActionListener, WindowListener {
    private final List<MembroClube> membros = new ArrayList<>();

    private final JTextField campoId = new JTextField(10);
    private final JTextField campoNome = new JTextField(18);
    private final JTextField campoPontos = new JTextField(10);
    private final JTextArea areaRelatorio = new JTextArea(12, 42);

    private final JButton botaoCadastrar = new JButton("Cadastrar");
    private final JButton botaoPesquisar = new JButton("Pesquisar");
    private final JButton botaoProcessar = new JButton("Processar operação");
    private final JButton botaoOrdenar = new JButton("Ordenar / Ranking");
    private final JButton botaoSair = new JButton("Sair");

    public TelaClube() {
        super("Sistema de Clube de Fidelidade");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);

        JPanel painelCampos = new JPanel(new GridLayout(3, 2, 8, 8));
        painelCampos.setBorder(BorderFactory.createTitledBorder("Dados do membro"));
        painelCampos.add(new JLabel("ID:"));
        painelCampos.add(campoId);
        painelCampos.add(new JLabel("Nome:"));
        painelCampos.add(campoNome);
        painelCampos.add(new JLabel("Pontos / quantidade:"));
        painelCampos.add(campoPontos);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        for (JButton botao : new JButton[]{botaoCadastrar, botaoPesquisar,
                botaoProcessar, botaoOrdenar, botaoSair}) {
            botao.addActionListener(this);
            painelBotoes.add(botao);
        }

        areaRelatorio.setEditable(false);
        areaRelatorio.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane rolagem = new JScrollPane(areaRelatorio);

        setLayout(new BorderLayout(10, 10));
        add(painelCampos, BorderLayout.NORTH);
        add(rolagem, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        setSize(620, 430);
        setLocationRelativeTo(null);
        atualizarRelatorio();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origem = e.getSource();

        try {
            if (origem == botaoCadastrar) {
                cadastrar();
            } else if (origem == botaoPesquisar) {
                pesquisar();
            } else if (origem == botaoProcessar) {
                processarOperacao();
            } else if (origem == botaoOrdenar) {
                ordenar();
            } else if (origem == botaoSair) {
                confirmarSaida();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID e pontos devem ser números inteiros.",
                    "Dados inválidos", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Operação inválida", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cadastrar() {
        int id = Integer.parseInt(campoId.getText().trim());
        String nome = campoNome.getText().trim();
        int pontos = Integer.parseInt(campoPontos.getText().trim());

        if (id <= 0 || nome.isEmpty() || pontos < 0) {
            JOptionPane.showMessageDialog(this,
                    "Informe ID positivo, nome e pontos iguais ou maiores que zero.");
            return;
        }

        MembroClube novo = new MembroClube(id, nome, pontos);
        if (membros.contains(novo)) {
            JOptionPane.showMessageDialog(this, "Já existe um membro com esse ID.");
            return;
        }

        membros.add(novo);
        JOptionPane.showMessageDialog(this, "Membro cadastrado com sucesso!");
        atualizarRelatorio();
        limparCampos();
    }

    private void pesquisar() {
        int id = Integer.parseInt(campoId.getText().trim());
        for (MembroClube membro : membros) {
            if (membro.getId() == id) {
                campoNome.setText(membro.getNome());
                campoPontos.setText(String.valueOf(membro.getPontos()));
                JOptionPane.showMessageDialog(this, membro.toString());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Membro não encontrado.");
    }

    // Digite o ID e a quantidade de pontos. Valores positivos acumulam;
    // valores negativos representam resgate.
    private void processarOperacao() {
        int id = Integer.parseInt(campoId.getText().trim());
        int quantidade = Integer.parseInt(campoPontos.getText().trim());

        MembroClube membro = buscarPorId(id);
        if (membro == null) {
            JOptionPane.showMessageDialog(this, "Membro não encontrado.");
            return;
        }

        if (quantidade > 0) {
            membro.acumularPontos(quantidade);
            JOptionPane.showMessageDialog(this, "Pontos acumulados com sucesso!");
        } else if (quantidade < 0) {
            if (quantidade == Integer.MIN_VALUE) {
                JOptionPane.showMessageDialog(this, "Quantidade de resgate inválida.");
                return;
            }
            try {
                membro.resgatarPontos(Math.abs(quantidade));
                JOptionPane.showMessageDialog(this, "Resgate realizado com sucesso!");
            } catch (PontuacaoInsuficienteException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Não foi possível resgatar", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Informe uma quantidade diferente de zero.");
        }
        atualizarRelatorio();
    }

    private MembroClube buscarPorId(int id) {
        for (MembroClube membro : membros) {
            if (membro.getId() == id) return membro;
        }
        return null;
    }

    private void ordenar() {
        Collections.sort(membros);
        atualizarRelatorio();
    }

    private void atualizarRelatorio() {
        StringBuilder texto = new StringBuilder("MEMBROS DO CLUBE\n");
        texto.append("-----------------------------------------------\n");
        if (membros.isEmpty()) {
            texto.append("Nenhum membro cadastrado.\n");
        } else {
            for (MembroClube membro : membros) {
                texto.append(membro).append("\n");
            }
        }
        areaRelatorio.setText(texto.toString());
    }

    private void limparCampos() {
        campoId.setText("");
        campoNome.setText("");
        campoPontos.setText("");
        campoId.requestFocus();
    }

    private void confirmarSaida() {
        int resposta = JOptionPane.showConfirmDialog(this,
                "Deseja realmente sair?", "Confirmar saída",
                JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    @Override public void windowClosing(WindowEvent e) { confirmarSaida(); }
    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
}
