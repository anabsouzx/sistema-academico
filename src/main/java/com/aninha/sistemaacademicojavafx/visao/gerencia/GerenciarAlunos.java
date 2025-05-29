package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.Conexao;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarAluno;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class GerenciarAlunos implements Initializable {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableColumn<Aluno, Integer> colunaCodigoA;

    @FXML
    private TableColumn<Aluno, String> colunaCpf;

    @FXML
    private TableColumn<Aluno, Date> colunaData;

    @FXML
    private TableColumn<Aluno, String> colunaNomeA;

    @FXML
    private TableColumn<Aluno, String> colunaTel;

    @FXML
    private TableView<Aluno> tableAlunos;

    @FXML
    void editarAluno(ActionEvent event) throws IOException {
        Aluno alunoSelecionado = tableAlunos.getSelectionModel().getSelectedItem();
        if (alunoSelecionado == null) { // alerta caso aluno n seja selecionado
            mostrarAlerta("Seleção Necessária", "Por favor, selecione um aluno para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit/editar-aluno.fxml")); // carrega a tela de ediçao
            Parent telaEdicao = loader.load();

            EditarAluno controllerEdicao = loader.getController();
            controllerEdicao.setAlunoParaEditar(alunoSelecionado, this); // passa o aluno e a referência deste controller

            painelPrincipal.setCenter(telaEdicao);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao Carregar Tela", "Não foi possível carregar a tela de edição.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void excluirAluno(ActionEvent event) throws IOException{
        System.out.println("exclusao");
        Aluno alunoSelecionado = tableAlunos.getSelectionModel().getSelectedItem();

        if (alunoSelecionado == null) { // alerta caso nao seja selecionado um aluno
            mostrarAlerta("Seleção Necessária", "Por favor, selecione um aluno para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // prevençao de erros
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir Aluno: " + alunoSelecionado.getNomeAluno());
        confirmacao.setContentText("Tem certeza que deseja excluir este aluno permanentemente?");

        Optional<ButtonType> resultado = confirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Conexao c = null;
            try {
                c = new Conexao();
                DAOAluno daoAluno = new DAOAluno(c.getConexao());
                if (daoAluno.excluirAluno(alunoSelecionado.getCodigoAluno())) {
                    mostrarAlerta("Sucesso", "Aluno excluído com sucesso!", Alert.AlertType.INFORMATION);
                    carregarDados(); // Atualiza a tabela
                } else {
                    // O DAOAluno já pode ter mostrado um alerta mais específico
                    // mostrarAlerta("Erro", "Não foi possível excluir o aluno.", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro Crítico", "Ocorreu um erro inesperado ao tentar excluir.", Alert.AlertType.ERROR);
            } finally {
                if (c != null) {
                    c.fecharConexao();
                }
            }
        }
    }

    @FXML
    void inserirAluno(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-aluno.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigoA.setCellValueFactory(new PropertyValueFactory<>("codigoAluno"));
        colunaNomeA.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        carregarDados();
    }

    public void carregarDados() {
        Conexao c = null;
        c = new Conexao();
        DAOAluno daoAluno = new DAOAluno(c.getConexao());

        ObservableList<Aluno> listaDeUsuarios = daoAluno.listarAlunos();
        tableAlunos.setItems(listaDeUsuarios);
    }

    public void limparPainelCentral() {
        painelPrincipal.setCenter(null); // Ou coloque um conteúdo padrão
    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }

    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }
}
