package hello.world;

import hello.world.service.Hello;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@SpringBootApplication(scanBasePackages = "hello.world.service")
public class SparkSpringWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparkSpringWebApplication.class, args);
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        SparkConf sparkConf = new SparkConf().setAppName(SparkApplication.class.getSimpleName());
        sparkConf.setMaster(sparkConf.get("spark.master", "local"));
        return new JavaSparkContext(sparkConf);
    }

    @Autowired
    private JavaSparkContext jsc;

    @Autowired
    private Hello hello;

    @GetMapping("start")
    public ResponseEntity start() {
        JavaRDD<String> rdd = jsc.parallelize(Arrays.asList("spark", "spring"), 2);

        List<String> strings = new ArrayList<>();
        strings.add(hello.hello("driver"));

        // Executor计算后返回结果到Driver
        List<String> collect = rdd.mapPartitions(new FlatMap()).collect();
        strings.addAll(collect);
        return ResponseEntity.ok(strings);
    }

    static class FlatMap implements FlatMapFunction<Iterator<String>, String> {
        @Override
        public Iterable<String> call(Iterator<String> iterator) {
            //Hello hello = new Hello();
            Hello hello = SpringContext.getBean(Hello.class);
            List<String> list = new ArrayList<>();
            while (iterator.hasNext()) {
                String text = iterator.next();
                list.add(hello.hello(text));
            }
            return list;
        }
    }

    @PreDestroy
    public void close() {
        jsc.close();
    }
}



