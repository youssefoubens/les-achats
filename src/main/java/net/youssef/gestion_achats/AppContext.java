package net.youssef.gestion_achats;

import org.springframework.context.ApplicationContext;

public class AppContext {

    private static ApplicationContext context;

    // Set the ApplicationContext
    public static void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    // Get the ApplicationContext
    public static ApplicationContext getInstance() {
        return context;
    }

    // Get a bean from the context
    public static <T> T getBean(Class<T> beanClass) {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext is not initialized.");
        }
        return context.getBean(beanClass);
    }
}
