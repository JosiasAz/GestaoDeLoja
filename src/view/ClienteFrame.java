package view;

import model.Cliente;
import Controller.LojaService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteFrame extends JFrame implements ActionListener {
    private LojaService lojaService;

    private JLabel lblNome, lblCpf, lblTelefone, lblEndereco, lblDataNascimento, lblEmail, lblReceberPromocoes;
    private JTextField campoNome, campoCpf, campoTelefone, campoEndereco, campoEmail;
    private JFormattedTextField campoDataNascimento;
    private JCheckBox checkReceberPromocoes;
    private JButton cadastrarBtn, verClientesBtn, cancelarBtn;

    public ClienteFrame(LojaService lojaService) {
        this.lojaService = lojaService;
        criarComponentes();
    }

    private void criarComponentes() {
        setTitle("Cadastro de Clientes");
        setSize(450, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(0, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblNome = new JLabel("Nome:");
        lblCpf = new JLabel("CPF:");
        lblTelefone = new JLabel("Telefone:");
        lblEndereco = new JLabel("Endereço:");
        lblDataNascimento = new JLabel("Data de Nascimento:");
        lblEmail = new JLabel("Email:");
        lblReceberPromocoes = new JLabel("Receber Promoções:");

        campoNome = new JTextField();
        campoCpf = new JTextField();
        campoTelefone = new JTextField();
        campoEndereco = new JTextField();
        campoEmail = new JTextField();

        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            campoDataNascimento = new JFormattedTextField(dataMask);
        } catch (Exception e) {
            campoDataNascimento = new JFormattedTextField();
        }

        checkReceberPromocoes = new JCheckBox();

        painel.add(lblNome);
        painel.add(campoNome);

        painel.add(lblCpf);
        painel.add(campoCpf);

        painel.add(lblTelefone);
        painel.add(campoTelefone);

        painel.add(lblEndereco);
        painel.add(campoEndereco);

        painel.add(lblDataNascimento);
        painel.add(campoDataNascimento);

        painel.add(lblEmail);
        painel.add(campoEmail);

        painel.add(lblReceberPromocoes);
        painel.add(checkReceberPromocoes);

        cadastrarBtn = new JButton("Cadastrar Cliente");
        cadastrarBtn.addActionListener(this);
        painel.add(cadastrarBtn);

        verClientesBtn = new JButton("Ver Clientes Cadastrados");
        verClientesBtn.addActionListener(this);
        painel.add(verClientesBtn);

        cancelarBtn = new JButton("Voltar");
        cancelarBtn.addActionListener(this);
        painel.add(cancelarBtn);

        add(painel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarBtn) {
            String nome = campoNome.getText();
            String cpfTexto = campoCpf.getText();
            String telefoneTexto = campoTelefone.getText();
            String endereco = campoEndereco.getText();
            String dataNascimento = campoDataNascimento.getText();
            String email = campoEmail.getText();
            boolean receberPromocoes = checkReceberPromocoes.isSelected();

            if (nome.isEmpty() || cpfTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Long.parseLong(cpfTexto);
                Long.parseLong(telefoneTexto);

                Cliente cliente = new Cliente(nome, cpfTexto, telefoneTexto, endereco, dataNascimento, email, receberPromocoes);
                lojaService.cadastrarCliente(cliente);

                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");

                campoNome.setText("");
                campoCpf.setText("");
                campoTelefone.setText("");
                campoEndereco.setText("");
                campoDataNascimento.setValue(null);
                campoEmail.setText("");
                checkReceberPromocoes.setSelected(false);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "CPF e Telefone devem conter apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == verClientesBtn) {
            List<Cliente> clientes = lojaService.getClientes();
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado ainda.");
            } else {
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Total de clientes: ").append(clientes.size()).append("\n\n");
                for (Cliente c : clientes) {
                    mensagem.append("- ").append(c.getNome()).append("\n");
                }

                JTextArea textArea = new JTextArea(mensagem.toString());
                textArea.setEditable(false);

                JOptionPane.showMessageDialog(this, textArea, "Clientes Cadastrados", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        if (e.getSource() == cancelarBtn) {
            dispose();
        }
    }
}
