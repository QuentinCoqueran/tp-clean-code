package fr.esgi.cleancode.service.validator;

import fr.esgi.cleancode.model.DrivingLicence;
import fr.esgi.cleancode.service.DrivingLicenceFinderService;
import fr.esgi.cleancode.service.validator.DrivingLicenceRemovePointsValidator;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceRemovePointsValidatorTest {

    @Mock
    private DrivingLicenceFinderService drivingLicenceFinderService;

    @InjectMocks
    private DrivingLicenceRemovePointsValidator drivingLicenceRemovePointsValidator;


    @Test
    void should_find() {
        UUID id = UUID.randomUUID();

        DrivingLicence drivingLicence = DrivingLicence.builder().id(id).build();

        when(drivingLicenceFinderService.findById(id)).thenReturn(Optional.of(drivingLicence));

        val actual = drivingLicenceRemovePointsValidator.drivingLicenceIdExist(id);

        assertThat(actual.isValid()).isTrue();
    }

    @Test
    void should_not_find() {
        UUID id = UUID.randomUUID();

        when(drivingLicenceFinderService.findById(id)).thenReturn(Optional.empty());

        val actual = drivingLicenceRemovePointsValidator.drivingLicenceIdExist(id);

        assertThat(actual.isInvalid()).isTrue();
    }

}
