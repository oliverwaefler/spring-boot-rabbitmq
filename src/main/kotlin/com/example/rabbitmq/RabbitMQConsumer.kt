package com.example.rabbitmq

import mu.KotlinLogging
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class RabbitMQConsumer {
	companion object {
		private val logger = KotlinLogging.logger {}
	}

	@RabbitListener(queues = ["\${rabbitmq.target.queue}"])
	fun consumeQueue1(message: MyData) {
		logger.info { "consumeQueue1: $message" }
	}

	@RabbitListener(queues = ["\${rabbitmq.target.queue}"])
	fun consumeQueue2(message: MyData) {
		logger.info { "consumeQueue2: $message" }
	}

	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "tmpPubSub1", autoDelete = true.toString()),
					exchange = Exchange(value = "amq.fanout", type = ExchangeTypes.FANOUT)
			)]
	)
	fun consumeTopic1(message: MyData) {
		logger.info { "consumeTopic1: $message" }
	}

	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "tmpPubSub2", autoDelete = true.toString()),
					exchange = Exchange(value = "amq.fanout", type = ExchangeTypes.FANOUT)
			)]
	)
	fun consumeTopic2(message: MyData) {
		logger.info { "consumeTopic2: $message" }
	}

	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "tmpRequestResponse", autoDelete = true.toString()),
					exchange = Exchange(value = "amq.direct"), key = ["rpc.date"]
			)]
	)
	fun consumeRPC(message: MyData): String {
		logger.info { "consumeRPC: $message" }
		val response = Date().toString()
		logger.info { "returning: $response" }
		return response
	}
}