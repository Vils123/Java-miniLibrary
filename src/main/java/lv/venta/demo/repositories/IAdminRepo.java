package lv.venta.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.models.Admin;

public interface IAdminRepo extends CrudRepository<Admin, Integer>{

	boolean existsByUsername(String username);
	boolean existsByPassword(String password);
	Admin findByUsername(String username);
}
