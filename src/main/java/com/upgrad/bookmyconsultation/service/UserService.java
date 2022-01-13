package com.upgrad.bookmyconsultation.service;


import com.upgrad.bookmyconsultation.entity.User;
import com.upgrad.bookmyconsultation.exception.InvalidInputException;
import com.upgrad.bookmyconsultation.exception.ResourceUnAvailableException;
import com.upgrad.bookmyconsultation.provider.PasswordCryptographyProvider;
import com.upgrad.bookmyconsultation.repository.UserRepository;
import com.upgrad.bookmyconsultation.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	@Autowired
	private PasswordCryptographyProvider passwordCryptographyProvider;


	public User register(User user) throws InvalidInputException {
		ValidationUtils.validate(user);

		user.setCreatedDate(LocalDate.now().toString());
		encryptPassword(user);
		userRepository.save(user);
		return user;
	}

	public User getUser(String id) {
		return Optional.ofNullable(userRepository.findById(id))
				.get()
				.orElseThrow(() -> new ResourceUnAvailableException("No User found!"));
	}

	//create a method named getAllUsers that returns a List of type User
		//return all the users from the database
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}


	//Finding users by Email ID

	public User findUserByEmailAddress(String email){
		return Optional.ofNullable(userRepository.findUserByEmailId(email))
				.orElseThrow(() -> new ResourceUnAvailableException("No user found with available Email : " + email));
	}


	private void encryptPassword(final User newUser) {

		String password = newUser.getPassword();
		final String[] encryptedData = passwordCryptographyProvider.encrypt(password);
		newUser.setSalt(encryptedData[0]);
		newUser.setPassword(encryptedData[1]);
	}
}
