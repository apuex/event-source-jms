package com.github.apuex.eventsource.jms;

import com.github.apuex.eventsource.EventSourceAdapter;

import static com.github.apuex.protobuf.jms.JmsConverterConfig.DEFAULT_PRINCIPAL_NAME_FIELD;
import static com.github.apuex.protobuf.jms.JmsConverterConfig.DEFAULT_SERVICE_URI_FIELD;

import org.springframework.jms.core.JmsTemplate;

import java.net.URI;
import java.security.Principal;
import java.util.Calendar;
import java.util.TimeZone;

public class EventSourceJmsAdapter implements EventSourceAdapter {
  private String principalNameField = DEFAULT_PRINCIPAL_NAME_FIELD;
  private String serviceUriField = DEFAULT_SERVICE_URI_FIELD;
  private JmsTemplate jmsTemplate;

  @Override
  public void publish(Object o) {
    jmsTemplate.convertAndSend(o);
  }

  @Override
  public void publish(Object event, Principal principal) {
    final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    jmsTemplate.convertAndSend(event, message -> {
      message.setJMSTimestamp(calendar.getTimeInMillis());
      if (null != principal) {
        message.setStringProperty(principalNameField, principal.getName());
      }
      return message;
    });
  }

  @Override
  public void publish(Object event, Principal principal, URI service) {
    final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    jmsTemplate.convertAndSend(event, message -> {
      message.setJMSTimestamp(calendar.getTimeInMillis());
      if (null != principal) {
        message.setStringProperty(principalNameField, principal.getName());
      }
      if (null != service) {
        message.setStringProperty(serviceUriField, service.toString());
      }
      return message;
    });
  }

  public void setPrincipalNameField(String principalNameField) {
    this.principalNameField = principalNameField;
  }

  public void setServiceUriField(String serviceUriField) {
    this.serviceUriField = serviceUriField;
  }

  public void setJmsTemplate(JmsTemplate jmsTemplate) {
    this.jmsTemplate = jmsTemplate;
  }
}
