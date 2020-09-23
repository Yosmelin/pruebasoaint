package com.soaint.prueba.seguridad.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.prueba.seguridad.autenticacion.Autenticacion;
import com.soaint.prueba.seguridad.entidad.ResultadoAutenticacion;
import com.soaint.prueba.seguridad.entidad.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(UserController.class);

	public Autenticacion autenticacion = new Autenticacion();

	@PostMapping("user")
	public ResultadoAutenticacion login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		
		logger.debug("Generando Token");

		ResultadoAutenticacion resultadoAutenticacion = new ResultadoAutenticacion();

		if (autenticacion.validarUsuarios(username)) {
			
			logger.info("Autenticacion correcta");
			
			
			String token = getJWTToken(username);
			User user = new User();
			user.setUser(username);
			user.setToken(token);

			resultadoAutenticacion.setUsuario(user);
			resultadoAutenticacion.setMensaje("Ok");

		} else {
			
			logger.error("Autenticacion Fallida");

			resultadoAutenticacion.setMensaje("Autenticacion fallida");

		}

		return resultadoAutenticacion;

	}

	private String getJWTToken(String username) {

	

		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
