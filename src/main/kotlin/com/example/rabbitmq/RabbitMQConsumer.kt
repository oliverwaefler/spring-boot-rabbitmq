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
		logger.info { "consumeQueue1 $message" }
	}

	@RabbitListener(queues = ["\${rabbitmq.target.queue}"])
	fun consumeQueue2(message: MyData) {
		logger.info { "consumeQueue2 $message" }
	}

	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "topic1", durable = true.toString()),
					exchange = Exchange(value = "amq.topic", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
					key = ["*.bla"]
			)]
	)
	fun listenToTopic1(message: MyData) {
		logger.info { "listenToTopic1: $message" }
	}

	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "tmp", durable = false.toString(), autoDelete = true.toString()),
					exchange = Exchange(value = "amq.topic", ignoreDeclarationExceptions = true.toString(), type = ExchangeTypes.TOPIC),
					key = ["bla.*"]
			)]
	)
	fun listenToTopic2(message: MyData) {
		logger.info { "listenToTopic2: $message" }
	}

	//PUB SUB mit Fanout
	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "tmpPubSub1", autoDelete = true.toString()),
					exchange = Exchange(value = "pubsub", type = ExchangeTypes.FANOUT)
			)]
	)
	fun listenToPubSub1(message: MyData) {
		logger.info { "listenToPubSub1: $message" }
	}

	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "tmpPubSub2", autoDelete = true.toString()),
					exchange = Exchange(value = "pubsub", type = ExchangeTypes.FANOUT)
			)]
	)
	fun listenToPubSub2(message: MyData) {
		logger.info { "listenToPubSub2: $message" }
	}

	//RPC
	@RabbitListener(
			bindings = [QueueBinding(
					value = Queue(value = "tmpRequestResponse", autoDelete = true.toString()),
					exchange = Exchange(value = "amq.direct"), key = ["rpc.date"]
			)]
	)
	fun listenToRPC(message: MyData): String {
		logger.info { "listenToRPC: $message" }
		val response = Date().toString()
		logger.info { "returning: $response" }
		return response
	}
}