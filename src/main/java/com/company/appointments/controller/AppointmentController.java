package com.company.appointments.controller;

import com.company.appointments.dto.CreateAppointmentRequest;
import com.company.appointments.entity.Appointment;
import com.company.appointments.entity.AppointmentStatus;
import com.company.appointments.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> getAll(
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) AppointmentStatus status
    ) {
        return appointmentService.findAll(doctorId, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return appointmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment create(@RequestBody @Valid CreateAppointmentRequest request) {
        return appointmentService.create(request);
    }

    @PatchMapping("/{id}/status")
    public Appointment updateStatus(@PathVariable Long id, @RequestParam String status) {
        return appointmentService.updateStatus(id, status);
    }
}
