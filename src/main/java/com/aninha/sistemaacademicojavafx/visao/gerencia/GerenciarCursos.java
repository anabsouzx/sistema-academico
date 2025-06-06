package com.aninha.sistemaacademicojavafx.visao.gerencia;

//imports nescessarios
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
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
    private DAODisciplina daoDisciplina;

    // Construtor: inicializa o DAO
    public GerenciarCursos() {
        this.daoCurso = new DAOCurso();
        this.daoDisciplina = new DAODisciplina();
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
        Curso selecionada = tableCursos.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // --- VERIFICAÇÃO ADICIONADA ---
        // Verifica se a disciplina está sendo usada em alguma matrícula
        boolean emUso = false;
        for (Disciplina disciplina : daoDisciplina.listarDisciplinas()) {
            if (disciplina.getCurso() != null && disciplina.getCurso().getCodigoCurso() == selecionada.getCodigoCurso()) {
                emUso = true;
                break; // Encontrou uma matrícula, não precisa continuar procurando
            }
        }

        // Se estiver em uso, impede a exclusão e avisa o usuário
        if (emUso) {
            mostrarAlerta("Exclusão não permitida", "Esta disciplina não pode ser excluída, pois existem alunos matriculados nela.", Alert.AlertType.ERROR);
            return;
        }
        // --- FIM DA VERIFICAÇÃO ---


        // Se não estiver em uso, a exclusão é realizada normalmente
        daoCurso.excluirCurso(selecionada);
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
    public void abrirTelaEditarCurso(Curso cursoSelecionado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/aninha/sistemaacademicojavafx/visao/gerencia/edit/editar-curso.fxml"));
            Parent root = loader.load();

            EditarCurso editarCursoController = loader.getController();
            editarCursoController.setCursoParaEditar(cursoSelecionado, this);

            exibirNoPainelCentral(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exibirNoPainelCentral(Parent tela) {
        painelPrincipal.setCenter(tela);
    }
    


}
