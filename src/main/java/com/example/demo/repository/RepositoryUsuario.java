package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

@Repository
public interface RepositoryUsuario extends JpaRepository<Usuario, Long> {

    public List<Usuario> findAllByEmailContainingIgnoreCase(String cpf);
	
	public Optional<Usuario> findByEmail(String cpf);
}
