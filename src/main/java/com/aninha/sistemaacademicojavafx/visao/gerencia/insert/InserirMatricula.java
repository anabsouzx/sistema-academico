package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.controller.DAOMatricula;
import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
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



    private DAOMatricula daoMatricula;
    private DAOAluno daoAluno;
    private DAODisciplina daoDisciplina;

    public InserirMatricula(){
        this.daoMatricula = new DAOMatricula();
        this.daoAluno = new DAOAluno();
        this.daoDisciplina = new DAODisciplina();
    }

    @FXML
    void insereMatricula(ActionEvent event) {
        int num=0;
        Aluno alunoSelecionado = comboBoxAluno.getSelectionModel().getSelectedItem();
        Disciplina disciplinaSelecionada = comboBoxDisc.getSelectionModel().getSelectedItem();
        String smtStr = txtSemestre.getText();
        String anoStr = txtAno.getText();

        if(alunoSelecionado==null || disciplinaSelecionada==null || smtStr.isEmpty() || anoStr.isEmpty()){
            // mensagem de erro aqui pq nao quero fazer
        }

        // conversao de dados para inteiro
        int smt, ano;

        try {
            smt = Integer.parseInt(smtStr);
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            System.out.println("ei man isso né um numero não");
            return;
        }

        Matricula matricula = new Matricula(num,alunoSelecionado,disciplinaSelecionada,smt,ano);

        try {
            daoMatricula.adicionar(matricula); // O DAO agora lida com a lista

            // Feedback para o utilizador
            Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
            sucesso.setTitle("Sucesso");
            sucesso.setHeaderText("matricula inserido");
            sucesso.setContentText("matricula do aluno " + alunoSelecionado.getNome() + " foi inserido com sucesso!");
            sucesso.showAndWait();

            txtSemestre.setText("");
            txtAno.setText("");
            comboBoxAluno.setItems(null);
            comboBoxDisc.setItems(null);
            txtSemestre.requestFocus(); // Foco no primeiro campo para nova inserção
        } catch (Exception e) {
            e.printStackTrace();
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Erro ao inserir aluno");
            erro.setContentText("Ocorreu um erro inesperado: " + e.getMessage());
            erro.showAndWait();
        }
        //Conexao conexao = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        popularComboBoxAlunos();
        popularComboBoxDisc();

    }

    private void popularComboBoxAlunos() {
        ObservableList<Aluno> alunos = daoAluno.listarTodosAlunosParaComboBox();
        comboBoxAluno.setItems(alunos);

        comboBoxAluno.setConverter(new StringConverter<Aluno>() {
            @Override
            public String toString(Aluno aluno) {
                return aluno == null ? "" : aluno.getNome() + " (ID: " + aluno.getCodigoAluno() + ")";
            }

            @Override
            public Aluno fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return null;
                }
                return comboBoxAluno.getItems().stream()
                        .filter(aluno -> (aluno.getNome() + " (ID: " + aluno.getCodigoAluno() + ")").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }

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
                if (string == null || string.isEmpty()) {
                    return null;
                }
                return comboBoxDisc.getItems().stream()
                        .filter(disciplina -> (disciplina.getNomeDisciplina() + " (ID: " + disciplina.getCodigoDisciplina() + ")").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }
}
