package padroes.proxy;

public class Usuario {

    private final String nome;
    private final boolean administrador;

    public Usuario(String nome, boolean administrador) {
        this.nome = nome;
        this.administrador = administrador;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAdministrador() {
        return administrador;
    }
}
