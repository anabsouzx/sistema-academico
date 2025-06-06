// Define o pacote onde esta classe está localizada
package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

// Importações das classes do sistema
import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOCurso;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarDisciplinas;

// Importações do JavaFX para componentes da interface e eventos
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

// Classe responsável pela lógica de edição de uma disciplina
public class EditarDisciplina {

    // Campo de texto para o nome da disciplina (ligado via FXML)
    @FXML
    private TextField txtNomeDisciplina;

    // Campo de texto para a carga horária
    @FXML
    private TextField txtCargaHoraria;

    // ComboBox para seleção do curso ao qual a disciplina pertence
    @FXML
    private ComboBox<Curso> comboCursos;

    // A disciplina que será editada
    private Disciplina disciplinaParaEditar;

    // Referência ao controlador principal que gerencia as disciplinas
    private GerenciarDisciplinas controllerGerenciador;

    // Objetos DAO para interagir com os dados da disciplina e do curso
    private DAODisciplina daoDisciplina;
    private DAOCurso daoCurso;

    // Construtor: inicializa os DAOs
    public EditarDisciplina() {
        this.daoDisciplina = new DAODisciplina();
        this.daoCurso = new DAOCurso();
    }

    // Método que recebe a disciplina a ser editada e o controlador chamador
    public void setDisciplinaParaEditar(Disciplina disciplina, GerenciarDisciplinas controllerGerenciador) {
        this.disciplinaParaEditar = disciplina;
        this.controllerGerenciador = controllerGerenciador;
        carregarCursos(); // Carrega a lista de cursos no ComboBox
        popularCampos();  // Preenche os campos com os dados da disciplina
    }

    // Carrega os cursos disponíveis e os insere no ComboBox
    private void carregarCursos() {
        ObservableList<Curso> cursos = daoCurso.listarCursos(); // Busca todos os cursos
        comboCursos.setItems(cursos); // Define os itens do ComboBox
    }

    // Preenche os campos da tela com os dados da disciplina a ser editada
    private void popularCampos() {
        if (disciplinaParaEditar != null) {
            txtNomeDisciplina.setText(disciplinaParaEditar.getNomeDisciplina());
            txtCargaHoraria.setText(String.valueOf(disciplinaParaEditar.getCargaHoraria()));

            // Seleciona no ComboBox o curso associado à disciplina
            if (disciplinaParaEditar.getCurso() != null) {
                comboCursos.getSelectionModel().select(disciplinaParaEditar.getCurso());
            }
        }
    }

    // Método acionado quando o botão de "Atualizar" é clicado
    @FXML
    void atualizaDisciplinaAction(ActionEvent event) {
        // Obtém os valores dos campos
        String nome = txtNomeDisciplina.getText();
        String cargaStr = txtCargaHoraria.getText();
        Curso cursoSelecionado = comboCursos.getSelectionModel().getSelectedItem();

        // Validação: verifica se há campos vazios ou curso não selecionado
        if (nome.isEmpty() || cargaStr.isEmpty() || cursoSelecionado == null) {
            mostrarAlerta("Campos vazios", "Preencha todos os campos e selecione um curso.", Alert.AlertType.WARNING);
            return;
        }

        int cargaHoraria;
        try {
            // Converte a carga horária para número inteiro
            cargaHoraria = Integer.parseInt(cargaStr);
        } catch (NumberFormatException e) {
            // Alerta de erro caso o valor não seja um número
            mostrarAlerta("Erro de formato", "Carga horária deve ser um número inteiro.", Alert.AlertType.ERROR);
            txtCargaHoraria.requestFocus(); // Foca no campo com erro
            return;
        }

        // Atualiza os dados da disciplina com os novos valores
        disciplinaParaEditar.setNomeDisciplina(nome);
        disciplinaParaEditar.setCargaHoraria(cargaHoraria);
        disciplinaParaEditar.setCurso(cursoSelecionado);

        // Tenta atualizar a disciplina no banco de dados
        if (daoDisciplina.atualizarDisciplina(disciplinaParaEditar)) {
            mostrarAlerta("Sucesso", "Disciplina atualizada com sucesso!", Alert.AlertType.INFORMATION);

            // Atualiza os dados na tela principal, se o controlador estiver disponível
            if (controllerGerenciador != null) {
                controllerGerenciador.carregarDados();
            }
            fecharTelaDeEdicao(); // Fecha a tela de edição
        } else {
            // Caso ocorra erro na atualização
            mostrarAlerta("Erro", "Falha ao atualizar a disciplina.", Alert.AlertType.ERROR);
        }
    }

    // Método acionado quando o botão "Cancelar" é clicado
    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTelaDeEdicao(); // Fecha a tela de edição
    }

    // Fecha a tela de edição chamando o método de limpeza da tela principal
    private void fecharTelaDeEdicao() {
        if (controllerGerenciador != null) {
            controllerGerenciador.limparPainelCentral();
        }
    }

    // Exibe uma caixa de alerta com título, mensagem e tipo de alerta
    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensagem);
        alert.showAndWait(); // Aguarda o usuário fechar o alerta
    }
}
