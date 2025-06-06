// Define o pacote onde está localizada a classe
package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

// Importa a classe de acesso a dados para Aluno
import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
// Importa a classe de modelo Aluno
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
// Importa o controlador da tela de gerenciamento de alunos
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarAlunos;
// Importa classes do JavaFX necessárias para eventos e interface gráfica
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

// Importa classes para manipulação de datas
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Classe responsável por editar os dados de um aluno
public class EditarAluno {

    // Campos de texto definidos no FXML
    @FXML
    private TextField txtCpf;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtNomeA;
    @FXML
    private TextField txtTel;

    // Objeto que representa o aluno que será editado
    private Aluno alunoParaEditar;
    // Referência ao controlador da tela de gerenciamento de alunos
    private GerenciarAlunos controllerGerenciador;
    // Objeto de acesso a dados do aluno
    private DAOAluno daoAluno;

    // Formatador de datas no padrão brasileiro
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Construtor que inicializa o DAO
    public EditarAluno(){
        this.daoAluno = new DAOAluno();
    }

    // Método chamado para definir o aluno que será editado e o controlador que o chamou
    public void setAlunoParaEditar(Aluno aluno, GerenciarAlunos controllerGerenciador) {
        this.alunoParaEditar = aluno;
        this.controllerGerenciador = controllerGerenciador;
        popularCampos(); // Preenche os campos com os dados do aluno
    }

    // Preenche os campos do formulário com os dados do aluno selecionado
    private void popularCampos() {
        if (alunoParaEditar != null) {
            txtNomeA.setText(alunoParaEditar.getNome());
            if (alunoParaEditar.getDataDeNascimento() != null) {
                txtData.setText(alunoParaEditar.getDataDeNascimento().format(dateFormatter)); // Formata a data para exibição
            } else {
                txtData.setText("");
            }
            txtCpf.setText(alunoParaEditar.getCpf());
            txtTel.setText(alunoParaEditar.getTelefone());
        }
    }

    // Método chamado quando o botão "Atualizar" é clicado
    @FXML
    void atualizaAlunoAction(ActionEvent event) {
        // Recupera os dados dos campos de texto
        String nome = txtNomeA.getText();
        String dataStr = txtData.getText();
        String cpf = txtCpf.getText();
        String tel = txtTel.getText();

        // Validação: verifica se algum campo está vazio
        if (nome.isEmpty() || dataStr.isEmpty() || cpf.isEmpty() || tel.isEmpty()) {
            mostrarAlerta("Campos Vazios", "Todos os campos precisam ser preenchidos.", Alert.AlertType.WARNING);
            return;
        }

        // Tenta converter a data inserida para LocalDate
        LocalDate dataDeNascimento = null;
        try {
            dataDeNascimento = LocalDate.parse(dataStr, dateFormatter);
        } catch (DateTimeParseException e) {
            mostrarAlerta("Data Inválida", "O formato da data deve ser dd/MM/yyyy.", Alert.AlertType.ERROR);
            txtData.requestFocus(); // Foca no campo de data
            return;
        }

        // Atualiza os dados do objeto aluno com os novos valores inseridos
        alunoParaEditar.setNome(nome);
        alunoParaEditar.setDataDeNascimento(dataDeNascimento);
        alunoParaEditar.setCpf(cpf);
        alunoParaEditar.setTelefone(tel);

        // Chama o método do DAO para atualizar o aluno no banco de dados
        if (daoAluno.atualizarAluno(alunoParaEditar)) {
            // Mostra mensagem de sucesso
            mostrarAlerta("Sucesso", "Aluno atualizado com sucesso!", Alert.AlertType.INFORMATION);
            if (controllerGerenciador != null) {
                controllerGerenciador.carregarDados(); // Atualiza a tabela na tela principal
            }
            fecharTelaDeEdicao(); // Fecha a tela atual
        } else {
            // Caso o aluno não seja encontrado ou atualização falhe
            mostrarAlerta("Erro", "Não foi possível atualizar o aluno. Aluno não encontrado.", Alert.AlertType.ERROR);
        }
    }

    // Método chamado quando o botão "Cancelar" é clicado
    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTelaDeEdicao(); // Fecha a tela de edição
    }

    // Fecha a tela de edição chamando método no controlador principal
    private void fecharTelaDeEdicao() {
        if (controllerGerenciador != null) {
            controllerGerenciador.limparPainelCentral(); // Método que limpa o painel principal
        }
    }

    // Exibe uma mensagem de alerta com título, cabeçalho e tipo
    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait(); // Exibe e aguarda o usuário fechar
    }
}
