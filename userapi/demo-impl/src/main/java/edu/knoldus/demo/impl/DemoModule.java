package edu.knoldus.demo.impl;
import edu.knoldus.demo.api.DemoService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;


public class DemoModule extends AbstractModule implements ServiceGuiceSupport {
    @Override
    protected void configure() {
        bindService(DemoService.class, DemoServiceImpl.class);
    }
}
