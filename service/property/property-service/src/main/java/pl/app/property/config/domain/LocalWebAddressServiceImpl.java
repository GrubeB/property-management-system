package pl.app.property.config.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
class LocalWebAddressServiceImpl implements WebAddressService {
    @Autowired
    private final Environment environment;
    private volatile Integer port;

    @Override
    public String getSubdomain() {
        return "www";
    }

    @Override
    public String getRootDomain() {
        return "localhost";
    }

    @Override
    public String getDomain() {
        return "localhost";
    }

    @Override
    public String getDomainWithPort() {
        return "localhost:" + getPort();
    }

    @Override
    public String getWebAddress() {
        return getProtocol() + "://localhost:" + getPort();
    }

    @Override
    public String getProtocol() {
        return "http";
    }

    @Override
    public Integer getPort() {
        if (port == null) {
            synchronized (LocalWebAddressServiceImpl.class) {
                if (port == null) {
                    String portString = environment.getProperty("local.server.port");
                    Objects.requireNonNull(portString);
                    port = Integer.valueOf(portString);
                }
            }
        }
        return port;
    }
}
