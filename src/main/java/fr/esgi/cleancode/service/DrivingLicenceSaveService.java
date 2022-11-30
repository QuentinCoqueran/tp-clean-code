package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.service.validator.SocialSecurityNumberValidator;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DrivingLicenceSaveService {

    private final InMemoryDatabase database;
    private final DrivingLicenceIdGenerationService drivingLicenceIdGenerationService;

    public Either<InvalidDriverSocialSecurityNumberException, DrivingLicence> create(String socialSecurityNumber) {

        Validation<InvalidDriverSocialSecurityNumberException, String> validation = SocialSecurityNumberValidator.validate(socialSecurityNumber);

        if (validation.isValid()) {
            DrivingLicence drivingLicence = DrivingLicence.builder()
                    .id(drivingLicenceIdGenerationService.generateNewDrivingLicenceId())
                    .driverSocialSecurityNumber(validation.get())
                    .build();
            return Either.right(database.save(drivingLicence.getId(), drivingLicence));
        } else {
            return Either.left(validation.getError());
        }

    }
}
