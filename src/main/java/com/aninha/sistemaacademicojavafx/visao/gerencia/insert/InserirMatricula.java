package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOAluno;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAODisciplina;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.DAOMatricula;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class InserirMatricula implements Initializable {
    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TextField txtAno;

    @FXML
    private ComboBox<Aluno> comboBoxAluno;

    @FXML
    private ComboBox<Disciplina> comboBoxDisc;

    @FXML
    private TextField txtSemestre;

    // DAOs para acesso às listas simuladas (como se fosse o banco de dados)
    private DAOAluno daoAluno;
    private DAOMatricula daoMatricula;
    private DAODisciplina daoDisciplina;

    // Ação executada ao clicar no botão "Inserir Matrícula"
    @FXML
    void insereMatricula(ActionEvent event) {
        int num = 0;

        // Recupera os dados do formulário
        Aluno alunoSelecionado = comboBoxAluno.getSelectionModel().getSelectedItem();
        Disciplina disciplinaSelecionada = comboBoxDisc.getSelectionModel().getSelectedItem();
        String smtStr = txtSemestre.getText();
        String anoStr = txtAno.getText();

        // Verifica se todos os campos foram preenchidos
        if (alunoSelecionado == null || disciplinaSelecionada == null || smtStr.isEmpty() || anoStr.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos obrigatórios");
            alerta.setHeaderText("Preencha todos os campos.");
            alerta.setContentText("Você deve selecionar um aluno, uma disciplina e preencher semestre e ano.");
            alerta.show();
            return;
        }

        try {
            // Converte os dados numéricos
            int codAluno = alunoSelecionado.getCodigoAluno();
            int codDisc = disciplinaSelecionada.getCodigoDisciplina();
            int smt = Integer.parseInt(smtStr);
            int ano = Integer.parseInt(anoStr);

            // Cria nova matrícula com os dados coletados
            Matricula matricula = new Matricula(num, codAluno, codDisc, smt, ano);

            // Adiciona a matrícula usando o DAO
            daoMatricula.adicionar(matricula); // Este método deve existir na DAOMatricula

            // Mostra mensagem de sucesso
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText("Matrícula registrada");
            sucesso.setContentText("Matrícula realizada com sucesso.");
            sucesso.showAndWait();

            // Limpa os campos após inserção
            txtAno.clear();
            txtSemestre.clear();
            comboBoxAluno.getSelectionModel().clearSelection();
            comboBoxDisc.getSelectionModel().clearSelection();

        } catch (NumberFormatException e) {
            // Mostra erro caso ano ou semestre não sejam números
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro de formato");
            erro.setHeaderText("Ano e Semestre precisam ser números.");
            erro.setContentText("Corrija os campos antes de continuar.");
            erro.showAndWait();
        }
    }

    // Inicializa os componentes ao carregar a tela
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        daoAluno = new DAOAluno();
        daoDisciplina = new DAODisciplina();
        daoMatricula = new DAOMatricula();

        // Preenche os ComboBoxes com os dados
        popularComboBoxAlunos();
        popularComboBoxDisc();
    }

    // Preenche ComboBox com os alunos e define como exibir
    private void popularComboBoxAlunos() {
        ObservableList<Aluno> alunos = daoAluno.listarTodosAlunosParaComboBox();
        comboBoxAluno.setItems(alunos);

        // Define como o aluno será mostrado na ComboBox
        comboBoxAluno.setConverter(new StringConverter<Aluno>() {
            @Override
            public String toString(Aluno aluno) {
                return aluno == null ? "" : aluno.getNome() + " (ID: " + aluno.getCodigoAluno() + ")";
            }

            @Override
            public Aluno fromString(String string) {
                return comboBoxAluno.getItems().stream()
                        .filter(aluno -> (aluno.getNome() + " (ID: " + aluno.getCodigoAluno() + ")").equals(string))
                        .findFirst().orElse(null);
            }
        });
    }

    // Preenche ComboBox com disciplinas e define como exibir
    private void popularComboBoxDisc() {
        ObservableList<Disciplina> disciplinas = daoDisciplina.listarDisciplinasComboBox();
        comboBoxDisc.setItems(disciplinas);

        comboBoxDisc.setConverter(new StringConverter<Disciplina>() {
            @Override
            public String toString(Disciplina disciplina) {
                return disciplina == null ? "" : disciplina.getNomeDisciplina() + " (ID: " + disciplina.getCodigoDisciplina() + ")";
            }

            @Override
            public Disciplina fromString(String string) {
                return comboBoxDisc.getItems().stream()
                        .filter(d -> (d.getNomeDisciplina() + " (ID: " + d.getCodigoDisciplina() + ")").equals(string))
                        .findFirst().orElse(null);
            }
        });
    }
}
