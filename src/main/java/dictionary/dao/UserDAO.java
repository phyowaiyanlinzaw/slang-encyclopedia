package dictionary.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import dictionary.dto.UserRequestDTO;

@Service("userDao")
public class UserDAO {
	static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int storeUsers(UserRequestDTO req) {
		int result =0;
		String sql = "insert into user(username,email,password,cPassword,createdBy,createdAt,updatedBy,updatedAt) (?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getUsername());
			ps.setString(2, req.getEmail());
			ps.setString(3, req.getPassword());
			ps.setString(4, req.getConfirm_password());
			ps.setString(5, req.getUsername());
			ps.setDate(6, Date.valueOf(LocalDate.now()));
			ps.setString(7, req.getUsername());
			ps.setDate(8, Date.valueOf(LocalDate.now()));
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
