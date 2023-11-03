package pl.app.mail.template.application.domain.model;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateTest {

    @Test
    void whenAddProperty_thenTemplateShouldHaveOne() {
        // given
        Template template = new Template();
        // when
        boolean addProperty = template.addProperty("propertyName", "propertyDefaultValue");
        // then
        assertThat(addProperty).isTrue();
        assertThat(template.getProperties().stream().filter(p -> "propertyName".equals(p.getName())).count()).isEqualTo(1L);
    }

    @Test
    void whenAddPropertyWithExistingPropertyName_thenTemplateShouldNotChangeState() {
        // given
        Template template = new Template();
        // when
        boolean addProperty = template.addProperty("propertyName", "propertyDefaultValue");
        boolean addProperty2 = template.addProperty("propertyName", "propertyDefaultValue2");
        // then
        assertThat(addProperty).isTrue();
        assertThat(addProperty2).isFalse();
        assertThat(template.getProperties().stream().filter(p -> "propertyName".equals(p.getName())).count()).isEqualTo(1L);
    }
}