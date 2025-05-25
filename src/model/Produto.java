package model;

public abstract class Produto {
    private final String categoria;
    protected String nome;
    protected String descricao;
    protected double preco;
    protected int quantidade;

    public Produto(String nome, String descricao, double preco, int quantidade, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria; // fiz o construtor da categoria
    }

    public abstract double calcularPrecoComDesconto();

    // Getters e Setters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public int getQuantidade() { return quantidade; }

    public double getDesconto(){
        return 0;
    }

    public void setQuantidade(int novoEstoque) {
    }
    public String getcategoria() { return categoria;}// retorna a categoria como String


    // criei um objeto pra categoria
    public Object getCategoria() {
        return categoria;
    } // criei o get para receber os dados da categoria
}