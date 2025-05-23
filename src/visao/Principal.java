package visao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame{
    private JPanel panel1;
    private JPanel panelBtn;
    private JDesktopPane desktop;
    private JButton inserirButton;
    private JButton listarButton;

    public Principal() {
        setContentPane(panel1); // painel de conteudo do jFrame

        setTitle("Sistema Acadêmico"); // titulo da janela
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // termina de rodar quando fecha
        setSize(800, 600); // tamanho da janela
        setLocationRelativeTo(null); // centralizar na tela
        setVisible(true); // torna visivel

        // açao do botao inserir
        inserirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaInserir telaInserir = new TelaInserir();

                if(desktop!=null){
                    desktop.add(telaInserir); // adiciona tela no desktopPane
                    System.out.println("inserir adicionado no desktop");

                    try {
                        telaInserir.setSelected(true);
                    } catch (java.beans.PropertyVetoException ex){
                        System.err.println("Erro ao selecionar TelaInserir: " + ex.getMessage());
                        ex.printStackTrace();
                    }

                    telaInserir.setVisible(true);
                    System.out.println("telaInserir.setVisible(true) chamado."); // <--- Teste
                    System.out.println("TelaInserir é visível? " + telaInserir.isVisible()); // <--- Teste
                    System.out.println("Tamanho de TelaInserir: " + telaInserir.getSize()); // <--- Teste
                }else{
                    System.err.println("ERRO: desktopPanePrincipal é nulo!");
                }
            }
        });

        // açao do botao listar
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaListar telaListar = new TelaListar();

                if(desktop!=null){
                    desktop.add(telaListar); // adiciona tela no desktoppane
                    System.out.println("listar adicionado no desktop");

                    try {
                        telaListar.setSelected(true);
                    } catch (java.beans.PropertyVetoException ex){
                        System.err.println("Erro ao selecionar TelaListar: " + ex.getMessage());
                        ex.printStackTrace();
                    }

                    telaListar.setVisible(true);
                    System.out.println("telaListar.setVisible(true) chamado."); // <--- Teste
                    System.out.println("TelaListar é visível? " + telaListar.isVisible()); // <--- Teste
                    System.out.println("Tamanho de TelaListar: " + telaListar.getSize()); // <--- Teste
                }else{
                    System.err.println("ERRO: desktopPanePrincipal é nulo!");
                }
            }
        });
    }

    // rodar aplicaçao
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Principal frame = new Principal();
            frame.setVisible(true);
        });
    }
}
