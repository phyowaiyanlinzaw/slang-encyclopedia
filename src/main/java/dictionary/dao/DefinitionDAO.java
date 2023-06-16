package dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.stereotype.Service;


import dictionary.dto.DefandTermRequestDTO;
import dictionary.dto.DefandTermResponseDTO;

@Service("definitionDao")
public class DefinitionDAO {
static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int storeDefinition(DefandTermRequestDTO req) {
		int result =0;
		String sql="insert into definition(createdBy,createdDate,definition_text,updatedBy,updatedAt,status,term_id,user_id) values(?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, req.getCreatedBy());
			ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(3, req.getDefinition_text());
			ps.setString(4, req.getUpdatedBy());
			ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(6, req.getStatus());
			ps.setInt(7, req.getTermId());
			ps.setString(8, req.getUserId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public ArrayList<DefandTermResponseDTO> getAllDef(){
		ArrayList<DefandTermResponseDTO> resList = new ArrayList<>();
		
		String sql="select*from definition";
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				DefandTermResponseDTO res=new DefandTermResponseDTO();
				
				res.setDefinition_text(rs.getString("definition_text"));
				res.setTermId(rs.getString("term_id"));
				res.setUserId(rs.getString("user_id"));
				resList.add(res);
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resList;
	}
	
	
	public DefandTermResponseDTO getDef(DefandTermResponseDTO req) {
		DefandTermResponseDTO resList = new DefandTermResponseDTO();
		String sql = "select * from definition where user_id=? and term_id=?";
		
		try {
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, req.getUserId());
			ps.setString(2, req.getTermId());
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				DefandTermResponseDTO res = new DefandTermResponseDTO();
				res.setDefinition_text(rs.getString("definition_text"));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resList;
	}
}
