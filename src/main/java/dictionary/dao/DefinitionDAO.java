package dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
		String sql="insert into definition(createdBy,createdDate,definition_text,updatedBy,updatedAt,status,term_id,user_id,example) values(?,?,?,?,?,?,?,?,?)";
		
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
			ps.setString(9, req.getExample());
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
				res.setExample(rs.getString("example"));
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
	
	public ArrayList<DefandTermResponseDTO> getAllDefwithTermOrderByAttribute(String attribute, String sortOrder) {
	    ArrayList<DefandTermResponseDTO> resList = new ArrayList<>();

	    String sql = "SELECT d.id, d.definition_text, d.example, t.term_name, u.username, t.createdDate,t.updatedAt, "
	    		+ "COUNT(*) AS like_count "
	            + "FROM definition d JOIN term t ON d.term_id = t.id "
	            + "JOIN user u ON d.user_id = u.id "
	            + "LEFT JOIN vote v ON d.id = v.definitionId "
	            + "GROUP BY d.id, d.definition_text, t.term_name, d.example,  u.username, t.createdDate, t.updatedAt "
	            + "ORDER BY " + attribute + " " + sortOrder;

	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            DefandTermResponseDTO res = new DefandTermResponseDTO();
 
	            res.setDefId(rs.getInt("id"));
	            res.setDefinition_text(rs.getString("definition_text"));
	            res.setTerm(rs.getString("term_name"));
	            res.setCreatedBy(rs.getString("username"));
	            res.setCreatedDate(rs.getDate("createdDate").toLocalDate());
	            res.setUpdatedAt(rs.getDate("updatedAt").toLocalDate());
	            res.setExample(rs.getString("example"));
	            res.setLikeCount(rs.getInt("like_count")-1);
	            resList.add(res);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }

	    return resList;
	}

	public int getDefinitionCountForCurrentUser(String currentUserId) {
	    int definitionCount = 0;
	    String sql = "SELECT COUNT(*) AS count FROM definition WHERE user_id = ?";

	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, currentUserId);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            definitionCount = rs.getInt("count");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return definitionCount;
	}
	
	public int getDefId(DefandTermRequestDTO req) {
		int defId = 0;
		String sql = "select id from definition where term_id=? and user_id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, req.getTermId());
			ps.setString(2, req.getUserId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				defId = rs.getInt("id");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return defId;
	}
	
	public ArrayList<DefandTermResponseDTO> searchDefinitionsByTerm(String searchTerm) {
	    ArrayList<DefandTermResponseDTO> resList = new ArrayList<>();


	    String sql = "SELECT d.id, d.definition_text, d.example, t.term_name, u.username, t.createdDate, t.updatedAt, COUNT(v.definitionId) AS like_count "
	            + "FROM definition d "
	            + "JOIN term t ON d.term_id = t.id "
	            + "JOIN user u ON d.user_id = u.id "
	            + "LEFT JOIN vote v ON d.id = v.definitionId "
	            + "WHERE t.term_name LIKE ? "
	            + "GROUP BY d.id, d.definition_text, d.example, t.term_name, u.username, t.createdDate, t.updatedAt";

	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setString(1, searchTerm + "%");
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            DefandTermResponseDTO res = new DefandTermResponseDTO();

	            res.setDefId(rs.getInt("id"));
	            res.setDefinition_text(rs.getString("definition_text"));
	            res.setTerm(rs.getString("term_name"));
	            res.setCreatedBy(rs.getString("username"));
	            res.setCreatedDate(rs.getDate("createdDate").toLocalDate());
	            res.setUpdatedAt(rs.getDate("updatedAt").toLocalDate());
	            res.setExample(rs.getString("example"));
	            res.setLikeCount(rs.getInt("like_count")-1);
	            resList.add(res);
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }

	    return resList;
	}
	public void deleteDefinitionById(int definitionId) {
	    String sql = "DELETE FROM definition WHERE id = ?";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, definitionId);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public ArrayList<DefandTermResponseDTO> getDefsByUser(String createdBy){
		
		ArrayList<DefandTermResponseDTO> defsByUser = new ArrayList<>();
		String sql = "SELECT d.id, d.definition_text, d.example, t.term_name, u.username, "
				+ "t.createdDate, t.updatedAt, "
				+ "COUNT(v.definitionId) AS like_count "
	            + "FROM definition d "
	            + "JOIN term t ON d.term_id = t.id "
	            + "JOIN user u ON d.user_id = u.id "
	            + "LEFT JOIN vote v ON d.id = v.definitionId "
	            + "WHERE d.createdBy = ? "
	            + "GROUP BY d.id, d.definition_text, d.example, "
	            + "t.term_name, u.username, t.createdDate, t.updatedAt";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, createdBy);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				DefandTermResponseDTO res = new DefandTermResponseDTO();

	            res.setDefId(rs.getInt("id"));
	            res.setDefinition_text(rs.getString("definition_text"));
	            res.setTerm(rs.getString("term_name"));
	            res.setCreatedBy(rs.getString("username"));
	            res.setCreatedDate(rs.getDate("createdDate").toLocalDate());
	            res.setUpdatedAt(rs.getDate("updatedAt").toLocalDate());
	            res.setExample(rs.getString("example"));
	            res.setLikeCount(rs.getInt("like_count")-1);
	            defsByUser.add(res);
			}
			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return defsByUser;
	}

	
}
