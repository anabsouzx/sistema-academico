package com.aninha.sistemaacademicojavafx.visao.gerencia;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarAluno;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
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

    private DAOAluno daoAluno;

    public GerenciarAlunos() {
        this.daoAluno = new DAOAluno(); // Instancia o DAO
    }

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

    }

    @FXML
    void inserirAluno(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-aluno.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colunaCodigoA.setCellValueFactory(new PropertyValueFactory<>("codigoAluno"));
        colunaNomeA.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataDeNascimento"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
        carregarDados();
    }

    public void carregarDados() {
        ObservableList<Aluno> listaDeAlunos = daoAluno.listarAlunos(); // DAO agora usa lista
        tableAlunos.setItems(listaDeAlunos);
        if(listaDeAlunos.isEmpty()){
            Aluno a = new Aluno("viado", LocalDate.of(1995, 6, 2),"12345678900","11999999999");
            daoAluno.adicionar(a);
        }
        tableAlunos.refresh();
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
