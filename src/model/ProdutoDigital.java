package model;

public class ProdutoDigital extends Produto {
    public ProdutoDigital(String nome, String descricao, double preco, int quantidade, String categoria) {
        super(nome, descricao, preco, quantidade, categoria); // adicionei a categoria para que possa retorna na tabela
    }

    @Override
    public double calcularPrecoComDesconto() {
        return preco * 0.90; // 10% de desconto
    }

    public String toString(){
        return String.format("Nome: %s \n Descrição: %s \n Preço: R$%.2f \n Quantidade: %d", nome,descricao,preco,quantidade);
    }

    @Override
    public double getDesconto() {
        return 10.0;
    }


}