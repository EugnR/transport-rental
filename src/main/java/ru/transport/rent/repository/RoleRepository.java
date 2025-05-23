package ru.transport.rent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.transport.rent.entity.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
