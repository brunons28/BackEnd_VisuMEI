package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserLogin;
import com.example.demo.model.Usuario;
import com.example.demo.repository.RepositoryUsuario;
import com.example.demo.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RepositoryUsuario repository;
	

	@GetMapping
	public ResponseEntity<List<Usuario>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Usuario> GetById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<List<Usuario>> getByEmail(@PathVariable String email) {
		return ResponseEntity.ok(repository.findAllByEmailContainingIgnoreCase(email));	
	}
	
	@PutMapping
	public ResponseEntity<Usuario> put(@RequestBody Usuario usuario){
		return ResponseEntity.ok(repository.save(usuario));
	}
	
	@PutMapping("/put/{id}")
	public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario objetinho) {
		objetinho.setId(id);
		repository.save(objetinho);
		return objetinho;
	}

	@DeleteMapping("/delete/{id}")
	public String remover (@PathVariable Long id) {
		try {
			repository.deleteById(id);
		return "Deletado com sucesso !";
		} catch (Exception e) {
			return "Erro: " + e.getLocalizedMessage();			
		}
	}
		
	
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> Autentication(@RequestBody Optional<UserLogin> user){
		return usuarioService.Logar(user).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> Post(@RequestBody Usuario usuario) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioService.CadastrarCliente(usuario));
	}
}
