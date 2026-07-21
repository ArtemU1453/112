package by.mchs.e112.platform.commons;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import by.mchs.e112.platform.commons.dto.PageRequest;
import by.mchs.e112.platform.commons.dto.PageResponse;
import by.mchs.e112.platform.commons.error.ErrorCode;
import by.mchs.e112.platform.commons.error.ProblemDetail;
import by.mchs.e112.platform.commons.error.ProblemDetails;
import by.mchs.e112.platform.commons.exception.ValidationException;
import by.mchs.e112.platform.commons.localization.ResourceBundleMessageResolver;
import by.mchs.e112.platform.commons.localization.SupportedLocales;
import by.mchs.e112.platform.commons.security.PlatformPrincipal;
import by.mchs.e112.platform.commons.security.Roles;
import by.mchs.e112.platform.commons.validation.ValidationResult;
import by.mchs.e112.platform.commons.validation.Validators;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PlatformCommonsTest {

    @Test
    void problemDetailCarriesCodeAndStatus() {
        ProblemDetail pd = ProblemDetails.of(ErrorCode.NOT_FOUND, "нет ресурса", "/api/v1/x", "corr-1");
        assertThat(pd.status()).isEqualTo(404);
        assertThat(pd.code()).isEqualTo("platform.resource.not_found");
        assertThat(pd.correlationId()).isEqualTo("corr-1");
        assertThat(pd.violations()).isEmpty();
    }

    @Test
    void pageRequestRejectsInvalidSize() {
        assertThatThrownBy(() -> PageRequest.of(0, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> PageRequest.of(0, PageRequest.MAX_SIZE + 1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void pageResponseComputesMetadata() {
        PageResponse<String> page = PageResponse.of(List.of("a", "b"), PageRequest.of(0, 2), 5);
        assertThat(page.totalPages()).isEqualTo(3);
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    void validationResultAggregatesViolations() {
        ValidationResult result = new ValidationResult()
                .rejectIf(!Validators.notBlank(""), "name", "field.required", "обязательно")
                .rejectIf(!Validators.isEmail("bad"), "email", "field.email", "неверный e-mail");
        assertThat(result.hasErrors()).isTrue();
        assertThatThrownBy(() -> result.throwIfInvalid("ошибка"))
                .isInstanceOf(ValidationException.class);
        assertThat(result.violations()).hasSize(2);
    }

    @Test
    void principalChecksRoles() {
        PlatformPrincipal p = new PlatformPrincipal(UUID.randomUUID(), "d1", Set.of(Roles.DISPATCHER));
        assertThat(p.hasRole(Roles.DISPATCHER)).isTrue();
        assertThat(p.hasAnyRole(Roles.ADMIN, Roles.DISPATCHER)).isTrue();
        assertThat(p.hasRole(Roles.ADMIN)).isFalse();
    }

    @Test
    void messageResolverFallsBackToKey() {
        ResourceBundleMessageResolver resolver = new ResourceBundleMessageResolver("platform-messages");
        assertThat(resolver.resolve("platform.resource.not_found", SupportedLocales.RU))
                .isEqualTo("Ресурс не найден");
        assertThat(resolver.resolve("unknown.key", SupportedLocales.RU)).isEqualTo("unknown.key");
    }
}
