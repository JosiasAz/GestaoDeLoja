package view;

import model.Cliente;
import Controller.LojaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClienteFrame extends JFrame {
    private LojaService lojaService;
    private JTextField nomeField, cpfField, telefoneField, enderecoField;

    public ClienteFrame(LojaService lojaService) {
        this.lojaService = lojaService;
        initUI();
    }

    private void initUI() {
        setTitle("Cadastro de Clientes");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos do formulário
        panel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        panel.add(nomeField);

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        panel.add(cpfField);

        panel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        panel.add(telefoneField);

        panel.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        panel.add(enderecoField);

        // Botão de cadastro
        JButton cadastrarBtn = new JButton("Cadastrar Cliente");
        cadastrarBtn.addActionListener(this::cadastrarCliente);
        panel.add(new JLabel());
        panel.add(cadastrarBtn);

        add(panel);
    }

    private void cadastrarCliente(ActionEvent e) {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();
        String endereco = enderecoField.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, telefone, endereco);
        lojaService.cadastrarCliente(cliente);
        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        limparCampos();
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
    }
}