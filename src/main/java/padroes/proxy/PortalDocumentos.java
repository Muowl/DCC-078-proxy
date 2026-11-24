package padroes.proxy;

public class PortalDocumentos {

    private final ServicoDocumentos servico;

    public PortalDocumentos(ServicoDocumentos servico) {
        this.servico = servico;
    }

    public String exibir(String codigo) {
        return "Documento " + codigo + ": " + servico.ler(codigo);
    }
}
