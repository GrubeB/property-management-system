package pl.app.property.config.domain;

public interface WebAddressService {
    // protocol://subdomain.second-level-domain.top-level-domain:port/path
    String getSubdomain(); // subdomain

    String getRootDomain(); // second-level-domain.top-level-domain

    String getDomain(); // subdomain.second-level-domain.top-level-domain

    String getDomainWithPort(); // subdomain.second-level-domain.top-level-domain:port

    String getWebAddress(); // protocol://subdomain.second-level-domain.top-level-domain:port

    String getProtocol(); // protocol

    Integer getPort(); // port
}
