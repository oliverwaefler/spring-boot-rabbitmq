package com.example.rabbitmq

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitMQConfig {

//	@Bean
//	fun jsonMessageConverter(): MessageConverter? {
//		return Jackson2JsonMessageConverter()
//	}
//
//	@Bean
//	fun rabbitTemplate(connectionFactory: ConnectionFactory): AmqpTemplate {
//		val rabbitTemplate = RabbitTemplate(connectionFactory)
//		rabbitTemplate.messageConverter = jsonMessageConverter()!!
//		return rabbitTemplate
//	}
}