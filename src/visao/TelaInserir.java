package visao;

import javax.swing.*;

public class TelaInserir extends JInternalFrame{
    private JPanel panelInserir;
    private JTextField campoNome;
    private JTextField campoData;
    private JTextField campoCPF;
    private JTextField campoTelefone;
    private JButton salvarButton;

    public TelaInserir() {
        setContentPane(panelInserir);
        setTitle("Inserção de Dados");
        setClosable(true); // permite que feche
        setIconifiable(true); // permite minimizar
        setMaximizable(true); // permite maximizar
        setResizable(true); // permite redimensionar

        setSize(400, 300);
    }
}
