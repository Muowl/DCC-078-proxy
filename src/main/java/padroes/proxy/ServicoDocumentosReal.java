package padroes.proxy;

import java.util.HashMap;
import java.util.Map;

public class ServicoDocumentosReal implements ServicoDocumentos {

    private final Map<String, String> documentos = new HashMap<>();
    private int totalLeituras;

    public void adicionar(String codigo, String conteudo) {
        documentos.put(codigo, conteudo);
    }

    @Override
    public String ler(String codigo) {
        String conteudo = documentos.get(codigo);
        if (conteudo == null) {
            throw new IllegalArgumentException("Documento inexistente: " + codigo);
        }
        totalLeituras++;
        return conteudo;
    }

    public int getTotalLeituras() {
        return totalLeituras;
    }
}
