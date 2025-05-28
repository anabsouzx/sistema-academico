package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.Conexao;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOCurso;
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

public class GerenciarCursos implements Initializable {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableColumn<Curso, Integer> colunacodigoCurso;

    @FXML
    private TableColumn<Curso, String> colunanomeCurso;

    @FXML
    private TableColumn<Curso, Integer> colunaduracao;

    @FXML
    private TableView<Curso> tableCurso;

    @FXML
    void editarCurso(ActionEvent event) {

    }

    @FXML
    void excluirCurso(ActionEvent event) {

    }

    @FXML
    void inserirCurso(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-curso.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunacodigoCurso.setCellValueFactory(new PropertyValueFactory<>("codigoCurso"));
        colunanomeCurso.setCellValueFactory(new PropertyValueFactory<>("nomeCurso"));
        colunaduracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));

        carregarDados();
    }

    private void carregarDados() {
        Conexao c = null;
        c = new Conexao();
        DAOCurso daoCurso = new DAOCurso(c.getConexao());

        ObservableList<Curso> listaDeCursos = daoCurso.listarCursos();
        tableCurso.setItems(listaDeCursos);
    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }
}
