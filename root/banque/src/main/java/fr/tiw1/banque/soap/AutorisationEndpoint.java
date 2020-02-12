package fr.tiw1.banque.soap;

import fr.tiw1.banque.services.CompteService;

import localhost._9090.ws.banque.AutorisationRequest;
import localhost._9090.ws.banque.AutorisationResponse;
import localhost._9090.ws.banque.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AutorisationEndpoint {
    public final static String NAMESPACE_URI_AUTORISATION = "http:/univ-lyon1.fr/tiw1-is/banque/autorisation";

    private final static ObjectFactory banqueObjectFactory = new ObjectFactory();
    private static final Logger LOG = LoggerFactory.getLogger(AutorisationEndpoint.class);

    @Autowired
    private CompteService compteService;

    @PayloadRoot(namespace = NAMESPACE_URI_AUTORISATION, localPart = "autorisationRequest")
    @ResponsePayload
    public AutorisationResponse autorisation(@RequestPayload AutorisationRequest autorisation) {
        LOG.info("AUTORISATION !!!!!!!!!!!");
        long autorisationId = compteService.autorisation(autorisation.getFrom(), autorisation.getTo(), autorisation.getMontant());

        AutorisationResponse response = banqueObjectFactory.createAutorisationResponse();
        response.setAutorisationOk(autorisationId != 0);
        response.setIdEmprunt(autorisation.getIdEmprunt());
        response.setNumeroAutorisation(autorisationId);
        return response;
    }

}
