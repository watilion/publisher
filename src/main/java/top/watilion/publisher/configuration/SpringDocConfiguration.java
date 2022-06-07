package top.watilion.publisher.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author watilion
 * @date 2022/6/7 22:42
 */
@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI springDoc(){
        return new OpenAPI()
                .info(new Info().title("publisher")
                        .description("publisher api文档")
                        .version("v1.0.0"));
    }
}
