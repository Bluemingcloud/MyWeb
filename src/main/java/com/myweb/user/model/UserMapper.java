package com.myweb.user.model;

public interface UserMapper {
	
	public UserDTO findUser(String id);
	
	public int insertUser(UserDTO dto);
	
	public UserDTO loginUser(UserDTO dto);
	
	public int modifyUser(UserDTO dto);
	
	public void modifyPw(UserDTO dto);
	
	public int deleteUser(UserDTO dto);
}
