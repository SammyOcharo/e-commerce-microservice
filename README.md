# E-commerce Java Springboot Microservices Application

## Overview
This project implements a microservices-based e-commerce application with the following components:

- **API Gateway**: Acts as a single entry point for all client requests.
- **Discovery Server**: Handles service registration and discovery.
- **Config Server**: Centralized configuration management for all services.
- **Customer Microservice**: Manages customer registration and authentication (MongoDB).
- **Order Microservice**: Handles order creation and management (Postgres).
- **Product Microservice**: Manages product inventory and availability (Postgres).
- **Payment Microservice**: Processes payments (Postgres).
- **Notification Microservice**: Sends email notifications for order and payment events (MongoDB).
- **Event Streaming**: Kafka facilitates asynchronous communication between microservices.
- **Tracing**: Zipkin enables distributed tracing for monitoring inter-service communication.

The application is containerized using Docker and orchestrated through Docker Compose.


## Process Flow

1. **Customer Registration**:
   - The customer registers via the Customer Microservice.
   - Only registered customers can place orders.

2. **Order Placement**:
   - Customers place orders through the Order Microservice.
   - The Order Microservice communicates with the Product Microservice (via OpenFeign) to validate product availability.

3. **Kafka Event Publishing**:
   - Upon successful order placement, the Order Microservice publishes an event to Kafka.
   - The Notification Microservice consumes the event to send an email confirmation for the order.

4. **Payment Processing**:
   - The Order Microservice triggers payment processing through the Payment Microservice.
   - Once the payment is successful, the Payment Microservice publishes an event to Kafka.
   - The Notification Microservice sends a payment confirmation email.



## Architecture

### Microservices
- **Customer**: Handles user registration and stores data in MongoDB.
- **Product**: Manages product inventory with a Postgres database.
- **Order**: Validates orders and manages transactions.
- **Payment**: Processes payments and handles Postgres for payment transactions.
- **Notification**: Sends notifications using MailDev for development.

### Tools and Technologies
- **Event Streaming**: Kafka for inter-service communication.
- **Database**: Postgres and MongoDB.
- **Tracing**: Zipkin for distributed tracing.
- **Configuration**: Config Server for centralized management.
- **Service Discovery**: Eureka Discovery Server.


## Prerequisites

1. Docker and Docker Compose installed on your machine.
2. Java (JDK 17 or above) and Maven for microservices.

---

## Setup Instructions

### 1. Clone Repository
```bash
git clone <repository-url>
cd eCommerceApp
```

### 2. Configure Environment Variables
Set environment variables for each microservice (e.g., database credentials, Kafka configurations).

### 3. Run Docker Compose
Start all services using Docker Compose:
```bash
docker-compose up -d
```

### 4. Verify Services
- **API Gateway**: `http://localhost:8222`
- **Zipkin**: `http://localhost:9411`
- **MailDev**: `http://localhost:1080`
- **PgAdmin**: `http://localhost:5050`
- **Mongo Express**: `http://localhost:8081`

---

## Docker Compose Configuration

### Services

#### PostgreSQL
```yaml
postgres:
  container_name: ms_pg_sql
  image: postgres
  environment:
    POSTGRES_USER: <your_user>
    POSTGRES_PASSWORD: <your_password>
    PGDATA: /var/lib/postgresql/data
  volumes:
    - postgres:/var/lib/postgresql/data
  ports:
    - 5433:5432
  networks:
    - microservices-net
  restart: unless-stopped
```

#### MongoDB
```yaml
mongodb:
  container_name: ms_mongo_db
  image: mongo
  ports:
    - 27017:27017
  volumes:
    - mongo:/data
  environment:
      - MONGO_INITDB_ROOT_USERNAME=<your_user>
      - MONGO_INITDB_ROOT_PASSWORD=<your_password>
```

#### Kafka
```yaml
kafka:
  image: confluentinc/cp-kafka:latest
  container_name: ms_kafka
  ports:
    - 9092:9092
  depends_on:
    - zookeeper
  environment:
    KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1
    KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
    KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
    KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
  networks:
    - microservices-net
```

#### Zipkin
```yaml
zipkin:
  container_name: zipkin
  image: openzipkin/zipkin
  ports:
    - 9411:9411
  networks:
    - microservices-net
```

---

## Testing the Application

1. **Register a Customer**:
   - POST request to `/api/v1/customers`

2. **Place an Order**:
   - POST request to `/api/v1/orders`
   - Ensure the product exists and inventory is sufficient.

3. **Check Notifications**:
   - Access MailDev to verify order and payment email notifications.

4. **Trace Requests**:
   - Use Zipkin to trace inter-service communication.

---

## Monitoring and Debugging

- **Logs**: Check logs for each container using:
  ```bash
  docker logs <container_name>
  ```
- **Zipkin**: Visualize service communication at `http://localhost:9411`.

---

## Contributing
Contributions are welcome. Fork the repository and submit a pull request with detailed changes.

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.

