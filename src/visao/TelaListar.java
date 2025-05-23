package visao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaListar extends JInternalFrame{
    private JTable tabelaItens;
    private JButton listarDadosButton;
    private JButton apagarButton;
    private JPanel panelLista;

    public TelaListar() {
        setContentPane(panelLista);
        setTitle("Listar BD");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(400,300);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("Data de Nascimento");
        model.addColumn("CPF");
        model.addColumn("Telefone");
        tabelaItens.setModel(model);
    }
}
