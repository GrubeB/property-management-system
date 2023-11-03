package pl.app.common.shared.mail;

public enum MailTemplateNames {
    PAYMENT_LINK_MAIL_TEMPLATE("paymentLinkMailTemplate"),
    PAYMENT_CHECKOUT_MAIL_TEMPLATE("paymentCheckoutMailTemplate");
    public final String templateName;

    MailTemplateNames(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
