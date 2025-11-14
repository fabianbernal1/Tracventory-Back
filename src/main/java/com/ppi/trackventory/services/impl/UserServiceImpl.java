package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.configurations.BusinessException;

import com.ppi.trackventory.models.Profile;
import com.ppi.trackventory.models.User;
import com.ppi.trackventory.models.UserWithPasswordDTO;
import com.ppi.trackventory.repositories.ProfileRepository;
import com.ppi.trackventory.repositories.RolRepository;
import com.ppi.trackventory.repositories.UserRepository;
import com.ppi.trackventory.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordGenerator passwordGenerator;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public UserWithPasswordDTO saveUser(User user, Boolean assign) throws Exception {
		User newUser = null;
		String generatedPassword = null;

		Optional<User> userLocal = userRepository.findById(user.getId());

		if (userLocal.isPresent()) {
			if(assign) {
				generatedPassword = assignPassword(user);
			}
			if (!userLocal.get().getUsername().equals(user.getUsername())) {
				if (userRepository.findByUsername(user.getUsername()) != null) {
					throw new BusinessException("Usuario con este Nombre de Usuario ya existe");
				}
			}
			newUser = userRepository.save(user);

		} else {
			// Asignación de contraseña nueva
			generatedPassword = assignPassword(user);

			// Asignar perfil DEFAULT
			if (user.getProfile() == null) {
				Optional<Profile> profile = profileRepository.findByName("DEFAULT");
				if (profile.isPresent()) {
					user.setProfile(profile.get());
				} else {
					Profile profileDefault = new Profile();
					profileDefault.setName("DEFAULT");
					profileRepository.save(profileDefault);
					user.setProfile(profileDefault);
				}
			}

			newUser = userRepository.save(user);
		}

		return new UserWithPasswordDTO(newUser, generatedPassword);
	}
	
	@Override
	public UserWithPasswordDTO updateUserPassword(String userId) throws Exception {
		Optional<User> userLocal = userRepository.findById(userId);

		if (!userLocal.isPresent()) {
			throw new BusinessException("Usuario no encontrado");
		}

		User user = userLocal.get();
		user.setPassword(null);

		String generatedPassword = assignPassword(user);

		user = userRepository.save(user);

		return new UserWithPasswordDTO(user, generatedPassword);
	}

	/**
	 * Asigna una contraseña al usuario.
	 * @return contraseña en texto plano si fue generada por el sistema.
	 */
	private String assignPassword(User user) {

		if (user.getPassword() != null && !user.getPassword().isBlank()) {
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			return null; // No generada
		}

		// Generar contraseña
		String rawPassword = PasswordGenerator.generatePassword();
		user.setPassword(this.passwordEncoder.encode(rawPassword));

		return rawPassword;
	}

	@Override
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<User> getUserById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
}
