package tiw1.SpringBootTP3.rabbitmq;

import fr.univ_lyon1.tiw1_is.emprunt.soap.TransfertRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class RabbitService {

    @Value("${rabbit-mq.banque-queue}")
    private String BANQUE_QUEUE;

    private Logger _log = LoggerFactory.getLogger(RabbitService.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void OrderMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(TransfertRequest transfertRequest) {
        _log.info("Sending transfertRequest, from {}, to {}, for mount {}, for emprunt {}",
            transfertRequest.getFrom(),
            transfertRequest.getTo(),
            transfertRequest.getMontant(),
            transfertRequest.getIdEmprunt()
        );
        this.rabbitTemplate.convertAndSend(BANQUE_QUEUE, transfertRequest);
    }

}
