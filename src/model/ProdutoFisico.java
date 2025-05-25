package model;

public class ProdutoFisico extends Produto {
    public ProdutoFisico(String nome, String descricao, double preco, int quantidade) {
        super(nome, descricao, preco, quantidade);
    }

    @Override
    public double calcularPrecoComDesconto() {
        return preco * 0.95; // 5% de desconto
    }

    @Override
    public double getDesconto() {
        return 5.0;
    }
}