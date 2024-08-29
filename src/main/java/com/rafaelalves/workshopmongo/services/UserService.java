package com.rafaelalves.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelalves.workshopmongo.domain.User;
import com.rafaelalves.workshopmongo.dto.UserDTO;
import com.rafaelalves.workshopmongo.repository.UserRepository;
import com.rafaelalves.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}

	public User insert(User obj) {
		return repo.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);

	}
	public User update(User obj) {
	    // Busca o usuário pelo ID usando Optional
	    Optional<User> optionalUser = repo.findById(obj.getId());

	    // Se o usuário é encontrado, atualiza os dados e salva
	    return optionalUser.map(existingUser -> {
	        updateData(existingUser, obj);
	        return repo.save(existingUser);
	    }).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + obj.getId()));
	}

	private void updateData(User existingUser, User obj) {
	    // Atualiza os campos desejados
	    existingUser.setName(obj.getName());
	    existingUser.setEmail(obj.getEmail());
	}


	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());

	}

}
