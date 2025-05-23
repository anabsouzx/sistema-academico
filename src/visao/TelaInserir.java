package visao;

import modelo.Aluno;
import persistencia.Conexao;
import persistencia.JDBCAluno;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        // açao do botao salvar
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String dataStr = campoData.getText();
                String cpf = campoCPF.getText();
                String telefone = campoTelefone.getText();

                if (nome.isEmpty() || dataStr.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaInserir.this, // this refere-se ao JInternalFrame
                            "Todos os campos devem ser preenchidos!",
                            "Erro de Validação",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Interrompe a execução se houver erro
                }

                // conversao de date para string
                java.sql.Date dataConvertida = null; // É uma boa prática inicializar com null

                // 1. Defina o formato esperado da data
                String formatoEsperado = "dd/MM/yyyy"; // Ex: "23/05/2025"
                SimpleDateFormat sdf = new SimpleDateFormat(formatoEsperado);
                // sdf.setLenient(false); // Recomendado para validação rigorosa da entrada do usuário

                // 2. Tente converter (parse) a String para java.util.Date primeiro, e depois para java.sql.Date
                try {
                    if (dataStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, // ou o componente pai
                                "O campo data não pode estar vazio.",
                                "Erro de Validação",
                                JOptionPane.ERROR_MESSAGE);
                        // return;
                    } else {
                        // Passo A: Parse para java.util.Date
                        java.util.Date utilDate = sdf.parse(dataStr);

                        // Validação adicional (opcional, como discutido antes)
                        // Se sdf.setLenient(false) não foi usado, esta verificação é mais importante.
                        String dataFormatadaNovamente = sdf.format(utilDate);
                        if (!dataStr.equals(dataFormatadaNovamente) && !sdf.isLenient()) { // Verifica se a re-formatação bate com a original apenas se não for leniente ou para casos específicos
                            JOptionPane.showMessageDialog(null,
                                    "A data '" + dataStr + "' não parece ser uma data válida no formato " + formatoEsperado + ".",
                                    "Erro de Validação de Data",
                                    JOptionPane.ERROR_MESSAGE);
                            // return;
                        } else {
                            // Passo B: Converta java.util.Date para java.sql.Date
                            dataConvertida = new java.sql.Date(utilDate.getTime());
                            // Se chegou aqui, a conversão para java.sql.Date foi bem-sucedida!
                            // Exemplo: System.out.println("Data SQL convertida: " + dataSQLConvertida);
                        }
                    }

                } catch (ParseException ex) {
                    // Esta exceção ocorre se a String não puder ser convertida usando o formato especificado
                    JOptionPane.showMessageDialog(null, // ou o componente pai
                            "Data inválida! Por favor, use o formato " + formatoEsperado + " (ex: 23/05/2025).",
                            "Erro de Conversão de Data",
                            JOptionPane.ERROR_MESSAGE);
                    // dataSQLConvertida permanecerá null
                    // return;
                    // ex.printStackTrace();
                }

                // A partir daqui, se dataSQLConvertida não for null, a conversão foi bem-sucedida.
                if (dataConvertida != null) {
                    // Faça algo com a dataSQLConvertida (ex: salvar no banco de dados)
                    System.out.println("Data SQL pronta para ser usada: " + dataConvertida);
                    // Note que a saída de System.out.println(dataSQLConvertida) será no formato yyyy-MM-dd
                }

                Aluno aluno = new Aluno(nome, dataConvertida, cpf, telefone); // Adapte conforme seu construtor
                System.out.println("Objeto Aluno criado: " + aluno.getNomeAluno());

                Conexao conexao = null;
                try {
                    conexao = new Conexao(); // Crie ou obtenha sua instância de conexão [00:03:11]
                    JDBCAluno jdbcAluno = new JDBCAluno(conexao.getConexao()); // Sua classe DAO [00:03:29]

                    jdbcAluno.inserirAluno(aluno); // [00:04:00]
                    System.out.println("Produto inserido no banco.");

                    JOptionPane.showMessageDialog(TelaInserir.this,
                            "Produto salvo com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);

                    // 5. Limpeza dos campos do formulário [00:04:49, 00:05:35]
                    campoNome.setText("");
                    campoData.setText("");
                    campoCPF.setText("");
                    campoTelefone.setText("");
                    System.out.println("Campos limpos.");

                    // 6. Definição do foco no campo nome [00:06:02]
                    campoNome.requestFocusInWindow(); // Solicita foco para o campoNome
                    System.out.println("Foco em campoNome.");

                } catch (Exception exGeral) {
                    // Tratar outras exceções gerais
                    JOptionPane.showMessageDialog(TelaInserir.this,
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
