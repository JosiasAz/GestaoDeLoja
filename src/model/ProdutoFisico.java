package model;

public class ProdutoFisico extends Produto {
    public ProdutoFisico(String nome, String descricao, double preco, int quantidade, String categoria) {
        super(nome, descricao, preco, quantidade, categoria);// adicionei a categoria nos modelos
    }

    @Override
    public double calcularPrecoComDesconto() {
        return preco * 0.95; // 5% de desconto
    }

    @Override
    public double getDesconto() {
        return 5.0;
    }

    public String toString(){
        return String.format("Nome: %s \n Descrição: %s \n Preço: R$%.2f \n Quantidade: %d", nome,descricao,preco,quantidade);
    }
}