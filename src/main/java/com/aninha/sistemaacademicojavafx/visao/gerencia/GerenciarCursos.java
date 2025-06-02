package com.aninha.sistemaacademicojavafx.visao.gerencia;

//imports nescessarios
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOCurso;
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarCurso;
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

// Classe que controla a tela de gerenciamento de cursos
public class GerenciarCursos implements Initializable {

    // Painel principal da interface onde telas serão carregadas dinamicamente
    @FXML
    private BorderPane painelPrincipal;

    // Tabela que exibe os cursos
    @FXML
    private TableView<Curso> tableCursos;

    // Colunas da tabela
    @FXML
    private TableColumn<Curso, Integer> colunaCodigoC;

    @FXML
    private TableColumn<Curso, String> colunaNomeC;

    @FXML
    private TableColumn<Curso, Integer> colunaDuracao;

    // Objeto DAO para manipular os dados dos cursos
    private DAOCurso daoCurso;

    // Construtor: inicializa o DAO
    public GerenciarCursos() {
        this.daoCurso = new DAOCurso();
    }

    // Método executado quando o botão de "Editar" é clicado
    @FXML
    void editarCurso(ActionEvent event) throws IOException {
        // Pega o curso selecionado na tabela
        Curso cursoSelecionado = tableCursos.getSelectionModel().getSelectedItem();

        // Verifica se algum curso foi selecionado
        if (cursoSelecionado == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione um curso para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Carrega a tela de edição do curso
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit/editar-curso.fxml"));
            Parent telaEdicao = loader.load();

            // Pega o controller da tela de edição
            EditarCurso controllerEdicao = loader.getController();

            // Passa o curso selecionado e o controller atual para a tela de edição
            controllerEdicao.setCursoParaEditar(cursoSelecionado, this);

            // Troca o conteúdo do painel pela tela de edição
            painelPrincipal.setCenter(telaEdicao);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao Carregar Tela", "Não foi possível carregar a tela de edição.", Alert.AlertType.ERROR);
        }
    }

    // Método chamado ao clicar no botão "Excluir"
    @FXML
    void excluirCurso(ActionEvent event) {
        // Pega o curso selecionado
        Curso cursoSelecionado = tableCursos.getSelectionModel().getSelectedItem();

        // Verifica se nada foi selecionado
        if (cursoSelecionado == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione um curso para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // Exclui o curso e atualiza a tabela
        daoCurso.excluirCurso(cursoSelecionado);
        carregarDados();
    }

    // Metodo chamado ao clicar em "Inserir novo curso"

    @FXML
    void inserirCurso(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-curso.fxml");
    }

    // Metodo chamado automaticamente ao carregar o FXML (inicializa a tela)
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Associa as colunas da tabela às propriedades da classe Curso
        colunaCodigoC.setCellValueFactory(new PropertyValueFactory<>("codigoCurso"));
        colunaNomeC.setCellValueFactory(new PropertyValueFactory<>("nomeCurso"));
        colunaDuracao.setCellValueFactory(new PropertyValueFactory<>("duracao"));

        // Carrega os dados na tabela
        carregarDados();
    }

    // Recarrega os dados da tabela com os cursos atualizados
    public void carregarDados() {
        ObservableList<Curso> listaDeCursos = daoCurso.listarCursos();
        tableCursos.setItems(listaDeCursos);
        tableCursos.refresh();
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
