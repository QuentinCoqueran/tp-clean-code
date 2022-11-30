package fr.esgi.cleancode.service.validator;

import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.service.DrivingLicenceFinderService;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.Invalid;
import static io.vavr.API.Valid;

@RequiredArgsConstructor
public class DrivingLicenceRemovePointsValidator {
    private final DrivingLicenceFinderService drivingLicenceFinderService;

    public Validation<ResourceNotFoundException, Optional<DrivingLicence>> drivingLicenceIdExist(UUID drivingLicenceId) {
        Optional<DrivingLicence> drivingLicence = drivingLicenceFinderService.findById(drivingLicenceId);
        return drivingLicence.isPresent()
                ? Valid(drivingLicence)
                : Invalid(new ResourceNotFoundException("Driving licence not found"));
    }
}
