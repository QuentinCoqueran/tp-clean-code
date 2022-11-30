package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.model.DrivingLicence;
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
class DrivingLicenceFinderServiceTest {

    @InjectMocks
    private DrivingLicenceFinderService service;

    @Mock
    private InMemoryDatabase database;

    @Test
    void should_find() {
        UUID id = UUID.randomUUID();

        DrivingLicence drivingLicence = DrivingLicence.builder().id(id).build();

        when(database.findById(id)).thenReturn(Optional.of(drivingLicence));

        val actual = service.findById(id);

        assertThat(actual).contains(drivingLicence);
    }

    @Test
    void should_not_find() {
        UUID id = UUID.randomUUID();

        when(database.findById(id)).thenReturn(Optional.empty());

        val actual = service.findById(id);

        assertThat(actual).isEmpty();
    }
}