package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.configurations.BusinessException;
import com.ppi.trackventory.models.Profile;
import com.ppi.trackventory.models.User;
import com.ppi.trackventory.repositories.ProfileRepository;
import com.ppi.trackventory.repositories.RolRepository;
import com.ppi.trackventory.repositories.UserRepository;
import com.ppi.trackventory.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordGenerator passwordGenerator;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public User saveUser(User user, Boolean assign) throws Exception {
		User newUser = null;
		Optional<User> userLocal = userRepository.findById(user.getId());

		if (userLocal.isPresent()) {
			if(assign) {
				assignPassword(user);
			}
			if (!userLocal.get().getUsername().equals(user.getUsername())) {
				if (userRepository.findByUsername(user.getUsername()) != null) {
					throw new BusinessException("Usuario con este Nombre de Usuario ya existe");
				}
			}
			newUser = userRepository.save(user);

		} else {
			// 👉 Separamos la asignación de contraseña
			assignPassword(user);

			// 👉 Si no tiene perfil, asignar DEFAULT
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

		return newUser;
	}
	
	@Override
	public User updateUserPassword(String userId) throws Exception {
		User newUser = null;
		Optional<User> userLocal = userRepository.findById(userId);
		if (userLocal.isPresent()) {
			newUser = userLocal.get();
			newUser.setPassword(null);
			assignPassword(newUser);
			newUser = userRepository.save(newUser);
		}else {
			throw new BusinessException("Usuario no encontrado");
		}
		return newUser;
	}

	private void assignPassword(User user) {
		if (user.getPassword() != null && !user.getPassword().isBlank()) {
			// Si trae contraseña → encriptar
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		} else {
			// Si no trae → generar aleatoria
			String rawPassword = PasswordGenerator.generatePassword();
			user.setPassword(this.passwordEncoder.encode(rawPassword));

			// Armar contenido HTML del correo
			String htmlContent = "<!DOCTYPE html>\r\n" + "<html lang=\"es\">\r\n" + "<head>\r\n"
					+ "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <title>Credenciales de Acceso</title>\r\n" + "    <style>\r\n"
					+ "        @import url('https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap');\r\n"
					+ "        body { font-family: 'Lato', sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; color: #333; }\r\n"
					+ "        .email-container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }\r\n"
					+ "        h1 { color: #4CAF50; font-size: 24px; margin-bottom: 20px; }\r\n"
					+ "        .stock-warning { background-color: #e8f5e9; color: #256029; padding: 10px; border-left: 6px solid #4CAF50; border-radius: 4px; font-weight: bold; }\r\n"
					+ "        .footer { text-align: center; margin-top: 30px; font-size: 12px; color: #777; }\r\n"
					+ "        .footer a { color: #4CAF50; text-decoration: none; }\r\n"
					+ "        .footer a:hover { text-decoration: underline; }\r\n" + "    </style>\r\n" + "</head>\r\n"
					+ "<body>\r\n" + "    <div class=\"email-container\">\r\n"
					+ "        <h1>Bienvenido a Trackventory</h1>\r\n" + "        <p>Hola,</p>\r\n"
					+ "        <p>Se ha creado tu usuario en <strong>Trackventory</strong>. Tus credenciales son:</p>\r\n"
					+ "        <div class=\"stock-warning\">Contraseña: <strong>" + rawPassword + "</strong></div>\r\n"
					+ "        <p>Por motivos de seguridad, te recomendamos cambiarla después de tu primer inicio de sesión.</p>\r\n"
					+ "        <div class=\"footer\">\r\n"
					+ "            <p>Este es un mensaje automático. Si tienes alguna duda, <a href=\"mailto:soporte@trackventory.com\">contacta con soporte</a>.</p>\r\n"
					+ "            <p>&copy; 2025 Trackventory. Todos los derechos reservados.</p>\r\n"
					+ "        </div>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>\r\n";

			// Enviar correo
			try {
				emailService.sendHtmlEmail(user.getUsername() + "@" + user.getDomain(),
						"Credenciales de acceso a Trackventory", htmlContent);
			} catch (Exception e) {
				e.printStackTrace(); // Aquí podrías usar log.error
			}
		}
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