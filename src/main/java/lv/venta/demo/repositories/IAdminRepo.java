package lv.venta.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import lv.venta.demo.models.Admin;

public interface IAdminRepo extends CrudRepository<Admin, Integer>{

}
