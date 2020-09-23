package com.soaint.prueba.seguridad.autenticacion;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class Autenticacion {

   @Autowired
	@Value("${seguridad.usuarios}")
	private List<String> usuarios;


	public boolean validarUsuarios(String usuarioIn) {
		
		boolean respuesta;
		
//		System.out.println("Lista de usuarios: "+usuarios.size());

	

		if (searchList(usuarios, usuarioIn))
			respuesta = true;
		else
			respuesta = false;
		
		

		return respuesta;

	}

	private static boolean searchList(List<String> usuarios2, String searchString) {
		return Arrays.asList(usuarios2).contains(searchString);
	}

}
