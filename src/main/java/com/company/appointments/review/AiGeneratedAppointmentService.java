package com.company.appointments.review;

import com.company.appointments.dto.CreateAppointmentRequest;
import com.company.appointments.entity.Appointment;
import com.company.appointments.entity.AppointmentStatus;
import com.company.appointments.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AiGeneratedAppointmentService {

    private final AppointmentRepository repository;

    public Appointment createIfNotExists(CreateAppointmentRequest request) {
        Optional<Appointment> existing = repository.findByPatientIdAndDoctorIdAndScheduledAt(
                request.getPatientId(),
                request.getDoctorId(),
                request.getScheduledAt()
        );

        if (existing.isPresent()) {
            return existing.get();
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setScheduledAt(request.getScheduledAt());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(request.getNotes());
        appointment.setCreatedAt(LocalDateTime.now());

        return repository.save(appointment);
    }

    public List<Appointment> findOverlapping(Long doctorId, LocalDateTime start, LocalDateTime end) {
        return repository.findByDoctorId(doctorId).stream()
                .filter(a -> !a.getScheduledAt().isBefore(start) && !a.getScheduledAt().isAfter(end))
                .toList();
    }
}
