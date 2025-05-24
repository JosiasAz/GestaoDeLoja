package view;

import model.*;
import Controller.LojaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ProdutoFrame extends JFrame {
    private LojaService lojaService;
    private JComboBox<String> tipoProdutoCombo;
    private JTextField nomeField, descricaoField, precoField, quantidadeField;

    public ProdutoFrame(LojaService lojaService) {
        this.lojaService = lojaService;
        initUI();
    }

    private void initUI() {
        setTitle("Cadastro de Produtos");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos do formulário
        panel.add(new JLabel("Tipo de Produto:"));
        tipoProdutoCombo = new JComboBox<>(new String[]{"Físico", "Digital"});
        panel.add(tipoProdutoCombo);

        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField();
        panel.add(descricaoField);

        panel.add(new JLabel("Preço:"));
        precoField = new JTextField();
        panel.add(precoField);

        panel.add(new JLabel("Quantidade:"));
        quantidadeField = new JTextField();
        panel.add(quantidadeField);

        // Botão de cadastro
        JButton cadastrarBtn = new JButton("Cadastrar Produto");
        cadastrarBtn.addActionListener(this::cadastrarProduto);
        panel.add(new JLabel());
        panel.add(cadastrarBtn);

        add(panel);
    }

    private void cadastrarProduto(ActionEvent e) {
        try {
            String tipo = (String) tipoProdutoCombo.getSelectedItem();
            String nome = nomeField.getText();
            String descricao = descricaoField.getText();
            double preco = Double.parseDouble(precoField.getText());
            int quantidade = Integer.parseInt(quantidadeField.getText());

            Produto produto;
            if (tipo.equals("Físico")) {
                produto = new ProdutoFisico(nome, descricao, preco, quantidade);
            } else {
                produto = new ProdutoDigital(nome, descricao, preco, quantidade);
            }

            lojaService.cadastrarProduto(produto);
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço e quantidade devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        descricaoField.setText("");
        precoField.setText("");
        quantidadeField.setText("");
    }
}