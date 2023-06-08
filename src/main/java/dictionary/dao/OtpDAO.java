package dictionary.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import dictionary.dto.OtpRequestDTO;
import dictionary.dto.OtpResponseDTO;

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
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(3, req.getRequestedBy());
			ps.setTimestamp(4, req.getExpTime());
			result = ps.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public OtpResponseDTO getOtp(OtpRequestDTO req) {
		OtpResponseDTO res = new OtpResponseDTO();
		String sql = "select * from otp where createdBy=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getRequestedBy());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				res.setOtpNumber(rs.getString("otpNum"));
				res.setExpTime(rs.getTimestamp("exp_time"));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return res;
		
 	}
}
