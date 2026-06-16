package com.company.appointments.service;

import com.company.appointments.dto.CreateAppointmentRequest;
import com.company.appointments.entity.Appointment;
import com.company.appointments.entity.AppointmentStatus;
import com.company.appointments.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository repository;

    @InjectMocks
    private AppointmentService service;

    // TODO: Implement the tests below.
    // You may use AI tools to help, but you must be able to explain every line.

    @Test
    void findAll_noFilters_returnsAll() {
        // TODO
    }

    @Test
    void findAll_withDoctorId_filtersCorrectly() {
        // TODO
    }

    @Test
    void findAll_withStatus_filtersCorrectly() {
        // TODO
    }

    @Test
    void findAll_withBothFilters_filtersCorrectly() {
        // TODO
    }

    @Test
    void create_validRequest_savesAppointmentWithScheduledStatus() {
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setPatientId(1L);
        request.setDoctorId(2L);
        request.setScheduledAt(LocalDateTime.now().plusDays(1));

        Appointment saved = new Appointment();
        saved.setId(10L);
        saved.setPatientId(1L);
        saved.setDoctorId(2L);
        saved.setStatus(AppointmentStatus.SCHEDULED);

        when(repository.save(any(Appointment.class))).thenReturn(saved);

        Appointment result = service.create(request);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
        verify(repository).save(any(Appointment.class));
    }

    @Test
    void findById_existing_returnsAppointment() {
        // TODO
    }

    @Test
    void findById_notFound_returnsEmpty() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Appointment> result = service.findById(99L);

        assertThat(result).isEmpty();
    }

    // BONUS: Write an integration test using Testcontainers that proves
    // two concurrent calls to create() with the same (patientId, doctorId, scheduledAt)
    // result in duplicate records. Then fix it.
}
