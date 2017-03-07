import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;


@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableSwagger2
@ComponentScan("hello")
public class Application extends SpringBootServletInitializer {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }
  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("greetings")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("hello"))
            .paths(regex("/greeting.*"))
            .build();
  }

  @Bean
  public Docket newsApi2() {
    return new Docket(DocumentationType.SWAGGER_2)
            .groupName("makes")
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("hello"))
            .paths(regex("/feel.*"))
            .build();
  }
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            /*.title("Spring REST Sample with Swagger")
            .description("Spring REST Sample with Swagger")
            .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
            .contact("Niklas Heidloff")
            .license("Apache License Version 2.0")
            .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
            .version("2.0")*/
            .title("Spring Boot中使用Swagger2构建RESTful APIs")
            .description("更多Spring Boot相关文章请关注：http://blog.didispace.com/")
            .termsOfServiceUrl("http://blog.didispace.com/")
            .contact("美科研发组")
            .version("1.0")
            .build();
  }
}

