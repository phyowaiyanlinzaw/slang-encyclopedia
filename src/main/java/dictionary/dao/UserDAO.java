package dictionary.dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import dictionary.dto.UserRequestDTO;
import dictionary.dto.UserResponseDTO;

@Service("userDao")
public class UserDAO {
	static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int storeUsers(UserRequestDTO req) {
		int result =0;
		String sql = "insert into user(username,email,password,cPassword,createdBy,createdAt,updatedBy,updatedAt,role_id,isVerified,isLocked,status) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getUsername());
			ps.setString(2, req.getEmail());
			ps.setString(3, req.getPassword());
			ps.setString(4, req.getConfirm_password());
			ps.setString(5, req.getUsername());
			ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(7, req.getUsername());
			ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(9, 2);
			ps.setString(10, "No");
			ps.setString(11, "No");
			ps.setString(12, "Exist");
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public ArrayList<UserResponseDTO> getAllUsers(){
		ArrayList<UserResponseDTO> resList = new ArrayList<>();
		String sql = "select*from user where role_id=2";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserResponseDTO res = new UserResponseDTO();
				res.setEmail(rs.getString("email"));
				res.setUsername(rs.getString("username"));
				res.setPassword(rs.getString("password"));
				resList.add(res);
			}
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return resList;
	}
	
	public UserResponseDTO getAdminAccount() {
		UserResponseDTO res = new UserResponseDTO();
		String sql = "select*from user where role_id=1";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				res.setEmail(rs.getString("email"));
				res.setPassword(rs.getString("password"));
				
			}
 			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return res;
	}
	
//	public int storeOtpCount(int otpCount) {
//		int result = 0;
//		String sql = "insert into user(otpCount) values=?";
//		
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setInt(1, otpCount);
//			result = ps.executeUpdate();
//			
//		}catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//		
//		return result;
//	}
	
	public int getUserId(String userEmail) {
		int id = 0;
		String sql = "select id from user where email=?";
		
		try {
			PreparedStatement ps =con.prepareStatement(sql);
			ps.setString(1, userEmail);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		return id;
	}
	
}
