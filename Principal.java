import visao.TelaClube;
import javax.swing.SwingUtilities;

public class Principal {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaClube tela = new TelaClube();
            tela.setVisible(true);
        });
    }
}
