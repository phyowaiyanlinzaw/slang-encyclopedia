package dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import dictionary.dto.DefandTermRequestDTO;
import dictionary.dto.DefandTermResponseDTO;

@Service("termDao")
public class TermDAO {
	static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int storeTerm(DefandTermRequestDTO req) {
		int result =0;
		
		String sql ="insert into term(term_name,createdBy,createdDate,updatedBy,updatedAt,status,userId) values(?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, req.getTerm());
			ps.setString(2, req.getCreatedBy());
			ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(4, req.getUpdatedBy());
			ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(6, req.getStatus());
			ps.setString(7, req.getUserId());
			result = ps.executeUpdate();
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	public ArrayList<DefandTermResponseDTO> getAllTerm(){
		ArrayList<DefandTermResponseDTO> resList = new ArrayList<>();
		
		String sql="select*from term";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DefandTermResponseDTO res=new DefandTermResponseDTO();
				
				res.setTerm(rs.getString("term_name"));
				res.setUserId(rs.getString("userId"));
				resList.add(res);
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resList;
	}
	
	public DefandTermResponseDTO getTerm(DefandTermResponseDTO req) {
		DefandTermResponseDTO resList = new DefandTermResponseDTO();
		String sql = "select * from term where user_id=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, req.getUserId());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				DefandTermResponseDTO res=new DefandTermResponseDTO();
				res.setTerm(rs.getString("term_name"));	
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return resList;
		
	}
	
	public int getTermId(String term_name) {
		int id=0;
		String sql="select id from term where term_name=?";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, term_name);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				id = rs.getInt("id");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			return id;
	}
}
