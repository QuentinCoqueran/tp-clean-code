package fr.esgi.cleancode.service.validator;

import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import io.vavr.control.Validation;

import static io.vavr.API.Invalid;
import static io.vavr.API.Valid;

public class SocialSecurityNumberValidator {
	public static Validation<InvalidDriverSocialSecurityNumberException, String> validate(String socialSecurityNumber) {

		return socialSecurityNumber != null && socialSecurityNumber.length() == 15 && socialSecurityNumber.matches("[0-9]+")
				? Valid(socialSecurityNumber)
				: Invalid(new InvalidDriverSocialSecurityNumberException("Invalid Social Security Number"));
	}
}
