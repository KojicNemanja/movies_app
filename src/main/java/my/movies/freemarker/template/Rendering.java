package my.movies.freemarker.template;

import freemarker.template.Template;
import my.movies.freemarker.configuration.FreemarkerConfig;

import java.io.StringWriter;

public class Rendering {

    public static String render(String template_name, Object model_data){
        try {
            Template template = FreemarkerConfig.getConfiguration().getTemplate(template_name + ".ftl");
            StringWriter writer = new StringWriter();
            template.process(model_data, writer);
            return writer.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String render(String template_name) {
        return render(template_name, null);
    }
}
