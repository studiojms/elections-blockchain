package br.com.jmsstudio.elections.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.jmsstudio.elections.models.Voter;

@Repository
public interface VoterDao extends CrudRepository<Voter, Integer>{

	List<Voter> findAllByEllectionId(Integer ellectionId);

	Optional<Voter> findByNumber(String voterNumber);

}
