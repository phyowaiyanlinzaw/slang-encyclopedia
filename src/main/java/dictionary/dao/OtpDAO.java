package dictionary.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


import org.springframework.stereotype.Service;

import dictionary.dto.OtpRequestDTO;

@Service("otpDao")
public class OtpDAO {
	
	static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int storeOtp(OtpRequestDTO req) {
		
		int result =0;
		String sql = "insert into otp(otpNum,createdAt,createdBy,exp_time) values(?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getOtpNumber());
			ps.setDate(2, Date.valueOf(LocalDate.now()));
			ps.setString(3, req.getRequestedBy());
			ps.setTimestamp(4, Timestamp.valueOf(req.getExpTime()));
			result = ps.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
}
