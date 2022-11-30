package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.service.validator.DrivingLicenceRemovePointsValidator;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenceRemovePointsService {

    private final DrivingLicenceRemovePointsValidator drivingLicenceIdExist;
    private final InMemoryDatabase database;

    public Either<ResourceNotFoundException, DrivingLicence> removePointsOfDrivingLicence(UUID drivingLicenceId, int pointsToRemove) {

        Validation<ResourceNotFoundException, Optional<DrivingLicence>> drivingLicence = drivingLicenceIdExist.drivingLicenceIdExist(drivingLicenceId);

        if (drivingLicence.isValid() && drivingLicence.get().isPresent()) {

            DrivingLicence drivingLicenceToSave = drivingLicence.get().get();
            drivingLicenceToSave = drivingLicenceToSave.withAvailablePoints(Math.max(drivingLicenceToSave.getAvailablePoints() - pointsToRemove, 0));
            return Either.right(database.save(drivingLicenceToSave.getId(), drivingLicenceToSave));

        } else {
            return Either.left(drivingLicence.getError());
        }
    }


}
