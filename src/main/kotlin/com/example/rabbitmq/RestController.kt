package com.example.rabbitmq

import com.beust.klaxon.Klaxon
import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController(val rabbitTemplate: RabbitTemplate,
					 @Value("\${rabbitmq.target.queue}") private var queueName: String = "job_queue",
					 @Value("\${rabbitmq.target.topic}") private var topicName: String = "pub_sub_topic",
					 @Value("\${rabbitmq.target.rpc}") private var rpcName: String = "rpc_topic") {
	companion object {
		private val logger = KotlinLogging.logger {}
	}

	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], value = ["/queue"])
	fun postQueue(@RequestBody myData: MyData) {
		logger.debug { "Sending $myData to queue - $queueName" }
		rabbitTemplate.convertAndSend(queueName, Klaxon().toJsonString(myData))
		logger.info { "Sent $myData to queue - $queueName" }
	}

	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], value = ["/topic"])
	fun postTopic(@RequestBody myData: MyData) {
		val fanoutExchange = "amq.fanout"
		logger.debug { "Sending $myData to topic - $fanoutExchange" }
		rabbitTemplate.convertAndSend(fanoutExchange, "", Klaxon().toJsonString(myData))
		logger.info { "Sent $myData topic: $fanoutExchange" }
	}

	@PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], value = ["/rpc"])
	fun postRPC(@RequestBody myData: MyData) {
		val directExchange = "amq.direct"
		logger.debug { "Sending $myData to topic - $directExchange" }
		rabbitTemplate.setReceiveTimeout(1000L)
		rabbitTemplate.setReplyTimeout(1000L)
		val response = rabbitTemplate.convertSendAndReceive(directExchange, "rpc.date", Klaxon().toJsonString(myData))
		logger.info { "Sent $myData destination: $directExchange response: $response" }
	}
}