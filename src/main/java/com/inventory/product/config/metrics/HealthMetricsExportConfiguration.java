package com.inventory.product.config.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.health.actuate.endpoint.HealthEndpoint;
import org.springframework.boot.health.contributor.Status;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class HealthMetricsExportConfiguration {

    public HealthMetricsExportConfiguration(MeterRegistry registry, HealthEndpoint healthEndpoint) {
        // Crea una métrica Gauge llamada "app.health.status"
        Gauge.builder("app.health.status", healthEndpoint, this::getStatusCode)
                .description("Estado de salud de la aplicación. 3=UP, 2=OUT_OF_SERVICE, 1=DOWN, 0=UNKNOWN")
                .strongReference(true) // Importante para que se actualice
                .register(registry);
    }

    private int getStatusCode(HealthEndpoint health) {
        Status status = health.health().getStatus();
        if (Status.UP.equals(status)) {
            return 3;
        }
        if (Status.OUT_OF_SERVICE.equals(status)) {
            return 2;
        }
        if (Status.DOWN.equals(status)) {
            return 1; // <-- Valor que indica que algo está CAÍDO
        }
        return 0;
    }
}