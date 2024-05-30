package com.example.demo.admin.user;

import com.example.demo.user.UserDTO;
import com.example.demo.user.UserMapper;
import com.example.demo.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserMapper userMapper;

    private final DynamicMapper dynamicMapper;

    public CustomTwoReturn<List<UserDTO>, CommonPagingDTO> getAllUser(UserDTO userDTO, CommonPagingDTO pageParam){

        CommonPagingExecuteImp<List<UserDTO>> commonPagingExecuteImp = commonPagingDTO -> userMapper.selectAllUser(userDTO,commonPagingDTO);
        CommonPagingExecute<List<UserDTO>> commonPagingExecute = new CommonPagingExecute<List<UserDTO>>();

        return commonPagingExecute.execute(commonPagingExecuteImp, dynamicMapper,pageParam);
    }

}
