package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private Controller.LojaService lojaService;

    public MainMenu() {
        lojaService = new Controller.LojaService();
        initUI();
    }

    private void initUI() {
        setTitle("Sistema de Gestão de Loja");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnProdutos = new JButton("Gerenciar Produtos");
        JButton btnClientes = new JButton("Gerenciar Clientes");
        JButton btnVendas = new JButton("Realizar Vendas");

        btnProdutos.addActionListener(e -> new ProdutoFrame(lojaService).setVisible(true));
        btnClientes.addActionListener(e -> new ClienteFrame(lojaService).setVisible(true));
        btnVendas.addActionListener(e -> new VendaFrame(lojaService).setVisible(true));

        panel.add(btnProdutos);
        panel.add(btnClientes);
        panel.add(btnVendas);

        add(panel);
    }
}