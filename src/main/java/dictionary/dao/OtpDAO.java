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
import dictionary.dto.UserRequestDTO;

@Service("otpDao")
public class OtpDAO {
	
	static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int storeOtp(OtpRequestDTO req) {
		
		int result =0;
		String sql = "insert into otp(otpNum,createdAt,createdBy,count,status,userId,restrictionTime) "
				+ "values(?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getOtpNumber());
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(3, req.getRequestedBy());
			ps.setInt(4, req.getOtpCount());
			ps.setString(5, "active");
			ps.setInt(6, req.getUserId());
			ps.setTimestamp(7, req.getRestrictTime());
			result = ps.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public OtpResponseDTO getOtp(OtpRequestDTO req) {
		OtpResponseDTO res = new OtpResponseDTO();
		String sql = "select * from otp where createdBy=? and status='active'";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getRequestedBy());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				res.setOtpNumber(rs.getString("otpNum"));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return res;
		
 	}

	public int updateOtpStatus(OtpRequestDTO req){
		int result = 0;
		String sql = "update otp set status='expired' where otpNum=?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getOtpNumber());
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}

		return result;
	}
	
	public int getOtpCounts(String email) {
		int rowCounts = 0;
		String sql = "select count(*) from otp where createdBy=? ";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				rowCounts = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
 		return rowCounts;
	}
	
	public int deleteOtps(OtpRequestDTO req) {
		int result = 0;
		String sql = "delete from otp where userId=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, req.getUserId());
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public int addRestrictionTime(OtpRequestDTO req) {
		int result = 0;
		String sql = "insert into otp(createdBy,createdAt,userId,restrictionTime)"
				+ "values(?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, req.getRequestedBy());
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(3, req.getUserId());
			ps.setTimestamp(4, req.getRestrictTime());
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	public int deleteRestrictionTime(OtpRequestDTO req) {
		int result = 0;
		String sql = "delete TOP(1) from otp where userId=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, req.getUserId());
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public int alterIncrement() {
		int result = 0;
		String sql = "alter table otp auto_increment=1";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}
