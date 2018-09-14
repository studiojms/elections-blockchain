package br.com.jmsstudio.elections.daos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jmsstudio.elections.models.Ellection;

@Repository
public interface EllectionDao extends CrudRepository<Ellection, Integer>{

	List<Ellection> findByOwnerId(Integer ownerId);

}
