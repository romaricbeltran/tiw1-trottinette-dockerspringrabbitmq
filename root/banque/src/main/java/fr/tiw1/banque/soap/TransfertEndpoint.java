package fr.tiw1.banque.soap;

import fr.tiw1.banque.services.CompteService;
import fr.univ_lyon1.tiw1_is.banque.service.ObjectFactory;
import fr.univ_lyon1.tiw1_is.banque.service.TransfertRequest;
import fr.univ_lyon1.tiw1_is.banque.service.TransfertResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class TransfertEndpoint {
    public final static String NAMESPACE_URI = "http:/univ-lyon1.fr/tiw1-is/banque/service";

    private final static ObjectFactory banqueObjectFactory = new ObjectFactory();

    @Autowired
    private CompteService compteService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "transfertRequest")
    @ResponsePayload
    public TransfertResponse transfert(@RequestPayload TransfertRequest transfert) {
        boolean ok = compteService.transfert(transfert.getFrom(), transfert.getTo(), transfert.getAutorisation(), transfert.getMontant());
        TransfertResponse response = banqueObjectFactory.createTransfertResponse();
        response.setTransfertOk(ok);
        return response;
    }

}
