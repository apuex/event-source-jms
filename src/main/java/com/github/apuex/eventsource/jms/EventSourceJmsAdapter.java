package com.github.apuex.eventsource.jms;

import com.github.apuex.eventsource.EventSourceAdapter;
import org.springframework.jms.core.JmsTemplate;

public class EventSourceJmsAdapter implements EventSourceAdapter {
  private final JmsTemplate jmsTemplate;

  public EventSourceJmsAdapter(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }

  @Override
  public void publish(Object o) {
    jmsTemplate.convertAndSend(o);
  }
}
