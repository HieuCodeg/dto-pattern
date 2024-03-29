package com.deft.service;

import com.deft.dto.UserCreationDTO;
import com.deft.dto.UserDTO;
import com.deft.entity.Role;
import com.deft.entity.User;
import com.deft.mapper.UserMapper;
import com.deft.repository.RoleRepository;
import com.deft.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO create(UserCreationDTO dto) {
//        User user = UserMapper.getInstance().toEntity(dto);
        User user = mapper.map(dto, User.class);
        List<Role> roles = roleRepository.findAllById(dto.getRoleIds());
        user.setRoles(roles);
//        return UserMapper.getInstance().toDTO(userRepository.save(user));
        return mapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
//                .map(user -> UserMapper.getInstance().toDTO(user))
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
