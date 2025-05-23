package visao;

import modelo.Aluno;
import modelo.Curso;
import persistencia.Conexao;
import persistencia.JDBCAluno;
import persistencia.JDBCCurso;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaInserirCurso extends JInternalFrame {
    private JPanel panelInserirC;
    private JTextField campoNome;
    private JTextField campoDuracao;
    private JButton salvarButton;

    public TelaInserirCurso() {
        setContentPane(panelInserirC);
        setTitle("Inserção de Dados de Curso");
        setClosable(true); // permite fechar
        setIconifiable(true); // permite minimizar
        setMaximizable(true); // permite maximizar
        setResizable(true); // permite redimensionar
        setSize(400, 300);

        // açao do botao salvar
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String duracaoStr = campoDuracao.getText();
                int codigo = 0;

                if (nome.isEmpty() || duracaoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaInserirCurso.this, // this refere-se ao JInternalFrame
                            "Todos os campos devem ser preenchidos!",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Interrompe a execução se houver erro
                }

                // conversao de duraçao para int
                int duracao;

                try {
                    duracao = Integer.parseInt(duracaoStr);
                    if (duracao <= 0) { // Validação adicional
                        JOptionPane.showMessageDialog(TelaInserirCurso.this,
                                "A duração deve ser um número inteiro positivo.",
                                "Erro de Validação",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TelaInserirCurso.this,
                            "Quantidade inválida! Use números inteiros.",
                            "Erro de Conversão",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Curso curso = new Curso(codigo, nome, duracao); // Adapte conforme seu construtor
                System.out.println("Objeto Aluno criado: " + curso.getNomeCurso());

                Conexao conexao = null;
                try {
                    conexao = new Conexao(); // Crie ou obtenha sua instância de conexão [00:03:11]
                    JDBCCurso jdbcCurso = new JDBCCurso(conexao.getConexao()); // Sua classe DAO [00:03:29]

                    jdbcCurso.inserirCurso(curso);
                    System.out.println("Curso inserido no banco.");

                    JOptionPane.showMessageDialog(TelaInserirCurso.this,
                            "Curso salvo com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    // limpar os campos do form
                    campoNome.setText("");
                    campoDuracao.setText("");
                    System.out.println("Campos limpos.");

                    campoNome.requestFocusInWindow(); // solicita foco para o campoNome
                    System.out.println("Foco em campoNome.");

                } catch (Exception exGeral) {
                    // Tratar outras exceções gerais
                    JOptionPane.showMessageDialog(TelaInserirCurso.this,
                            "Ocorreu um erro inesperado: " + exGeral.getMessage(),
                            "Erro Geral",
                            JOptionPane.ERROR_MESSAGE);
                    exGeral.printStackTrace(); // Importante para depuração no console
                } finally {
                    if (conexao != null) {
                        conexao.fecharConexao(); // Fechar a conexão [00:04:12]
                        System.out.println("Conexão com o banco fechada.");
                    }
                }
            }
        });
    }
}
