package com.ppi.trackventory.configurations;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ppi.trackventory.models.Permission;
import com.ppi.trackventory.services.impl.FormService;
import com.ppi.trackventory.services.impl.PermissionService;
import com.ppi.trackventory.services.impl.ProfileService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	private String SECRET_KEY = "ClavePPI";

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private FormService formService;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername(), userDetails.getAuthorities());
	}

	private String createToken(Map<String, Object> claims, String subject,
			Collection<? extends GrantedAuthority> authorities) {

// suponemos que solo hay 1 perfil, extraído como authority
		String profileId = null;
		if (authorities != null && !authorities.isEmpty()) {
			profileId = authorities.iterator().next().getAuthority();

// obtener permisos del perfil
			List<Permission> permisos = permissionService
					.getPermissionsByProfile(profileService.getProfileById(Long.parseLong(profileId)).orElse(null));

// lista de permisos finales para guardar en el token
			List<String> permisosToken = new java.util.ArrayList<>();

			for (Permission permiso : permisos) {
// obtener el form asociado
				String formUrl = formService.getFormById(permiso.getForm_pms().getId()).map(f -> f.getUrl()).orElse(null);

				if (formUrl != null) {
					// revisar qué operaciones tiene activas el permiso
					if (permiso.isCanCreate()) {
						permisosToken.add(formUrl + ":c");
					}
					if (permiso.isCanRead()) {
						permisosToken.add(formUrl + ":r");
					}
					if (permiso.isCanUpdate()) {
						permisosToken.add(formUrl + ":u");
					}
					if (permiso.isCanDelete()) {
						permisosToken.add(formUrl + ":d");
					}
				}
			}

			claims.put("profileId", profileId);
			claims.put("permissions", permisosToken);
		}

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
