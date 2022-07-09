package com.example.demo.service;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserLogin;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RepositoryUsuario;

@Service
public class UsuarioService {
	
	@Autowired
	private RepositoryUsuario repository;	
	
	public Usuario CadastrarCliente(Usuario cliente) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				
		String senhaEncoder = encoder.encode(cliente.getSenha());
		cliente.setSenha(senhaEncoder);
		
		return repository.save(cliente);
	}
	
	public Optional<UserLogin> Logar(Optional<UserLogin> user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuario = repository.findByEmail(user.get().getEmail());
		
		if(usuario.isPresent()) {
			if(encoder.matches(user.get().getSenha(), usuario.get().getSenha())) {

				String auth = user.get().getEmail() + ":" + user.get().getSenha();
				byte[]  encoderAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encoderAuth);
				
				user.get().setToken(authHeader);
				user.get().setEmail(usuario.get().getEmail());
				user.get().setNome(usuario.get().getNome());
				user.get().setId(usuario.get().getId());
				
				return user;
			}
		}
		
		return null;
	}
}
