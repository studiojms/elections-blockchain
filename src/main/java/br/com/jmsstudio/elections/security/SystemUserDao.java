package br.com.jmsstudio.elections.security;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserDao extends CrudRepository<SystemUser, Integer>{

	Optional<SystemUser> findByEmail(String email);

}
