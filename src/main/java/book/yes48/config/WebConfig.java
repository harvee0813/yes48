package book.yes48.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String AdminConnectPath = "/admin/**";
    private String GoodsConnectPath = "/goods/**";
    private String resourcePath = "file:///C:/upload/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(AdminConnectPath, GoodsConnectPath)
                .addResourceLocations(resourcePath);
    }
}
