package fr.tiw1.banque.soap;

import fr.tiw1.banque.services.CompteService;
import localhost._9090.ws.banque.ObjectFactory;
import localhost._9090.ws.banque.TransfertRequest;
import localhost._9090.ws.banque.TransfertResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class TransfertEndpoint {
    public final static String NAMESPACE_URI = "http:/univ-lyon1.fr/tiw1-is/banque/service";
    private final static Logger LOGGER_BANQUE = LoggerFactory.getLogger(TransfertEndpoint.class);
    private final static ObjectFactory banqueObjectFactory = new ObjectFactory();

    @Autowired
    private CompteService compteService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "transfertRequest")
    @ResponsePayload
    public TransfertResponse transfert(@RequestPayload TransfertRequest transfert) {
        LOGGER_BANQUE.info("TRANSFERT");
        boolean ok = compteService.transfert(transfert.getFrom(), transfert.getTo(), transfert.getAutorisation(), transfert.getMontant());
        TransfertResponse response = banqueObjectFactory.createTransfertResponse();
        response.setTransfertOk(ok);
        return response;
    }

}
