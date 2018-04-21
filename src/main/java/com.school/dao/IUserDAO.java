package com.school.dao;

import com.school.entity.UserDTO;
import org.springframework.stereotype.Component;

public interface IUserDAO {

    int insert(UserDTO userDTO);

    UserDTO findByNickname(String nickname);
}
