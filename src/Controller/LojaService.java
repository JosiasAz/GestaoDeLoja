package Controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class LojaService {
    private List<Produto> produtos;
    private List<Cliente> clientes;
    private List<Venda> vendas;

    public LojaService() {
        produtos = new ArrayList<>();
        clientes = new ArrayList<>();
        vendas = new ArrayList<>();
    }

    public void cadastrarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void registrarVenda(Venda venda) {
        vendas.add(venda);
    }

    // Getters
    public List<Produto> getProdutos() { return new ArrayList<>(produtos); }
    public List<Cliente> getClientes() { return new ArrayList<>(clientes); }
}