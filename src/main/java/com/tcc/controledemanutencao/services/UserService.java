package com.tcc.controledemanutencao.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.controledemanutencao.dto.UserRequestDTO;
import com.tcc.controledemanutencao.interfaces.UserServiceInterface;
import com.tcc.controledemanutencao.models.UserModel;
import com.tcc.controledemanutencao.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel save(UserRequestDTO dto) {

        UserModel user = new UserModel();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        user.setPassword(
                passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public UserModel findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    @Override
    public List<UserModel> findAll() {

        return userRepository.findAll();
    }

    @Override
    public UserModel update(Long id, UserRequestDTO dto) {

        UserModel user = findById(id);

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(
                passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {

        UserModel user = findById(id);

        userRepository.delete(user);
    }

    @Override
    public UserModel findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
}
