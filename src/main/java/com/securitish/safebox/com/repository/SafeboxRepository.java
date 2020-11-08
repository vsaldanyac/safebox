package com.securitish.safebox.com.repository;

import com.securitish.safebox.com.repository.dao.SafeboxDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SafeboxRepository extends CrudRepository<SafeboxDAO, String> {

}
