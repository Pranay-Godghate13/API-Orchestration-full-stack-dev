package com.publicsapient.publicsapient.Service;

import java.util.List;

import com.publicsapient.publicsapient.Model.APIUser;
import com.publicsapient.publicsapient.Payload.APIUserDTO;
import com.publicsapient.publicsapient.Payload.ResponseDTO;


public interface UserService {
    String loadData();
    APIUserDTO findUserById(Long id);
    APIUserDTO findUserByEmail(String email);
    ResponseDTO findByKeyword(String keyword);

}
