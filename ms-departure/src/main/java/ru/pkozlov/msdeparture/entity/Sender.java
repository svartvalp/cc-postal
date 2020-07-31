package ru.pkozlov.msdeparture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import java.util.Queue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sender {

    private Long id;

    private JmsTemplate jmsTemplate;
    private Queue queue;

    public void sendId() {
        jmsTemplate.send((Destination) queue, s -> s.createTextMessage("Id: " + id));
    }
}
