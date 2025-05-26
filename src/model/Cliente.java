package model;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String dataNascimento;
    private String email;

    public Cliente(String nome, String cpf, String telefone, String endereco, String dataNascimento,
                   String email, boolean receberPromocoes){
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

}
