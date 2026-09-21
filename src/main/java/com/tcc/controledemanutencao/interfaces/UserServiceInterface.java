package com.tcc.controledemanutencao.interfaces;

import java.util.List;

import com.tcc.controledemanutencao.dto.UserRequestDTO;
import com.tcc.controledemanutencao.models.UserModel;

public interface UserServiceInterface {

    UserModel save(UserRequestDTO dto);

    UserModel findById(Long id);

    List<UserModel> findAll();

    UserModel update(Long id, UserRequestDTO dto);

    void delete(Long id);

    UserModel findByEmail(String email);
}
