package Book.yes48.config;

import Book.yes48.web.controller.AdminController;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private String AdminConnectPath = "/admin/**";
    private String GoodsConnectPath = "/book-list/**";
    private String resourcePath = "file:///C:/upload/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(AdminConnectPath, GoodsConnectPath)
                .addResourceLocations(resourcePath);
    }

}
