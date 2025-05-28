package com.aninha.sistemaacademicojavafx.visao.gerencia.insert;

import com.aninha.sistemaacademicojavafx.modelo.Curso;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
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

public class InserirDisciplina implements Initializable {
    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private TextField txtCargaH;

    @FXML
    private ComboBox<Curso> comboBoxCurso;

    @FXML
    private TextField txtNomeD;

    private DAODisciplina daoDisciplina;
    private DAOCurso daoCurso;

    @FXML
    void insereDisciplina(ActionEvent event) {
        int codigo = 0;
        String nome = txtNomeD.getText();
        Curso cursoSelecionado = comboBoxCurso.getSelectionModel().getSelectedItem();
        String cargaHStr = txtCargaH.getText();

        if(cursoSelecionado==null || nome.isEmpty() || cargaHStr.isEmpty()){
            // mensagem de erro aqui pq nao quero fazer
        }

        // converter horas pra inteiro nao aguento mais
        int cargaH, codCurso;

        try{
            cargaH = Integer.parseInt(cargaHStr);
            codCurso = cursoSelecionado.getCodigoCurso();
        } catch (NumberFormatException e) {
            System.out.println("djabo de numero eh esse man");
            return;
        }

        // adicionar na tabela socorro
        Disciplina disciplina = new Disciplina(codigo,nome,codCurso,cargaH);
        Conexao conexao = null;

        try{
            conexao = new Conexao();
            DAODisciplina daoDisciplina = new DAODisciplina(conexao.getConexao());

            daoDisciplina.inserirDisciplina(disciplina);

            comboBoxCurso.getSelectionModel().clearSelection();
            txtCargaH.setText("");
            txtNomeD.setText("");
            txtNomeD.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(conexao!=null){
                conexao.fecharConexao();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Conexao c = new Conexao();
        daoCurso = new DAOCurso(c.getConexao());
        daoDisciplina = new DAODisciplina(c.getConexao());

        popularComboBoxCurso();
    }

    private void popularComboBoxCurso() {
        ObservableList<Curso> cursos = daoCurso.listarCursosComboBox();
        comboBoxCurso.setItems(cursos);

        // Configurar como o objeto Aluno é exibido no ComboBox
        comboBoxCurso.setConverter(new StringConverter<Curso>() {
            @Override
            public String toString(Curso curso) {
                return curso == null ? "" : curso.getNomeCurso() + " (ID: " + curso.getCodigoCurso() + ")";
            }

            @Override
            public Curso fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return null;
                }
                return comboBoxCurso.getItems().stream()
                        .filter(curso -> (curso.getNomeCurso() + " (ID: " + curso.getCodigoCurso() + ")").equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }
}
