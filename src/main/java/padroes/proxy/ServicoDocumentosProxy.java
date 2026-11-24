package padroes.proxy;

import java.util.HashMap;
import java.util.Map;

public class ServicoDocumentosProxy implements ServicoDocumentos {

    private static final String PREFIXO_CONFIDENCIAL = "CONF-";

    private final ServicoDocumentos real;
    private final Usuario usuario;
    private final Map<String, String> cache = new HashMap<>();

    public ServicoDocumentosProxy(ServicoDocumentos real, Usuario usuario) {
        this.real = real;
        this.usuario = usuario;
    }

    @Override
    public String ler(String codigo) {
        if (codigo.startsWith(PREFIXO_CONFIDENCIAL) && !usuario.isAdministrador()) {
            throw new SecurityException("Acesso negado a " + codigo + " para " + usuario.getNome());
        }
        String conteudo = cache.get(codigo);
        if (conteudo == null) {
            conteudo = real.ler(codigo);
            cache.put(codigo, conteudo);
        }
        return conteudo;
    }
}
