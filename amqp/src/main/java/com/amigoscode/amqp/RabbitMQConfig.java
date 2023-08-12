package com.amigoscode.amqp;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

  private final ConnectionFactory connectionFactory;

  /**
   * Provides synchronous send and receive
   * convert and send a Java object to an Amqp Message
   * receive and convert an Amqp Message to Java object
   * @return AmqpTemplate
   */
  @Bean
  public AmqpTemplate amqpTemplate() {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jacksonConverter());
    return rabbitTemplate;
  }

  /**
   * receive the message by the consumer from the queue
   * @return SimpleRabbitListenerContainerFactory
   */
  @Bean
  public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setMessageConverter(jacksonConverter());
    return factory;
  }

  @Bean
  public MessageConverter jacksonConverter() {
    MessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
    return jackson2JsonMessageConverter;
  }

}