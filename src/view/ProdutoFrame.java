package view;

import model.*;
import Controller.LojaService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ProdutoFrame extends JFrame {
    private LojaService lojaService;
    private JComboBox<String> tipoProdutoCombo, categoriaCombo;
    private JTextField nomeField, descricaoField, precoField, quantidadeField;

    public ProdutoFrame(LojaService lojaService) {
        this.lojaService = lojaService;
        initUI();
    }

    private void initUI() {
        setTitle("Cadastro de Produtos");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos do formulário
        panel.add(new JLabel("Tipo de Produto:"));
        tipoProdutoCombo = new JComboBox<>(new String[]{"","Físico", "Digital"});
        panel.add(tipoProdutoCombo);

        panel.add(new JLabel("Categoria:"));
        categoriaCombo = new JComboBox<>(new String[]{"","Eletronicos", "Modas", "Relogios e Joias", "Brinquedos", "Livros"});
        panel.add(categoriaCombo);

        panel.add(new JLabel("Produto:"));
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

        // Botão Consultar Estoque
        JButton consultarBtn = new JButton("Consultar Estoque");
        consultarBtn.addActionListener(this::consultarEstoque);

        panel.add(new JLabel());
        panel.add(cadastrarBtn);

        panel.add(new JLabel());
        panel.add(consultarBtn);

        add(panel);
    }

    private void cadastrarProduto(ActionEvent e) {
        try {
            String tipo = (String) tipoProdutoCombo.getSelectedItem();
            String nome = nomeField.getText();
            String categoria = (String) categoriaCombo.getSelectedItem(); // Pega a categoria selecionada
            String descricao = descricaoField.getText();
            double preco = Double.parseDouble(precoField.getText());
            int quantidade = Integer.parseInt(quantidadeField.getText());

            // Validação dos campos obrigatórios
            if(tipo == null || tipo.isEmpty() || categoria == null || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Selecione o tipo e a categoria do produto!",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Produto produto;
            if (tipo.equals("Físico")) {
                produto = new ProdutoFisico(nome, descricao, preco, quantidade, categoria); // Adiciona categoria
            } else {
                produto = new ProdutoDigital(nome, descricao, preco, quantidade, categoria); // Adiciona categoria
            }

            lojaService.cadastrarProduto(produto);
            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Preço e quantidade devem ser números válidos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarEstoque(ActionEvent e) {
        List<Produto> produtos = lojaService.listarProdutos();

        if(produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum produto cadastrado no estoque!",
                    "Estoque",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Cria modelo de tabela com categoria
        String[] colunas = {"Produto", "Categoria", "Valor", "Quantidade"};
        Object[][] dados = new Object[produtos.size()][4];

        for(int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            dados[i][0] = p.getNome();
            dados[i][1] = p.getCategoria(); // Mostra a categoria
            dados[i][2] = String.format("R$ %.2f", p.getPreco());
            dados[i][3] = p.getQuantidade();
        }

        JTable tabela = new JTable(dados, colunas);
        JScrollPane scrollPane = new JScrollPane(tabela);

        JOptionPane.showMessageDialog(this, scrollPane,
                "Estoque - " + produtos.size() + " itens",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void limparCampos() {
        nomeField.setText("");
        descricaoField.setText("");
        precoField.setText("");
        quantidadeField.setText("");
        tipoProdutoCombo.setSelectedIndex(0);
        categoriaCombo.setSelectedIndex(0);
    }
}