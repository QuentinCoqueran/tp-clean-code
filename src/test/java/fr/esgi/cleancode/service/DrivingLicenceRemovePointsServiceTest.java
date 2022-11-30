package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.service.validator.DrivingLicenceRemovePointsValidator;
import io.vavr.control.Validation;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static io.vavr.API.Invalid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceRemovePointsServiceTest {
    @InjectMocks
    private DrivingLicenceRemovePointsService drivingLicenceRemovePointsService;

    @Mock
    private DrivingLicenceRemovePointsValidator drivingLicenceRemovePointsValidator;

    @Mock
    private DrivingLicenceFinderService drivingLicenceFinderService;

    @Mock
    private InMemoryDatabase database;

    @Test
    void should_remove_points_positive() {
        UUID id = UUID.randomUUID();
        String validSocialSecurityNumber = "123456789123456";
        int pointsToRemove = 2;

        DrivingLicence drivingLicence = DrivingLicence.builder()
                .id(id)
                .driverSocialSecurityNumber(validSocialSecurityNumber)
                .build();

        when(database.save(any(UUID.class), any(DrivingLicence.class))).thenReturn(drivingLicence.withAvailablePoints(Math.max(drivingLicence.getAvailablePoints() - pointsToRemove, 0)));

        when(drivingLicenceRemovePointsValidator.drivingLicenceIdExist(id)).thenReturn(Validation.valid(Optional.of(drivingLicence)));

        val actual = drivingLicenceRemovePointsService.removePointsOfDrivingLicence(id, pointsToRemove);

        assertThat(actual.get().getAvailablePoints()).isEqualTo(10);

    }

    @Test
    void should_remove_points_negative() {
        UUID id = UUID.randomUUID();
        String validSocialSecurityNumber = "123456789123456";
        int pointsToRemove = 22;

        DrivingLicence drivingLicence = DrivingLicence.builder()
                .id(id)
                .driverSocialSecurityNumber(validSocialSecurityNumber)
                .build();

        when(database.save(any(UUID.class), any(DrivingLicence.class))).thenReturn(drivingLicence.withAvailablePoints(Math.max(drivingLicence.getAvailablePoints() - pointsToRemove, 0)));

        when(drivingLicenceRemovePointsValidator.drivingLicenceIdExist(id)).thenReturn(Validation.valid(Optional.of(drivingLicence)));

        val actual = drivingLicenceRemovePointsService.removePointsOfDrivingLicence(id, pointsToRemove);

        assertThat(actual.get().getAvailablePoints()).isEqualTo(0);

    }

    @Test
    void should_driving_licence_not_exist() {
        UUID id = UUID.randomUUID();

        when(drivingLicenceRemovePointsValidator.drivingLicenceIdExist(id)).thenReturn(Invalid(new ResourceNotFoundException("Driving licence not found")));

        val actual = drivingLicenceRemovePointsService.removePointsOfDrivingLicence(id, 2);

        assertThat(actual.getLeft()).isInstanceOf(ResourceNotFoundException.class);

    }
}
