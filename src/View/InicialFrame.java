package View;

import javax.swing.*;

import Controller.Jogo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class InicialFrame extends JFrame {
    private JButton newGameButton;
    private JButton loadGameButton;
    private JFileChooser fileChooser;

    public InicialFrame() {
        setTitle("Batalha Naval - Menu Inicial");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        newGameButton = new JButton("Nova Partida");
        newGameButton.setBounds(50, 50, 130, 50);
        panel.add(newGameButton);

        loadGameButton = new JButton("Carregar Partida");
        loadGameButton.setBounds(200, 50, 130, 50);
        panel.add(loadGameButton); 

        fileChooser = new JFileChooser();

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(InicialFrame.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Aqui você pode processar o arquivo selecionado
                    System.out.println("Arquivo selecionado: " + selectedFile.getAbsolutePath());
                }
            }
        });

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                InicialFrame.this.dispose();
                Jogo.startNewGame();            }
        });

        setContentPane(panel);
        setLocationRelativeTo(null); // Centraliza a janela na tela
    }

    public static void main(String[] args) {
        InicialFrame initialFrame = new InicialFrame();
        initialFrame.setVisible(true);
    }
}
