package fr.esgi.cleancode.service.validator;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static fr.esgi.cleancode.service.validator.SocialSecurityNumberValidator.validate;
import static org.assertj.vavr.api.VavrAssertions.assertThat;

public class SocialSecurityNumberValidatorTest {

	@ParameterizedTest
	@ValueSource(strings = {"123456789012345", "098765432109876"})
	void should_validate(String validSocialSecurityNumber) {
		val actual = validate(validSocialSecurityNumber);
		assertThat(actual).containsValidInstanceOf(String.class);
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"lorem ipsum", "azertyuiopmlkjh", "09876543210987654321", "098654"})
	void should_not_validate(String invalidSocialSecurityNumber) {
		val actual = validate(invalidSocialSecurityNumber);
		assertThat(actual).containsInvalidInstanceOf(InvalidDriverSocialSecurityNumberException.class);
	}
}
