package model;

public class ProdutoDigital extends Produto {
    public ProdutoDigital(String nome, String descricao, double preco, int quantidade) {
        super(nome, descricao, preco, quantidade);
    }

    @Override
    public double calcularPrecoComDesconto() {
        return preco * 0.90; // 10% de desconto
    }

    public String toString(){
        return String.format("Nome: %s \n Descrição: %s \n Preço: R$%.2f \n Quantidade: %d", nome,descricao,preco,quantidade);
    }

}