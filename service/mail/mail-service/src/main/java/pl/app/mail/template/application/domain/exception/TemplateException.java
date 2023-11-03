package pl.app.mail.template.application.domain.exception;


public interface TemplateException {
    class NotFoundTemplateException extends RuntimeException {
        public NotFoundTemplateException() {
            super();
        }

        public NotFoundTemplateException(String message) {
            super(message);
        }

        public static NotFoundTemplateException fromId(String templateId) {
            return new NotFoundTemplateException("Not found template with id: " + templateId);
        }
    }
}
