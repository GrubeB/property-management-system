package pl.app.property.config.payment;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class WebsiteBankAccountServiceImpl implements WebsiteBankAccountService {
    @Override
    public UUID getWebsiteAccountId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}
