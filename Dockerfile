FROM openjdk:20

LABEL authors="drishti"

# Install PostgreSQL
RUN apt-get update && \
    apt-get install -y postgresql postgresql-contrib && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set environment variables for PostgreSQL
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB shopping_cart

# Expose PostgreSQL port
EXPOSE 5432

# Create a directory for PostgreSQL data
RUN mkdir -p /var/lib/postgresql/data
VOLUME /var/lib/postgresql/data

# Expose Spring Boot port
EXPOSE 8080

# Add the Spring Boot application JAR file
ADD target/shopping-cart-backend.jar shopping-cart-backend.jar

# Start PostgreSQL and Spring Boot application
CMD service postgresql start && \
    sleep 10 && \
    psql -U postgres -c "ALTER USER ${POSTGRES_USER} WITH PASSWORD '${POSTGRES_PASSWORD}';" && \
    psql -U postgres -c "CREATE DATABASE ${POSTGRES_DB};" && \
    java -jar shopping-cart-backend.jar
