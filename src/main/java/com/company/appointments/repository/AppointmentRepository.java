package com.company.appointments.repository;

import com.company.appointments.entity.Appointment;
import com.company.appointments.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);

    Optional<Appointment> findByPatientIdAndDoctorIdAndScheduledAt(
            Long patientId, Long doctorId, LocalDateTime scheduledAt);
}
