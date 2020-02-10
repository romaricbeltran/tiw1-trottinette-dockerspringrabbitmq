package fr.tiw1.banque.rabbitmq;

import fr.tiw1.banque.services.CompteService;

import localhost._9090.ws.banque.ObjectFactory;
import localhost._9090.ws.banque.TransfertRequest;
import localhost._9090.ws.banque.TransfertResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class RabbitService {

    private Logger _log = LoggerFactory.getLogger(RabbitService.class);

    @Autowired
    private CompteService compteService;
    private final static ObjectFactory banqueObjectFactory = new ObjectFactory();

    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queuesToDeclare = @Queue(name = "banque-queue"))
    public void receive(TransfertRequest transfert) {
        _log.info("A transfert has been requested, from {}, to {}, numeroAutorisation {}, mount {}",
            transfert.getFrom(),
            transfert.getTo(),
            transfert.getAutorisation(),
            transfert.getMontant()
        );
        boolean ok = compteService.transfert(transfert.getFrom(), transfert.getTo(), transfert.getAutorisation(), transfert.getMontant());
        TransfertResponse response = banqueObjectFactory.createTransfertResponse();
        response.setTransfertOk(ok);
        this.sendOrder(response, transfert.getResponseQueue());
    }

    @Autowired
    public void OrderMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void sendOrder(TransfertResponse transfertResponse, String responseQueue) {
        _log.info("Response send "+transfertResponse.isTransfertOk());
        this.rabbitTemplate.convertAndSend(responseQueue, transfertResponse);
    }

}
