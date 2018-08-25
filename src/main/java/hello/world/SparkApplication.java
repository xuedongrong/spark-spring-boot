package hello.world;

import hello.world.service.Hello;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SparkApplication {

    public static void main(String[] args) {
        new SparkApplication().run(args);
    }

    private void run(String... args) {
        SparkConf sparkConf = new SparkConf().setAppName(SparkApplication.class.getSimpleName());
        sparkConf.setMaster(sparkConf.get("spark.master", "local"));

        try (JavaSparkContext jsc = new JavaSparkContext(sparkConf)) {
            JavaRDD<String> rdd = jsc.parallelize(Arrays.asList("spark", "spring"), 2);

            // Executor计算后返回结果到Driver
            List<String> strings = rdd.mapPartitions(new FlatMap()).collect();

            Hello hello = SpringContext.getBean(Hello.class);
            System.out.println(hello.hello("driver"));
            for (String string : strings) {
                System.out.println(string);
            }
        }
    }

    static class FlatMap implements FlatMapFunction<Iterator<String>, String> {
        @Override
        public Iterable<String> call(Iterator<String> iterator) {
            Hello hello = SpringContext.getBean(Hello.class);
            List<String> list = new ArrayList<>();
            while (iterator.hasNext()) {
                String text = iterator.next();
                list.add(hello.hello(text));
            }
            return list;
        }
    }
}
