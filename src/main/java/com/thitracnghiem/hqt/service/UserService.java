package com.thitracnghiem.hqt.service;

import java.util.List;
import com.thitracnghiem.hqt.model.TAIKHOAN;

public interface UserService {
    
    List<TAIKHOAN> getAllUsers();
    
    TAIKHOAN getUserById(String id);
    
    TAIKHOAN createUser(TAIKHOAN user);
    
    TAIKHOAN updateUser(TAIKHOAN user);
    
    void deleteUser(String id);
    
    boolean isPasswordValid(String rawPassword, String encodedPassword);
}
