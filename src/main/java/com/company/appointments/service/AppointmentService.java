package com.company.appointments.service;

import com.company.appointments.dto.CreateAppointmentRequest;
import com.company.appointments.entity.Appointment;
import com.company.appointments.entity.AppointmentStatus;
import com.company.appointments.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;

    public List<Appointment> findAll(Long doctorId, AppointmentStatus status) {
        if (doctorId != null && status != null) {
            return repository.findByDoctorIdAndStatus(doctorId, status);
        }
        if (doctorId != null) {
            return repository.findByDoctorId(doctorId);
        }
        if (status != null) {
            return repository.findByStatus(status);
        }
        return repository.findAll();
    }

    public Optional<Appointment> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Appointment create(CreateAppointmentRequest request) {
        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setScheduledAt(request.getScheduledAt());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(request.getNotes());
        return repository.save(appointment);
    }

    @Transactional
    public Appointment updateStatus(Long id, String status) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + id));
        appointment.setStatus(AppointmentStatus.valueOf(status));
        return repository.save(appointment);
    }
}
