version: '3.8'

services:
  mysqldb:
    image: mysql:5.7
    restart: unless-stopped
    env_file: ./.env
    environment:
      - MYSQL_ROOT_PASSWORD=${MYSQLDB_ROOT_PASSWORD}
      - MYSQL_DATABASE=${MYSQLDB_DATABASE}
      - MYSQL_USER=${MYSQLDB_USER}  # N'ajoutez pas ici `root` car c'est déjà défini
    ports:
      - "${MYSQLDB_LOCAL_PORT}:${MYSQLDB_DOCKER_PORT}"
    volumes:
      - db:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 3

  app:
    depends_on:
      - mysqldb
    image: meriembensalem/backend-spring:1.0.0
    restart: on-failure
    env_file: ./.env
    ports:
      - "${SPRING_LOCAL_PORT}:${SPRING_DOCKER_PORT}"
    environment:
      SPRING_APPLICATION_JSON: '{
        "spring.datasource.url": "jdbc:mysql://mysqldb:${MYSQLDB_DOCKER_PORT}/${MYSQLDB_DATABASE}?createDatabaseIfNotExist=true",
        "spring.datasource.username": "${MYSQLDB_USER}",
        "spring.datasource.password": "${MYSQLDB_ROOT_PASSWORD}",
        "spring.jpa.properties.hibernate.dialect": "org.hibernate.dialect.MySQLDialect",
        "spring.jpa.hibernate.ddl-auto": "update"
      }'
    stdin_open: true
    tty: true

volumes:
  db: {}