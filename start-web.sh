spark-submit \
 --class hello.world.SpringSparkWebApplication \
 --master yarn \
 --packages org.springframework.boot:spring-boot-starter-web:1.5.13.RELEASE,org.springframework.boot:spring-boot-starter-jetty:1.5.13.RELEASE \
 --exclude-packages org.springframework.boot:spring-boot-starter-logging,org.springframework.boot:spring-boot-starter-tomcat \
 spark-spring-boot-1.0-SNAPSHOT.jar
