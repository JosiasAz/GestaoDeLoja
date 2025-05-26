package view;

import model.*;
import Controller.LojaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable carrinhoTable;
    private DefaultTableModel carrinhoTableModel;
    private JLabel totalLabel;


    public VendaFrame(LojaService lojaService) {
        this.lojaService = lojaService;
        initUI();
    }

    private void removerItemSelecionado() {
        int linhaSelecionada = carrinhoTable.getSelectedRow();
        if (linhaSelecionada != -1) {
            carrinhoTableModel.removeRow(linhaSelecionada);
            atualizarValorTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
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
        String[] colunas = {"Produto", "Quantidade", "Valor Unitário", "Desconto (%)", "Valor Líquido"};

        carrinhoTableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // nenhuma célula será editável
            }
        };

        carrinhoTable = new JTable(carrinhoTableModel);
        carrinhoPanel.add(new JScrollPane(carrinhoTable), BorderLayout.CENTER);
        JButton removerBtn = new JButton("Remover do Carrinho");
        removerBtn.addActionListener(e -> removerItemSelecionado());
        carrinhoPanel.add(removerBtn, BorderLayout.SOUTH);  // ADICIONE ESTA LINHA


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
        totalLabel = new JLabel("Valor Total: R$ 0.00");
        footerPanel.add(Box.createHorizontalStrut(20)); // Espaço entre botões
        footerPanel.add(totalLabel);


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
            if (quantidade > produto.getQuantidade()) {
                JOptionPane.showMessageDialog(this,
                        "Quantidade solicitada (" + quantidade + ") excede o estoque disponível (" + produto.getQuantidade() + ").",
                        "Estoque insuficiente",
                        JOptionPane.WARNING_MESSAGE);
                return; // Impede a adição ao carrinho
            }

            double valorUnitario = produto.getPreco();
            double desconto = produto.getDesconto(); // Ex: 10.0
            double valorComDesconto = valorUnitario * (1 - desconto / 100);
            double valorTotalItem = valorComDesconto * quantidade;

            carrinhoTableModel.addRow(new Object[]{
                    produto.getNome(),
                    quantidade,
                    String.format("R$ %.2f", valorUnitario),
                    String.format("%.1f%%", desconto),
                    String.format("R$ %.2f", valorTotalItem)
            });

            atualizarValorTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private Produto encontrarProdutoPorNome(String nome) {
        List<Produto> produtos = lojaService.getProdutos();
        for (Produto p : produtos) {
            if (p.getNome().equals(nome)) {
                return p;
            }
        }
        return null;
    }


    private void finalizarVenda(ActionEvent e) {
        Cliente cliente = (Cliente) clienteCombo.getSelectedItem();
        String formaPagamento = (String) pagamentoCombo.getSelectedItem();

        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carrinhoTableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione produtos ao carrinho!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Atualizar estoque
        for (int i = 0; i < carrinhoTableModel.getRowCount(); i++) {
            String nomeProduto = carrinhoTableModel.getValueAt(i, 0).toString();
            int quantidadeVendida = Integer.parseInt(carrinhoTableModel.getValueAt(i, 1).toString());

            Produto produto = encontrarProdutoPorNome(nomeProduto);
            if (produto != null) {
                int novoEstoque = produto.getQuantidade() - quantidadeVendida;
                produto.setQuantidade(novoEstoque);
            }
        }

        carrinhoTableModel.setRowCount(0); // limpa a tabela
        atualizarValorTotal();

        JOptionPane.showMessageDialog(this, "Venda finalizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        atualizarProdutos(); // atualiza a lista visual dos produtos (quantidades novas)
    }


    private void atualizarValorTotal() {
        double total = 0.0;
        for (int i = 0; i < carrinhoTableModel.getRowCount(); i++) {
            String valorStr = carrinhoTableModel.getValueAt(i, 4).toString(); // Valor Líquido
            valorStr = valorStr.replace("R$", "").trim().replace(",", ".");
            total += Double.parseDouble(valorStr);
        }
        totalLabel.setText(String.format("Valor Total: R$ %.2f", total));
    }



}