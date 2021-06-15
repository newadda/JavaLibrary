package org.onecell.example.spring.message;

import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class MessageSourceSingleton {
    static private final String DEFAULT_ENCODEING = "UTF-8";
    static private final String[] ERRORMESSAGES_PROPERTIES_PATH={"classpath:ValidationMessages","classpath:/org/one/lib/ValidationMessages","classpath:/org/waterworks/lib/ValidationMessages"
            ,"classpath:/org/waterworks/lib/Messages","classpath:/org/hibernate/validator/ValidationMessages"};
    ReloadableResourceBundleMessageSource resourceBundleMessageSource;

    private MessageSourceSingleton()
    {
        resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames(ERRORMESSAGES_PROPERTIES_PATH);
        resourceBundleMessageSource.setDefaultEncoding(DEFAULT_ENCODEING);
        resourceBundleMessageSource.setFallbackToSystemLocale(true);

    }

    public static class LazyHolder{
        public static final MessageSourceSingleton INSTANCE = new MessageSourceSingleton();
    }

    public static AbstractResourceBasedMessageSource getMessageSource()
    {
        return LazyHolder.INSTANCE.resourceBundleMessageSource;
    }

}
