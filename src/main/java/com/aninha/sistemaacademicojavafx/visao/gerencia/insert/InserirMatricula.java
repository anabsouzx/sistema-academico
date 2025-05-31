package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.modelo.persistencia.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private DAOAluno daoAluno;
    private DAOMatricula daoMatricula;
    private DAODisciplina daoDisciplina;

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
        int codAluno, codDisc, smt, ano;

        try {
            codAluno = alunoSelecionado.getCodigoAluno();
            codDisc = disciplinaSelecionada.getCodigoDisciplina();
            smt = Integer.parseInt(smtStr);
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            System.out.println("ei man isso né um numero não");
            return;
        }

        // adicionando dados a tabela blabllabla
        Matricula matricula = new Matricula(num,codAluno,codDisc,smt,ano);
        //Conexao conexao = null;


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Conexao c = new Conexao();
        daoAluno = new DAOAluno(c.getConexao());
        daoMatricula = new DAOMatricula(c.getConexao());
        daoDisciplina = new DAODisciplina(c.getConexao());

        popularComboBoxAlunos();
        popularComboBoxDisc();*/
    }

    private void popularComboBoxAlunos() {
        /*ObservableList<Aluno> alunos = daoAluno.listarTodosAlunosParaComboBox();
        comboBoxAluno.setItems(alunos);

        // Configurar como o objeto Aluno é exibido no ComboBox
        comboBoxAluno.setConverter(new StringConverter<Aluno>() {
            @Override
            public String toString(Aluno aluno) {
                return aluno == null ? "" : aluno.getNomeAluno() + " (ID: " + aluno.getCodigoAluno() + ")";
            }

            @Override
            public Aluno fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return null;
                }
                return comboBoxAluno.getItems().stream()
                        .filter(aluno -> (aluno.getNomeAluno() + " (ID: " + aluno.getCodigoAluno() + ")").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        // Opcional: Adicionar um listener para quando a seleção muda
        /*comboBoxAluno.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Aluno selecionado: " + newValue.getNomeAluno() + " ID: " + newValue.getCodigoAluno());
            }
        });*/
    }

    private void popularComboBoxDisc() {
        ObservableList<Disciplina> disciplinas = daoDisciplina.listarDisciplinasComboBox();
        comboBoxDisc.setItems(disciplinas);

        // Configurar como o objeto Aluno é exibido no ComboBox
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
