package visao;

import modelo.Aluno;
import persistencia.Conexao;
import persistencia.JDBCAluno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TelaInserirAluno extends JInternalFrame{
    private JPanel panelInserirA;
    private JTextField campoNome;
    private JTextField campoData;
    private JTextField campoCPF;
    private JTextField campoTelefone;
    private JButton salvarButton;

    public TelaInserirAluno() {
        setContentPane(panelInserirA);
        setTitle("Inserção de Dados de Aluno");
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
                String dataStr = campoData.getText();
                String cpf = campoCPF.getText();
                String telefone = campoTelefone.getText();

                if (nome.isEmpty() || dataStr.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaInserirAluno.this, // this refere-se ao JInternalFrame
                            "Todos os campos devem ser preenchidos!",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Interrompe a execução se houver erro
                }

                // conversao de date para string
                java.sql.Date dataConvertida = null;

                String formatoEsperado = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(formatoEsperado);

                try {
                    if (dataStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, // ou o componente pai
                                "O campo data não pode estar vazio.",
                                "Erro de Validação",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // parse para java.util.Date
                        java.util.Date utilDate = sdf.parse(dataStr);

                        String dataFormatadaNovamente = sdf.format(utilDate);
                        if (!dataStr.equals(dataFormatadaNovamente) && !sdf.isLenient()) { // verifica se a re-formatação bate com a original apenas se não for leniente ou para casos específicos
                            JOptionPane.showMessageDialog(null,
                                    "A data '" + dataStr + "' não parece ser uma data válida no formato " + formatoEsperado + ".",
                                    "Erro de Validação de Data",
                                    JOptionPane.ERROR_MESSAGE);
                            // return;
                        } else {
                            // converte java.util.Date para java.sql.Date
                            dataConvertida = new java.sql.Date(utilDate.getTime());
                        }
                    }

                } catch (ParseException ex) {
                    // conversao falha
                    JOptionPane.showMessageDialog(null, // ou o componente pai
                            "Data inválida! Por favor, use o formato " + formatoEsperado + " (ex: 23/05/2025).",
                            "Erro de Conversão de Data",
                            JOptionPane.ERROR_MESSAGE);
                    // dataSQLConvertida permanecerá null
                }

                if (dataConvertida != null) {
                    System.out.println("Data SQL pronta para ser usada: " + dataConvertida);
                }

                Aluno aluno = new Aluno(nome, dataConvertida, cpf, telefone); // Adapte conforme seu construtor
                System.out.println("Objeto Aluno criado: " + aluno.getNomeAluno());

                Conexao conexao = null;
                try {
                    conexao = new Conexao(); // Crie ou obtenha sua instância de conexão [00:03:11]
                    JDBCAluno jdbcAluno = new JDBCAluno(conexao.getConexao()); // Sua classe DAO [00:03:29]

                    jdbcAluno.inserirAluno(aluno);
                    System.out.println("Aluno inserido no banco.");

                    JOptionPane.showMessageDialog(TelaInserirAluno.this,
                            "Aluno salvo com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    // limpar os campos do form
                    campoNome.setText("");
                    campoData.setText("");
                    campoCPF.setText("");
                    campoTelefone.setText("");
                    System.out.println("Campos limpos.");

                    campoNome.requestFocusInWindow(); // solicita foco para o campoNome
                    System.out.println("Foco em campoNome.");

                } catch (Exception exGeral) {
                    // Tratar outras exceções gerais
                    JOptionPane.showMessageDialog(TelaInserirAluno.this,
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
