package model;

public abstract class Produto {
    protected String nome;
    protected String descricao;
    protected double preco;
    protected int quantidade;

    public Produto(String nome, String descricao, double preco, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public abstract double calcularPrecoComDesconto();

    // Getters e Setters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }
}