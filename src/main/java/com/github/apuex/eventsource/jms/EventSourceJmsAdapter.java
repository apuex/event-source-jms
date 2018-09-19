package com.github.apuex.eventsource.jms;

import com.github.apuex.eventsource.EventSourceAdapter;
import static com.github.apuex.protobuf.jms.JmsConverterConfig.DEFAULT_PRINCIPAL_NAME_FIELD;
import static com.github.apuex.protobuf.jms.JmsConverterConfig.DEFAULT_RESOURCE_URI_FIELD;

import org.springframework.jms.core.JmsTemplate;

import java.net.URI;
import java.security.Principal;

public class EventSourceJmsAdapter implements EventSourceAdapter {
  private String principalNameField = DEFAULT_PRINCIPAL_NAME_FIELD;
  private String resourceUriField = DEFAULT_RESOURCE_URI_FIELD;
  private JmsTemplate jmsTemplate;

  @Override
  public void publish(Object o) {
    jmsTemplate.convertAndSend(o);
  }

  @Override
  public void publish(Object event, Principal principal) {
    jmsTemplate.convertAndSend(event, message -> { if(null != principal)
      message.setStringProperty(principalNameField, principal.getName());
      return message;
    });
  }

  @Override
  public void publish(Object event, Principal principal, URI uri) {
    jmsTemplate.convertAndSend(event, message -> { if(null != principal)
      message.setStringProperty(principalNameField, principal.getName());
      message.setStringProperty(resourceUriField, uri.toString());
      return message;
    });
  }

  public void setPrincipalNameField(String principalNameField) {
    this.principalNameField = principalNameField;
  }

  public void setResourceUriField(String resourceUriField) {
    this.resourceUriField = resourceUriField;
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }
}
