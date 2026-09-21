package com.tcc.controledemanutencao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.controledemanutencao.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>  {

    Optional<UserModel> findByEmail(String email);

}
