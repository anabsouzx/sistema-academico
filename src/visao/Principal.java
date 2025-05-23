package visao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame{
    private JPanel panel1;
    private JPanel panelBtn;
    private JDesktopPane desktop;
    private JButton inserirButton;
    private JButton listarButton;

    public Principal() {
        setContentPane(panel1); // painel de conteudo do jFrame

        setTitle("Sistema Acadêmico"); // titulo da janela
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // termina de rodar quando fecha
        setSize(800, 600); // tamanho da janela
        setLocationRelativeTo(null); // centralizar na tela
        setVisible(true); // torna visivel

        JMenuBar menuBar = new JMenuBar();

        JMenu menuCadastros = new JMenu("Cadastros");
        menuBar.add(menuCadastros);

        // Item de Menu para Inserir Aluno
        JMenuItem itemInserirAluno = new JMenuItem("Novo Aluno...");
        itemInserirAluno.addActionListener(e -> {
            abrirJanelaInterna(new TelaInserirAluno()); // Usando um método auxiliar
        });
        menuCadastros.add(itemInserirAluno);

        // Item de Menu para Inserir Curso
        JMenuItem itemInserirCurso = new JMenuItem("Novo Curso...");
        itemInserirCurso.addActionListener(e -> {
            abrirJanelaInterna(new TelaInserirCurso()); // Supondo que TelaInserirCurso exista
        });
        menuCadastros.add(itemInserirCurso);

        // Item de Menu para Inserir Disciplina
        JMenuItem itemInserirDisciplina = new JMenuItem("Nova Disciplina...");
        itemInserirDisciplina.addActionListener(e -> {
            abrirJanelaInterna(new TelaInserirDisciplina()); // Supondo que TelaInserirDisciplina exista
        });
        menuCadastros.add(itemInserirDisciplina);

        // Item de Menu para Inserir Matrícula
        JMenuItem itemInserirMatricula = new JMenuItem("Nova Matrícula...");
        itemInserirMatricula.addActionListener(e -> {
            abrirJanelaInterna(new TelaInserirMatricula()); // Supondo que TelaInserirMatricula exista
        });
        menuCadastros.add(itemInserirMatricula);

        setJMenuBar(menuBar);

        // açao do botao inserir aluno
        // provavelmente vou apagar para priorizar o menu
        inserirButton.setText("Novo Aluno");
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaInterna(new TelaInserirAluno());
            }
        });

        // açao do botao listar
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirJanelaInterna(new TelaListar());
            }
        });
    }

    // metodo de abrir janela interna
    private void abrirJanelaInterna(JInternalFrame janela) {
        if (desktop != null) {
            desktop.add(janela);
            System.out.println(janela.getClass().getSimpleName() + " adicionada ao desktop");
            try {
                janela.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                System.err.println("Erro ao selecionar " + janela.getClass().getSimpleName() + ": " + ex.getMessage());
                ex.printStackTrace();
            }
            janela.setVisible(true);
            System.out.println(janela.getClass().getSimpleName() + ".setVisible(true) chamado.");
            System.out.println(janela.getClass().getSimpleName() + " é visível? " + janela.isVisible());
            System.out.println("Tamanho de " + janela.getClass().getSimpleName() + ": " + janela.getSize());
        } else {
            System.err.println("ERRO: JDesktopPane (desktop) é nulo!");
        }
    }

    // rodar aplicaçao
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Principal frame = new Principal();
            frame.setVisible(true);
        });
    }
}
