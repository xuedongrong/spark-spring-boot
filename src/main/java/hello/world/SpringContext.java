package hello.world;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "hello.world.service")
public class SpringContext {

    private static ConfigurableApplicationContext context;

    public synchronized static <T> T getBean(Class<T> tClass) {
        if (context == null || !context.isActive()) {
            SpringApplication application = new SpringApplication(SpringContext.class);
            application.setWebEnvironment(false);
            context = application.run();
        }

        return context.getBean(tClass);
    }
}



