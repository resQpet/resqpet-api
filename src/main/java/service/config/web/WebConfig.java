package service.config.web;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Host;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.io.IOException;
import java.util.Collections;


@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig {

    /**
     * Returns a customized Tomcat servlet web server factory for Spring boot application.
     * Overrides the prepareContext method to add a child StandardContext with the necessary configuration.
     *
     * @return a TomcatServletWebServerFactory instance with customized behavior
     */
    @Bean
    public TomcatServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void prepareContext(Host host, ServletContextInitializer[] initializers) {
                super.prepareContext(host, initializers);
                final StandardContext child = new StandardContext();
                child.addLifecycleListener(new Tomcat.FixContextListener());
                child.setPath("");
                final ServletContainerInitializer initializer = getServletContextInitializer(getContextPath());
                child.addServletContainerInitializer(initializer, Collections.emptySet());
                child.setCrossContext(true);
                host.addChild(child);
            }
        };
    }

    /**
     * Retrieves a ServletContainerInitializer with a customized servlet that redirects requests to the specified context path.
     *
     * @param contextPath the context path to redirect requests to
     * @return a ServletContainerInitializer instance that adds a servlet with redirection behavior
     */
    private ServletContainerInitializer getServletContextInitializer(final String contextPath) {
        return (c, context) -> {
            final Servlet servlet = new HttpServlet() {
                @Override
                protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                    resp.sendRedirect(contextPath);
                }
            };
            context.addServlet("root", servlet).addMapping("/*");
        };
    }
}
