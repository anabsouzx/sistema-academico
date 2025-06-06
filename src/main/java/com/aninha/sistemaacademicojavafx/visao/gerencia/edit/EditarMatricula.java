// Pacote onde a classe está localizada
package com.aninha.sistemaacademicojavafx.visao.gerencia.edit;

// Importações de controladores e modelos utilizados na lógica
import com.aninha.sistemaacademicojavafx.controller.DAOAluno;
import com.aninha.sistemaacademicojavafx.controller.DAODisciplina;
import com.aninha.sistemaacademicojavafx.controller.DAOMatricula;
import com.aninha.sistemaacademicojavafx.modelo.Aluno;
import com.aninha.sistemaacademicojavafx.modelo.Disciplina;
import com.aninha.sistemaacademicojavafx.modelo.Matricula;
import com.aninha.sistemaacademicojavafx.visao.gerencia.GerenciarMatriculas;

// Importações JavaFX para eventos e componentes visuais
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

// Classe responsável pela tela e lógica de edição de uma matrícula
public class EditarMatricula {

    // Campos da interface gráfica (ligados via FXML)
    @FXML
    private TextField campoCodAluno;

    @FXML
    private TextField campoCodDisciplina;

    @FXML
    private TextField campoSemestre;

    @FXML
    private TextField campoAno;

    // Objeto matrícula que será editado
    private Matricula matricula;

    // Controlador da tela principal de gerenciamento de matrículas
    private GerenciarMatriculas controllerPai;

    // DAOs para acesso aos dados de alunos, disciplinas e matrículas
    private final DAOAluno daoAluno = new DAOAluno();
    private final DAODisciplina daoDisciplina = new DAODisciplina();
    private final DAOMatricula daoMatricula = new DAOMatricula();

    // Método que recebe a matrícula a ser editada e o controlador principal
    public void setMatriculaParaEditar(Matricula matricula, GerenciarMatriculas controllerPai) {
        this.matricula = matricula;
        this.controllerPai = controllerPai;

        // Preenche os campos da interface com os dados atuais da matrícula
        campoCodAluno.setText(String.valueOf(matricula.getAluno().getCodigoAluno()));
        campoCodDisciplina.setText(String.valueOf(matricula.getDisciplina().getCodigoDisciplina()));
        campoSemestre.setText(String.valueOf(matricula.getSemestre()));
        campoAno.setText(String.valueOf(matricula.getAno()));
    }

    // Método chamado quando o botão "Salvar" é pressionado
    @FXML
    void salvarAlteracoesAction(ActionEvent event) {
        // Obtém os dados preenchidos nos campos
        String codAlunoStr = campoCodAluno.getText();
        String codDisciplinaStr = campoCodDisciplina.getText();
        String semestre = campoSemestre.getText();
        String anoStr = campoAno.getText();

        // Verifica se algum campo está vazio
        if (codAlunoStr.isEmpty() || codDisciplinaStr.isEmpty() || semestre.isEmpty() || anoStr.isEmpty()) {
            mostrarAlerta("Campos vazios", "Preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        int codAluno, codDisciplina, ano;
        try {
            // Converte os valores para inteiro
            codAluno = Integer.parseInt(codAlunoStr);
            codDisciplina = Integer.parseInt(codDisciplinaStr);
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            // Mostra alerta se os valores não forem números inteiros válidos
            mostrarAlerta("Erro de formato", "Código e ano devem ser números inteiros.", Alert.AlertType.ERROR);
            return;
        }

        // Busca os objetos Aluno e Disciplina a partir dos códigos informados
        Aluno aluno = daoAluno.buscarPorCodigo(codAluno);
        Disciplina disciplina = daoDisciplina.buscarPorCodigo(codDisciplina);

        // Valida se ambos foram encontrados
        if (aluno == null || disciplina == null) {
            mostrarAlerta("Erro", "Aluno ou Disciplina não encontrados.", Alert.AlertType.ERROR);
            return;
        }

        // Atualiza os dados da matrícula com as novas informações
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);
        matricula.setSemestre(Integer.parseInt(campoSemestre.getText()));
        matricula.setAno(ano);

        // Importante: a lista de matrículas é a mesma referência, não há necessidade de salvar no DAO explicitamente

        // Alerta de sucesso
        mostrarAlerta("Sucesso", "Matrícula atualizada com sucesso!", Alert.AlertType.INFORMATION);

        // Atualiza a lista na tela principal
        controllerPai.carregarDados();

        // Fecha a tela de edição
        fecharTela();
    }

    // Método chamado ao pressionar "Cancelar"
    @FXML
    void cancelarAction(ActionEvent event) {
        fecharTela(); // Fecha a tela sem salvar
    }

    // Fecha a tela de edição (retorna para a tela principal)
    private void fecharTela() {
        if (controllerPai != null) {
            controllerPai.limparPainelCentral(); // Método para limpar/fechar a área de edição
        }
    }

    // Método utilitário para exibir alertas na tela
    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);        // Cria um novo alerta com o tipo informado
        alert.setTitle(titulo);              // Define o título da janela do alerta
        alert.setHeaderText(msg);            // Define o cabeçalho/mensagem
        alert.showAndWait();                 // Exibe o alerta e aguarda o fechamento
    }
}
