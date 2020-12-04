# spring-boot-rabbitmq

## Description

Simple spring boot application to interact with a RabbitMQ message broker using annotations.

## Getting Started

### Installing

```
 docker pull rabbitmq
 docker run -d --hostname my-rabbit -p 15672:15672 -p 5672:5672 --name some-rabbit rabbitmq:3
 docker exec -it some-rabbit2 /bin/bash
 rabbitmq-plugins enable rabbitmq_management
 rabbitmqctl add_user admin mypassword
 rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"
 rabbitmqctl set_user_tags admin administrator
```

## Authors

Oliver W. - email: oliverwaefler@gmail.com
