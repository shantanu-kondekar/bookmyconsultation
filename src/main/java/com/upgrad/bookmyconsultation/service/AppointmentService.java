package com.upgrad.bookmyconsultation.service;

import com.upgrad.bookmyconsultation.entity.Appointment;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.exception.SlotUnavailableException;
import com.upgrad.bookmyconsultation.repository.AppointmentRepository;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

	
	
	//mark it autowired
	//create an instance of AppointmentRepository called appointmentRepository
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private UserRepository userRepository;



	//create a method name appointment with the return type of String and parameter of type Appointment
	//declare exceptions 'SlotUnavailableException' and 'InvalidInputException'

	public String acceptAppointment(Appointment acceptAppointment) throws InvalidInputException, SlotUnavailableException {
		//validate the appointment details using the validate method from ValidationUtils class
		ValidationUtils.validate(acceptAppointment);
		//find if an appointment exists with the same doctor for the same date and time
		Appointment existingAppointment = appointmentRepository.
				findByDoctorIdAndTimeSlotAndAppointmentDate(acceptAppointment.getDoctorId(),
						acceptAppointment.getTimeSlot(), acceptAppointment.getAppointmentDate());
		//if the appointment exists throw the SlotUnavailableException
		if(!(existingAppointment == null)){
			if(Objects.equals(existingAppointment.getDoctorId(), acceptAppointment.getDoctorId())){
			throw new SlotUnavailableException("Appointment is already exists!");
			}
		}
		else{
			//save the appointment details to the database
			appointmentRepository.save(acceptAppointment);
		}
		//return the appointment id
		return acceptAppointment.getAppointmentId();
	}


	//create a method getAppointment of type Appointment with a parameter name appointmentId of type String
	//Use the appointmentId to get the appointment details

	public Optional<Appointment> getAppointment(String id) throws ResourceUnAvailableException {

//		Optional<Appointment> getAppointment = appointmentRepository.findById(id);
//
//		//if the appointment exists return the appointment
//		//else throw ResourceUnAvailableException
//		//tip: use Optional.ofNullable(). Use orElseThrow() method when Optional.ofNullable() throws NULL
//		return Optional.of(getAppointment).orElseThrow(()
//				-> new ResourceUnAvailableException("No Appointment found with this ID!"));

		return Optional.ofNullable(appointmentRepository.findById(id).orElseThrow(() -> new ResourceUnAvailableException("No Appointment found with this ID!")));
	}
	



	public List<Appointment> getAppointmentsForUser(String userId) {
		return appointmentRepository.findByUserId(userId);
	}
}
