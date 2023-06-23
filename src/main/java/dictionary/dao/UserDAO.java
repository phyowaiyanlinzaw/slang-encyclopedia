package dictionary.dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.PseudoColumnUsage;
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
				res.setIsVerified(rs.getString("isVerified"));
				res.setIsLocked(rs.getString("isLocked"));
				res.setUserId(rs.getInt("id"));
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
	
	public ArrayList<UserResponseDTO> getAllUsersWithDT() {
	    ArrayList<UserResponseDTO> userList = new ArrayList<>();

	    String sql = "SELECT u.username, t.term_name,  t.createdDate, t.updatedAt " +
	                 "FROM user u " +
	                 "JOIN term t ON u.id = t.userId ";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        	UserResponseDTO userdt = new UserResponseDTO();
	        	userdt.setUsername(rs.getString("username"));
	        	userdt.setTerm(rs.getString("term_name"));
	        	userdt.setCreatedDate(rs.getDate("createdDate").toLocalDate());
	        	userdt.setUpdatedDate(rs.getDate("updatedAt").toLocalDate());
	            userList.add(userdt);
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    return userList;
	}
	
	public int updateUserVerifiedStatus(String email) {
		int result = 0;
		
		String sql = "update user set isVerified=? where email=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "Yes");
			ps.setString(2, email);
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public int updateUserLockedStatus(String status,String email) {
		int result=0;
		String sql = "update user set isLocked=? where email=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, status);
			ps.setString(2, email);
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public int updateUser(UserRequestDTO req) {
		int result =0;
		String sql ="update user set username=?,email=?,password=?,cPassword=? where id=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, req.getUsername());
			ps.setString(2, req.getEmail());
			ps.setString(3, req.getPassword());
			ps.setString(4, req.getConfirm_password());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

	
}
