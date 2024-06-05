FROM openjdk:20
LABEL authors="drishti"
EXPOSE 8080
ADD target/shopping-cart-backend.jar shopping-cart-backend.jar
ENTRYPOINT ["java", "-jar", "/shopping-cart-backend.jar"]