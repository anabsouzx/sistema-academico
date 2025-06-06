// Pacote onde a classe está localizada
package com.aninha.sistemaacademicojavafx.visao.gerencia;

// Importações de DAOs e modelos
import com.aninha.sistemaacademicojavafx.controller.DAOMatricula;
import com.aninha.sistemaacademicojavafx.controller.DAOTurma;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.modelo.Turma;

// Importação da tela de edição
import com.aninha.sistemaacademicojavafx.visao.gerencia.edit.EditarAluno;

// JavaFX: classes para elementos visuais e FXML
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
import java.util.Date;
import java.util.ResourceBundle;

// Controlador da interface de gerenciamento de alunos
public class GerenciarAlunos implements Initializable {

    // Referência para o painel principal da tela
    @FXML
    private BorderPane painelPrincipal;

    // Colunas da tabela (ligadas via FXML)
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

    // Tabela de alunos
    @FXML
    private TableView<Aluno> tableAlunos;

    // DAOs responsáveis pela lógica de acesso aos dados
    private DAOAluno daoAluno;
    private DAOMatricula daoMatricula;
    private DAOTurma daoTurma;

    // Construtor: instancia os DAOs
    public GerenciarAlunos() {
        this.daoAluno = new DAOAluno();
        this.daoMatricula = new DAOMatricula();
        this.daoTurma = new DAOTurma();
    }

    // Método para abrir a tela de edição de um aluno selecionado
    @FXML
    void editarAluno(ActionEvent event) throws IOException {
        Aluno alunoSelecionado = tableAlunos.getSelectionModel().getSelectedItem();

        if (alunoSelecionado == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione um aluno para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("edit/editar-aluno.fxml"));
            Parent telaEdicao = loader.load();

            EditarAluno controllerEdicao = loader.getController();
            controllerEdicao.setAlunoParaEditar(alunoSelecionado, this); // Passa o aluno e o controller

            painelPrincipal.setCenter(telaEdicao); // Substitui o painel central pela tela de edição

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao Carregar Tela", "Não foi possível carregar a tela de edição.", Alert.AlertType.ERROR);
        }
    }

    // Método para excluir um aluno
    @FXML
    void excluirAluno(ActionEvent event) throws IOException {
        Aluno selecionada = tableAlunos.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Seleção Necessária", "Por favor, selecione uma disciplina para excluir.", Alert.AlertType.WARNING);
            return;
        }

        // Verifica se o aluno está associado a alguma matrícula
        boolean emUso = false;
        for (Matricula matricula : daoMatricula.listarMatriculas()) {
            if (matricula.getAluno() != null && matricula.getAluno().getCodigoAluno() == selecionada.getCodigoAluno()) {
                emUso = true;
                break;
            }
        }

        // Verifica se o aluno está em alguma turma
        boolean estaEmTurma = false;
        for (Turma turma : daoTurma.listarTurmas()) {
            for (Aluno alunoDaTurma : turma.getListaAlunos()) {
                if (alunoDaTurma.getCodigoAluno() == selecionada.getCodigoAluno()) {
                    estaEmTurma = true;
                    break;
                }
            }
            if (estaEmTurma) break;
        }

        // Bloqueia exclusão se estiver em turma
        if (estaEmTurma) {
            mostrarAlerta("Exclusão não permitida", "Este aluno não pode ser excluído, pois está matriculado em uma turma.", Alert.AlertType.ERROR);
            return;
        }

        // Bloqueia exclusão se estiver em matrícula
        if (emUso) {
            mostrarAlerta("Exclusão não permitida", "Este aluno não pode ser excluído, pois existe uma matrícula em vigor.", Alert.AlertType.ERROR);
            return;
        }

        // Aluno pode ser excluído
        daoAluno.excluirAluno(selecionada);
        carregarDados(); // Atualiza a tabela
    }

    // Método para abrir a tela de inserção de um novo aluno
    @FXML
    void inserirAluno(ActionEvent event) throws IOException {
        carregarTela("insert/inserir-aluno.fxml");
    }

    // Método chamado automaticamente quando a tela é inicializada
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Define o mapeamento entre colunas da tabela e atributos do objeto Aluno
        colunaCodigoA.setCellValueFactory(new PropertyValueFactory<>("codigoAluno"));
        colunaNomeA.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaData.setCellValueFactory(new PropertyValueFactory<>("dataDeNascimento"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        carregarDados(); // Carrega os dados da tabela na inicialização
    }

    // Atualiza a tabela com a lista de alunos
    public void carregarDados() {
        ObservableList<Aluno> listaDeAlunos = daoAluno.listarAlunos();
        tableAlunos.setItems(listaDeAlunos);
        tableAlunos.refresh(); // Garante que os dados sejam redesenhados
    }

    // Limpa o painel central (pode ser chamado após cancelar ou salvar uma edição)
    public void limparPainelCentral() {
        painelPrincipal.setCenter(null);
    }

    // Carrega dinamicamente uma nova tela FXML no painel central
    private void carregarTela(String fxmlFile) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent contentDaTela = fxmlLoader.load();
        painelPrincipal.setCenter(contentDaTela);
    }

    // Exibe alertas para o usuário
    private void mostrarAlerta(String titulo, String cabecalho, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.showAndWait();
    }
}
