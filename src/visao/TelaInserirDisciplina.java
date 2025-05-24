package visao;

import modelo.Disciplina;
import persistencia.Conexao;
import persistencia.JDBCDisciplina;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInserirDisciplina extends JInternalFrame {
    private JPanel panelInserirD;
    private JTextField nomeDisciplina;
    private JTextField codCurso;
    private JTextField cargaHoraria;
    private JButton salvarButton;

    public TelaInserirDisciplina() {
        setContentPane(panelInserirD);
        setTitle("Inserção de Dados da Disciplina");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(400, 300);

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeDisciplina.getText();
                String codCursoStr = codCurso.getText();
                String cargaHorariaStr = cargaHoraria.getText();
                int codigoDisciplina = 0;

                if (nome.isEmpty() || codCursoStr.isEmpty() || cargaHorariaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaInserirDisciplina.this,
                            "Todos os campos devem ser preenchidos!",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int codCursoInt;
                int cargaHorariaInt;

                try {
                    codCursoInt = Integer.parseInt(codCursoStr);
                    cargaHorariaInt = Integer.parseInt(cargaHorariaStr);
                    if (codCursoInt <= 0 || cargaHorariaInt <= 0) {
                        JOptionPane.showMessageDialog(TelaInserirDisciplina.this,
                                "Código do curso e carga horária devem ser positivos.",
                                "Erro de Validação",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TelaInserirDisciplina.this,
                            "Quantidade inválida! Use números inteiros.",
                            "Erro de Conversão",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Disciplina disciplina = new Disciplina(codigoDisciplina, nome, codCursoInt, cargaHorariaInt);
                System.out.println("Objeto Disciplina criado: " + disciplina.getNomeDisciplina());

                Conexao conexao = null;
                try {
                    conexao = new Conexao();
                    JDBCDisciplina jdbcDisciplina = new JDBCDisciplina(conexao.getConexao());

                    jdbcDisciplina.inserirDisciplina(disciplina); // Corrigido

                    System.out.println("Disciplina inserida no banco."); // Corrigido

                    JOptionPane.showMessageDialog(TelaInserirDisciplina.this,
                            "Disciplina salva com sucesso!", // Corrigido
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    nomeDisciplina.setText("");
                    codCurso.setText("");
                    cargaHoraria.setText("");
                    nomeDisciplina.requestFocusInWindow();

                } catch (Exception exGeral) {
                    JOptionPane.showMessageDialog(TelaInserirDisciplina.this, // Corrigido
                            "Ocorreu um erro inesperado: " + exGeral.getMessage(),
                            "Erro Geral",
                            JOptionPane.ERROR_MESSAGE);
                    exGeral.printStackTrace();
                } finally {
                    if (conexao != null) {
                        conexao.fecharConexao();
                        System.out.println("Conexão com o banco fechada.");
                    }
                }
            }
        });
    }
}

