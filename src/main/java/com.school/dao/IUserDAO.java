package com.school.dao;

import com.school.entity.UserDTO;

public interface IUserDAO {

    int insert(UserDTO userDTO);

    UserDTO findByNickname(String nickname);
}
