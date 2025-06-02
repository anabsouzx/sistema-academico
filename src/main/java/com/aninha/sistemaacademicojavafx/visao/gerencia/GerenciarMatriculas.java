
package com.aninha.sistemaacademicojavafx.visao.gerencia;

// Importações necessárias
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.controller.DAOMatricula;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarMatricula;
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
import java.util.ResourceBundle;

// Classe que controla a tela de gerenciamento de matrículas
public class GerenciarMatriculas implements Initializable {

    // Painel principal da interface onde telas serão carregadas dinamicamente
    @FXML
    private BorderPane painelPrincipal;

    // Tabela que exibe as matrículas
    @FXML
    private TableView<Matricula> tableMatriculas;

    // Colunas da tabela
    @FXML
    private TableColumn<Matricula, Integer> colunaNumero;

    @FXML
    private TableColumn<Matricula, Integer> colunaCodA;

    @FXML
    private TableColumn<Matricula, Integer> colunaCodDisc;

    @FXML
    private TableColumn<Matricula, Integer> colunaSemestre;

    @FXML
    private TableColumn<Matricula, Integer> colunaAno;

    // Objeto DAO para manipular os dados das matrículas
    private DAOMatricula daoMatricula;

    // Construtor: inicializa o DAO
    public GerenciarMatriculas() {
        this.daoMatricula = new DAOMatricula();
    }

    // Método executado quando o botão de "Editar" é clicado
    @FXML
    void editarMatricula(ActionEvent event) throws IOException {
        // Pega a matrícula selecionada na tabela
        Matricula matriculaSelecionada = tableMatriculas.getSelectionModel().getSelectedItem();

        // Verifica se alguma matrícula foi selecionada
        if (matriculaSelecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma matrícula para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Carrega a tela de edição da matrícula
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit/editar-matricula.fxml"));
            Parent telaEdicao = loader.load();

            // Pega o controller da tela de edição
            EditarMatricula controllerEdicao = loader.getController();

            // Passa a matrícula selecionada e o controller atual para a tela de edição
            controllerEdicao.setMatriculaParaEditar(matriculaSelecionada, this);

            // Troca o conteúdo do painel pela tela de edição
            painelPrincipal.setCenter(telaEdicao);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao Carregar Tela", "Não foi possível carregar a tela de edição.", Alert.AlertType.ERROR);
        }
    }

    // Método chamado ao clicar no botão "Excluir"
    @FXML
    void excluirMatricula(ActionEvent event) {
        // Pega a matrícula selecionada
        Matricula matriculaSelecionada = tableMatriculas.getSelectionModel().getSelectedItem();

        // Verifica se nada foi selecionado
        if (matriculaSelecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma matrícula para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // Exclui a matrícula e atualiza a tabela
        //daoMatricula.excluirMatricula(matriculaSelecionada);
        carregarDados();
    }

    // Método chamado ao clicar em "Inserir nova matrícula"
    @FXML
    void inserirMatricula(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-matricula.fxml");
    }

    // Método chamado automaticamente ao carregar o FXML (inicializa a tela)
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Associa as colunas da tabela às propriedades da classe Matricula
        colunaNumero.setCellValueFactory(new PropertyValueFactory<>("numMatricula"));
        colunaCodA.setCellValueFactory(new PropertyValueFactory<>("codigoAlunoProperty")); // Usando o novo getter
        colunaCodDisc.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
        //colunaCodDisc.setCellValueFactory(new PropertyValueFactory<>("codDisciplina"));
        colunaSemestre.setCellValueFactory(new PropertyValueFactory<>("semestre"));
        colunaAno.setCellValueFactory(new PropertyValueFactory<>("ano"));

        // Carrega os dados na tabela
        carregarDados();
    }

    // Recarrega os dados da tabela com as matrículas atualizadas
    public void carregarDados() {
        ObservableList<Matricula> listaDeMatriculas = daoMatricula.listarMatriculas();
        tableMatriculas.setItems(listaDeMatriculas);
        tableMatriculas.refresh();
    }

    // Limpa o conteúdo do painel principal (pode ser usado para voltar à tela padrão)
    public void limparPainelCentral() {
        painelPrincipal.setCenter(null);
    }

    // Carrega uma nova tela dentro do painel central
    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }

    // Exibe uma janela de alerta com título, cabeçalho e tipo
    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }
}
