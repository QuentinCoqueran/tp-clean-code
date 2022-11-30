package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.val;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceSaveServiceTest {

    @InjectMocks
    private DrivingLicenceSaveService serviceSave;

    @Mock
    private DrivingLicenceIdGenerationService serviceId;

    @Mock
    private InMemoryDatabase database;

    @Captor private ArgumentCaptor<DrivingLicence> entityCaptor;

    @Captor private ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Test
    void should_save() {
        UUID id = UUID.randomUUID();

        String validSocialSecurityNumber = "123456789123456";

        DrivingLicence drivingLicence = DrivingLicence.builder()
                .id(id)
                .driverSocialSecurityNumber(validSocialSecurityNumber)
                .build();

        when(serviceId.generateNewDrivingLicenceId()).thenReturn(id);

        when(database.save(eq(id),any(DrivingLicence.class))).thenReturn(drivingLicence);

        val actual = serviceSave.create(validSocialSecurityNumber);

        verify(database).save(eq(id),entityCaptor.capture());
        verifyNoMoreInteractions(database);

        assertThat(actual.get()).isEqualTo(drivingLicence);
    }

    @Test
    void should_not_save_if_repository_throw_exception() {

        String invalidSocialSecurityNumber = "1234567F89123456";

        val actual = serviceSave.create(invalidSocialSecurityNumber);

        VavrAssertions.assertThat(actual).isLeft().containsLeftInstanceOf(InvalidDriverSocialSecurityNumberException.class);
        assertThat(actual.getLeft())
                .usingRecursiveComparison()
                .isEqualTo(new InvalidDriverSocialSecurityNumberException("Invalid Social Security Number"));
    }
}
