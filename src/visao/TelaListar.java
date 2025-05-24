package visao;

import modelo.Aluno;
import modelo.Curso;
import modelo.Disciplina;
import modelo.Matricula;
import persistencia.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class TelaListar extends JInternalFrame{
    private JTable tabelaItens;
    private JButton apagarButton;
    private JPanel panelLista;
    private JTabbedPane abasPrincipais;
    private JPanel panelAlunos;
    private JPanel panelCursos;
    private JPanel panelDisciplina;
    private JPanel panelMatricula;
    private JTable tableAluno;
    private JTable tableCurso;
    private JTable tableDisciplina;
    private JTable tableMatricula;
    private JButton atualizarAlunosButton;
    private JButton atualizarCursosButton;
    private JButton atualizarDisciplinasButton;
    private JButton atualizarMatriculasButton;

    public TelaListar() {
        setContentPane(panelLista);
        setTitle("Listar BDs");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(400,300);

        DefaultTableModel modelA = new DefaultTableModel();
        modelA.addColumn("Codigo Aluno");
        modelA.addColumn("Nome");
        modelA.addColumn("Data de Nascimento");
        modelA.addColumn("CPF");
        modelA.addColumn("Telefone");
        tableAluno.setModel(modelA);

        DefaultTableModel modelC = new DefaultTableModel();
        modelC.addColumn("Codigo Curso");
        modelC.addColumn("Nome");
        modelC.addColumn("Duração");
        tableCurso.setModel(modelC);

        DefaultTableModel modelD = new DefaultTableModel();
        modelD.addColumn("Codigo Disciplina");
        modelD.addColumn("Nome");
        modelD.addColumn("Carga Horária");
        modelD.addColumn("Codigo Curso");
        tableDisciplina.setModel(modelD);

        DefaultTableModel modelM = new DefaultTableModel();
        modelM.addColumn("Numero Matricula");
        modelM.addColumn("Codigo Aluno");
        modelM.addColumn("Codigo Disciplina");
        modelM.addColumn("Semestre");
        modelM.addColumn("Ano");
        tableMatricula.setModel(modelM);

        // açoes botao atualizar de cada aba
        atualizarAlunosButton.addActionListener(e -> carregarAlunos());
        carregarAlunos();

        atualizarCursosButton.addActionListener(e -> carregarCursos());
        carregarCursos();

        atualizarDisciplinasButton.addActionListener(e -> carregarDisciplinas());
        carregarDisciplinas();

        atualizarMatriculasButton.addActionListener(e -> carregarMatriculas());
        carregarMatriculas();

        // carrega automaticamente qnd e clicado
        abasPrincipais.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = abasPrincipais.getSelectedIndex();
                String tituloAbaSelecionada = abasPrincipais.getTitleAt(selectedIndex);

                System.out.println("Aba selecionada: " + tituloAbaSelecionada + " (Índice: " + selectedIndex + ")");

                if ("Alunos".equals(tituloAbaSelecionada)) {
                    carregarAlunos();
                } else if ("Cursos".equals(tituloAbaSelecionada)) {
                    carregarCursos();
                } else if ("Disciplinas".equals(tituloAbaSelecionada)){
                    carregarDisciplinas();
                } else if ("Matriculas".equals(tituloAbaSelecionada)){
                    carregarMatriculas();
                }
            }
        });
    }

    // metodos de carregar dados
    private void carregarAlunos() {
        System.out.println("Tentando carregar dados dos alunos...");
        DefaultTableModel model = (DefaultTableModel) tableAluno.getModel();
        model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        Conexao c = null; // Sua classe de conexão
        try {
            c = new Conexao();
            JDBCAluno jdbcAluno = new JDBCAluno(c.getConexao()); // Assumindo que seu DAO precisa da conexão
            List<Aluno> alunos = jdbcAluno.listarAlunos(); // Ou o nome do seu método de listagem

            if (alunos.isEmpty()) {
                System.out.println("Nenhum aluno encontrado.");
                // Você pode adicionar uma linha à tabela indicando "Nenhum aluno encontrado"
                // model.addRow(new Object[]{"Nenhum aluno para exibir", "", "", ""});
            } else {
                for (Aluno aluno : alunos) {
                    model.addRow(new Object[]{
                            aluno.getCodigoAluno(), // Adapte para os getters reais da sua classe Aluno
                            aluno.getNomeAluno(),
                            aluno.getDataNasc(),
                            aluno.getCpf(),
                            aluno.getTelefone()
                    });
                }
                System.out.println(alunos.size() + " alunos carregados na tabela.");
            }
        } finally {
            if (c != null) {
                c.fecharConexao(); // Sempre feche a conexão
            }
        }
    }

    private void carregarCursos() {
        System.out.println("Tentando carregar dados dos cursos...");
        DefaultTableModel model = (DefaultTableModel) tableCurso.getModel();
        model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        Conexao c = null; // Sua classe de conexão
        try {
            c = new Conexao();
            JDBCCurso jdbcCurso = new JDBCCurso(c.getConexao()); // Assumindo que seu DAO precisa da conexão
            List<Curso> cursos = jdbcCurso.listarCursos(); // Ou o nome do seu método de listagem

            if (cursos.isEmpty()) {
                System.out.println("Nenhum aluno encontrado.");
                // Você pode adicionar uma linha à tabela indicando "Nenhum aluno encontrado"
                // model.addRow(new Object[]{"Nenhum aluno para exibir", "", "", ""});
            } else {
                for (Curso curso : cursos) {
                    model.addRow(new Object[]{
                            curso.getCodigoCurso(),
                            curso.getNomeCurso(),
                            curso.getDuracao()
                    });
                }
                System.out.println(cursos.size() + " cursos carregados na tabela.");
            }
        } finally {
            if (c != null) {
                c.fecharConexao(); // Sempre feche a conexão
            }
        }
    }

    private void carregarDisciplinas() {
        System.out.println("Tentando carregar dados das disciplinas...");
        DefaultTableModel model = (DefaultTableModel) tableDisciplina.getModel();
        model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        Conexao c = null; // Sua classe de conexão
        try {
            c = new Conexao();
            JDBCDisciplina jdbcDisciplina = new JDBCDisciplina(c.getConexao()); // Assumindo que seu DAO precisa da conexão
            List<Disciplina> disciplinas = jdbcDisciplina.listarDisciplinas(); // Ou o nome do seu método de listagem

            if (disciplinas.isEmpty()) {
                System.out.println("Nenhum aluno encontrado.");
                // Você pode adicionar uma linha à tabela indicando "Nenhum aluno encontrado"
                // model.addRow(new Object[]{"Nenhum aluno para exibir", "", "", ""});
            } else {
                for (Disciplina disciplina : disciplinas) {
                    model.addRow(new Object[]{
                            disciplina.getCodigoDisciplina(),
                            disciplina.getNomeDisciplina(),
                            disciplina.getCargaHoraria(),
                            disciplina.getCodCurso()
                    });
                }
                System.out.println(disciplinas.size() + " disciplinas carregadas na tabela.");
            }
        } finally {
            if (c != null) {
                c.fecharConexao(); // Sempre feche a conexão
            }
        }
    }    private void carregarMatriculas() {
        System.out.println("Tentando carregar dados das matriculas...");
        DefaultTableModel model = (DefaultTableModel) tableMatricula.getModel();
        model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        Conexao c = null; // Sua classe de conexão
        try {
            c = new Conexao();
            JDBCMatricula jdbcMatricula = new JDBCMatricula(c.getConexao()); // Assumindo que seu DAO precisa da conexão
            List<Matricula> matriculas = jdbcMatricula.listarMatriculas(); // Ou o nome do seu método de listagem

            if (matriculas.isEmpty()) {
                System.out.println("Nenhuma matricula encontrado.");
                // Você pode adicionar uma linha à tabela indicando "Nenhum aluno encontrado"
                // model.addRow(new Object[]{"Nenhum aluno para exibir", "", "", ""});
            } else {
                for (Matricula matricula : matriculas) {
                    model.addRow(new Object[]{
                            matricula.getNumMatricula(),
                            matricula.getCodAluno(),
                            matricula.getCodDisciplina(),
                            matricula.getSemestre(),
                            matricula.getAno()
                    });
                }
                System.out.println(matriculas.size() + " cursos carregados na tabela.");
            }
        } finally {
            if (c != null) {
                c.fecharConexao(); // Sempre feche a conexão
            }
        }
    }
}
