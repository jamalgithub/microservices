# Microservices
![Screenshot 2021-11-30 at 12 32 51](https://user-images.githubusercontent.com/40702606/144061535-7a42e85b-59d6-4f7f-9c35-18a48b49e6de.png)

# Run ower microservices:
------------------------
mvn spring-boot:run

ou

java -jar eureka-server\target\eureka-server-1.0-SNAPSHOT.jar

java -jar apigw\target\apigw-1.0-SNAPSHOT.jar

java -jar customer\target\customer-1.0-SNAPSHOT.jar

java -jar fraud\target\fraud-1.0-SNAPSHOT.jar

java -jar notification\target\notification-1.0-SNAPSHOT.jar

# Packaging OCI images:
----------------------
(class + ressources + metadata) ===jar===> jar file ===build===> image docker
application-profile.yml
SPRING_PROFILES_ACTIVE

docker login
cat .docker\config.json
docker compose stop
docker compose start

docker pull amigoscode/kubernetes:frontend-v1

mvn clean package -P build-docker-image   //activate the profile "build-docker-image", so the run submodule will have the plugin "jib-maven-plugin" 
docker compose -f docker-compose-profile.yml pull
docker compose -f docker-compose-profile.yml up -d
docker compose -f docker-compose-profile.yml down