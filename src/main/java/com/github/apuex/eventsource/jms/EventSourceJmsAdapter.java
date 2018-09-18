package com.github.apuex.eventsource.jms;

import com.github.apuex.eventsource.EventSourceAdapter;
import org.springframework.jms.core.JmsTemplate;

import java.security.Principal;

public class EventSourceJmsAdapter implements EventSourceAdapter {
  private final JmsTemplate jmsTemplate;

  public EventSourceJmsAdapter(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void publish(Object o) {
    jmsTemplate.convertAndSend(o);
  }

  @Override
  public void publish(Object event, Principal principal) {
    jmsTemplate.convertAndSend(event, message -> { if(null != principal)
      message.setStringProperty("principalName", principal.getName());
      return message;
    });
  }
}
