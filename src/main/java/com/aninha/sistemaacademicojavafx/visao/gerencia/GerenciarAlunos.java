package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.Conexao;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
    void editarAluno(ActionEvent event) {

    }

    @FXML
    void excluirAluno(ActionEvent event) {

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

    private void carregarDados() {
        Conexao c = null;
        c = new Conexao();
        DAOAluno daoAluno = new DAOAluno(c.getConexao());

        ObservableList<Aluno> listaDeUsuarios = daoAluno.listarAlunos();
        tableAlunos.setItems(listaDeUsuarios);
    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }
}
