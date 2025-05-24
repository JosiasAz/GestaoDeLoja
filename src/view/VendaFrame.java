package view;

import model.*;
import Controller.LojaService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class VendaFrame extends JFrame {
    private LojaService lojaService;
    private JComboBox<Cliente> clienteCombo;
    private JList<Produto> produtosList;
    private DefaultListModel<Produto> produtosListModel;
    private JSpinner quantidadeSpinner;
    private JTextArea carrinhoArea;
    private JComboBox<String> pagamentoCombo;

    public VendaFrame(LojaService lojaService) {
        this.lojaService = lojaService;
        initUI();
    }

    private void initUI() {
        setTitle("Realizar Venda");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel superior - seleção de cliente
        JPanel clientePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        clientePanel.add(new JLabel("Cliente:"));
        clienteCombo = new JComboBox<>();
        atualizarClientes();
        clientePanel.add(clienteCombo);
        panel.add(clientePanel, BorderLayout.NORTH);

        // Painel central - produtos e carrinho
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Lista de produtos
        JPanel produtosPanel = new JPanel(new BorderLayout());
        produtosPanel.add(new JLabel("Produtos Disponíveis:"), BorderLayout.NORTH);
        produtosListModel = new DefaultListModel<>();
        atualizarProdutos();
        produtosList = new JList<>(produtosListModel);
        produtosList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        produtosPanel.add(new JScrollPane(produtosList), BorderLayout.CENTER);

        // Quantidade
        JPanel quantidadePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        quantidadePanel.add(new JLabel("Quantidade:"));
        quantidadeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        quantidadePanel.add(quantidadeSpinner);

        JButton adicionarBtn = new JButton("Adicionar ao Carrinho");
        adicionarBtn.addActionListener(this::adicionarAoCarrinho);
        quantidadePanel.add(adicionarBtn);

        produtosPanel.add(quantidadePanel, BorderLayout.SOUTH);
        centerPanel.add(produtosPanel);

        // Carrinho
        JPanel carrinhoPanel = new JPanel(new BorderLayout());
        carrinhoPanel.add(new JLabel("Carrinho de Compras:"), BorderLayout.NORTH);
        carrinhoArea = new JTextArea();
        carrinhoArea.setEditable(false);
        carrinhoPanel.add(new JScrollPane(carrinhoArea), BorderLayout.CENTER);
        centerPanel.add(carrinhoPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        // Painel inferior - pagamento e finalização
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.add(new JLabel("Forma de Pagamento:"));
        pagamentoCombo = new JComboBox<>(new String[]{"Dinheiro", "Cartão de Crédito", "Cartão de Débito", "PIX"});
        footerPanel.add(pagamentoCombo);

        JButton finalizarBtn = new JButton("Finalizar Venda");
        finalizarBtn.addActionListener(this::finalizarVenda);
        footerPanel.add(finalizarBtn);

        panel.add(footerPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void atualizarClientes() {
        clienteCombo.removeAllItems();
        lojaService.getClientes().forEach(clienteCombo::addItem);
    }

    private void atualizarProdutos() {
        produtosListModel.clear();
        lojaService.getProdutos().forEach(produtosListModel::addElement);
    }

    private void adicionarAoCarrinho(ActionEvent e) {
        Produto produto = produtosList.getSelectedValue();
        int quantidade = (int) quantidadeSpinner.getValue();

        if (produto != null) {
            carrinhoArea.append(String.format("%s x%d - R$ %.2f\n",
                    produto.getNome(),
                    quantidade,
                    produto.calcularPrecoComDesconto() * quantidade));
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void finalizarVenda(ActionEvent e) {
        Cliente cliente = (Cliente) clienteCombo.getSelectedItem();
        String formaPagamento = (String) pagamentoCombo.getSelectedItem();

        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carrinhoArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione produtos ao carrinho!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Aqui você implementaria a lógica para criar a venda
        JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        carrinhoArea.setText("");
    }
}