package padroes.proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PortalDocumentosTest {

    private ServicoDocumentosReal real;

    @BeforeEach
    void preparar() {
        real = new ServicoDocumentosReal();
        real.adicionar("DOC-1", "Manual do usuário");
        real.adicionar("CONF-1", "Folha de pagamento");
    }

    private PortalDocumentos portalPara(Usuario usuario) {
        return new PortalDocumentos(new ServicoDocumentosProxy(real, usuario));
    }

    @Test
    void deveExibirDocumentoPublicoParaUsuarioComum() {
        PortalDocumentos portal = portalPara(new Usuario("Ana", false));

        assertEquals("Documento DOC-1: Manual do usuário", portal.exibir("DOC-1"));
    }

    @Test
    void deveNegarDocumentoConfidencialParaUsuarioComum() {
        PortalDocumentos portal = portalPara(new Usuario("Ana", false));

        assertThrows(SecurityException.class, () -> portal.exibir("CONF-1"));
        assertEquals(0, real.getTotalLeituras());
    }

    @Test
    void devePermitirDocumentoConfidencialParaAdministrador() {
        PortalDocumentos portal = portalPara(new Usuario("Bia", true));

        assertEquals("Documento CONF-1: Folha de pagamento", portal.exibir("CONF-1"));
    }

    @Test
    void deveReutilizarCacheEvitandoLeiturasRepetidas() {
        PortalDocumentos portal = portalPara(new Usuario("Ana", false));

        portal.exibir("DOC-1");
        portal.exibir("DOC-1");
        portal.exibir("DOC-1");

        assertEquals(1, real.getTotalLeituras());
    }

    @Test
    void servicoRealSemProxyDeveLerSempreDoArmazenamento() {
        new PortalDocumentos(real).exibir("DOC-1");
        new PortalDocumentos(real).exibir("DOC-1");

        assertEquals(2, real.getTotalLeituras());
    }

    @Test
    void naoDeveGuardarEmCacheDocumentoInexistente() {
        PortalDocumentos portal = portalPara(new Usuario("Ana", false));

        assertThrows(IllegalArgumentException.class, () -> portal.exibir("DOC-9"));
        real.adicionar("DOC-9", "Novo");

        assertEquals("Documento DOC-9: Novo", portal.exibir("DOC-9"));
    }
}
