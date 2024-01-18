package com.rdk.crime.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rdk.crime.domain.Crime;

public interface CrimeRepository extends MongoRepository<Crime, String> {

	List<Crime> findFirst100ByPrimaryTypeAndYear(String primaryType, String year);

}
