# spark-spring-boot
Spark集成Sparing-Boot

## 依赖
* jdk1.7

## 运行
```bash
spark-submit \  
  --class hello.world.SparkApplication \  
  --master yarn \  
  --packages org.springframework.boot:spring-boot-starter:1.5.13.RELEASE \  
  --exclude-packages org.springframework.boot:spring-boot-starter-logging \
  spark-spring-boot-1.0-SNAPSHOT.jar  
```
