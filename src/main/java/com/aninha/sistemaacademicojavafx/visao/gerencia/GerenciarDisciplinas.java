package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.Conexao;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAODisciplina;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOMatricula;
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
import java.util.ResourceBundle;

public class GerenciarDisciplinas implements Initializable {
    @FXML
    private TableColumn<Disciplina, Integer> colunaCargaH;

    @FXML
    private TableColumn<Disciplina, Integer> colunaCodigoC;

    @FXML
    private TableColumn<Disciplina, Integer> colunaCodigoD;

    @FXML
    private TableColumn<Disciplina, String> colunaNomeD;

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TableView<Disciplina> tableDisciplinas;

    @FXML
    void inserirDisciplina(ActionEvent event) throws IOException{
        carregarTela("insert/inserir-disciplina.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigoD.setCellValueFactory(new PropertyValueFactory<>("codigoDisciplina"));
        colunaNomeD.setCellValueFactory(new PropertyValueFactory<>("nomeDisciplina"));
        colunaCargaH.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        colunaCodigoC.setCellValueFactory(new PropertyValueFactory<>("codCurso"));

        carregarDados();
    }

    private void carregarDados() {
        Conexao c = null;
        c = new Conexao();
        DAODisciplina daoDisciplina = new DAODisciplina(c.getConexao());

        ObservableList<Disciplina> listaDisciplinas = daoDisciplina.listarDisciplinas();
        tableDisciplinas.setItems(listaDisciplinas);
    }

    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }
}
