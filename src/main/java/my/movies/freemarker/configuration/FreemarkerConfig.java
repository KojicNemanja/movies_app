package my.movies.freemarker.configuration;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;

public class FreemarkerConfig {
    private static Configuration configuration = null;

    public static Configuration getConfiguration() {
        try {
            if (configuration == null) {
                configuration = new Configuration(Configuration.VERSION_2_3_32);
                String template_path = System.getenv("JAVA_RESOURCES") + "/movies_app/Templates/";
                File template_file = new File(template_path);
                configuration.setDirectoryForTemplateLoading(template_file);
                configuration.setDefaultEncoding("UTF-8");
                configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
                configuration.setLogTemplateExceptions(false);
                configuration.setWrapUncheckedExceptions(true);
                configuration.setFallbackOnNullLoopVariable(false);
                configuration.setNumberFormat("computer");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return configuration;
    }
}
