package dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;
import org.hibernate.validator.Incubating;

import dictionary.dto.VoteRequestDTO;
import dictionary.dto.VoteResponseDTO;

public class VoteDAO {
static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public ArrayList<VoteResponseDTO> showVotes(){
		ArrayList<VoteResponseDTO> list=new ArrayList<VoteResponseDTO>();
		String sql="SELECT    "+
				   "FROM user u" +
				   "JOIN vote v ON       u.id=v.user_id" +
				   "JOIN definition d ON v.definition_id=d.id" +
				   "ORDER BY   " ;
		try {
			
			PreparedStatement check=con.prepareStatement(sql);
			ResultSet rs= check.executeQuery();
			while(rs.next()) {
				VoteResponseDTO vote=new VoteResponseDTO();
				vote.setUsername(rs.getString("username"));
				vote.setDefinition_id(rs.getString("definition_id"));
				list.add(vote);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		return list;
	}
	
	public int definitionTotalVotes(int definition_id) {
		
		int total = 0;
			String sql = "SELECT * FROM vote WHERE definition_id = ?";
			try {
				PreparedStatement check = con.prepareStatement(sql);
				check.setInt(1, definition_id);
				ResultSet rs = check.executeQuery();
				while(rs.next()) {
					total++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return total;		
		}
	
	public int storeLikeVote(VoteRequestDTO req) {
		int result = 0;
		String sql = "insert into vote(vote_type,createdBy,createdAt,definitionId,count) values(?,?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "Like");
			ps.setString(2, req.getCreatedBy());
			ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(4, req.getDefinitionId());
			ps.setInt(5, req.getCount());
			
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public int storeDislikeVote(VoteRequestDTO req) {
		int result = 0;
		String sql = "insert into vote(vote_type,createdBy,createdAt,definitionId,count) values(?,?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, "Dislike");
			ps.setString(2, req.getCreatedBy());
			ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(4, req.getDefinitionId());
			ps.setInt(5, req.getCount());
			
			result = ps.executeUpdate();
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
//	public int updateLikeVote(VoteRequestDTO req) {
//		int result = 0;
//		String sql = "update vote set updatedAt=?,count=?,updatedBy=? where vote_type=? and definitionId=?";
//		
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
//			ps.setInt(2, req.getCount());
//			ps.setString(3, req.getUpdatedBy());
//			ps.setString(4, "Like");
//			ps.setInt(5, req.getDefinitionId());
//			
//			result = ps.executeUpdate();
//			
//		}catch(SQLException e) {
//			System.out.println(e.getMessage());
//		}	
//		return result;
//	}
	
//	public boolean hasUserLikedDef(VoteRequestDTO req) {
//		boolean hasUserLikedDef = false;
//		String sql = "select count(*) as count from vote where user_id=? and vote_type=? and definitionId=?";
//		
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setInt(1, req.getUser_id());
//			ps.setString(2, "Like");
//			ps.setInt(3, req.getDefinitionId());
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				int count = rs.getInt("count");
//				hasUserLikedDef = count>0;
//				return hasUserLikedDef;
//			}
//		}catch(SQLException e) {
//			System.out.println(e.getMessage());
//		}
//		return hasUserLikedDef;		
//	}
//	
//	public boolean hasUserDislikedDef(VoteRequestDTO req) {
//		boolean hasUserDislikedDef = false;
//		String sql = "select count(*) as count from vote where user_id=? and vote_type=? and definitionId=?";
//		
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			ps.setInt(1, req.getUser_id());
//			ps.setString(2, "Dislike");
//			ps.setInt(3, req.getDefinitionId());
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				int count = rs.getInt("count");
//				hasUserDislikedDef = count>0;
//				return hasUserDislikedDef;
//			}
//		}catch(SQLException e) {
//			System.out.println(e.getMessage());
//		}
//		return hasUserDislikedDef;
//		
//		
//	}
	
	public ArrayList<Integer> getUserVotedDefId(int userId,String voteType) {
		ArrayList<Integer> userVotedDefId = new ArrayList<>();
		String sql = "select definitionId from vote where user_id=? and vote_type=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, voteType);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				userVotedDefId.add(rs.getInt("id"));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return userVotedDefId;
	}
	
	public int getLikeCount(int defId) {
		int likeCount =0;
		String sql = "select count(*) as count from vote where definitionId=? and vote_type=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, defId);
			ps.setString(2, "Like");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				likeCount = rs.getInt("count");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return likeCount;
	}
	
	public int getDislikeCount(int defId) {
		int likeCount =0;
		String sql = "select count(*) as count from vote where definitionId=? and vote_type=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, defId);
			ps.setString(2, "Dislike");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				likeCount = rs.getInt("count");
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return likeCount;
	}
	
	public int updateLikeVote(VoteRequestDTO req) {
		int result = 0;
		String sql = "update vote set updatedAt=?,count=?,updatedBy=? where vote_type=? and definitionId=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(2, req.getCount());
			ps.setString(3, req.getUpdatedBy());
			ps.setString(4, "Like");
			ps.setInt(5, req.getDefinitionId());
			
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
		return result;
	}
	
	public int updateDislikeVote(VoteRequestDTO req) {
		int result = 0;
		String sql = "update vote set updatedAt=?,count=?,updatedBy=? where vote_type=? and definitionId=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			ps.setInt(2, req.getCount());
			ps.setString(3, req.getUpdatedBy());
			ps.setString(4, "Dislike");
			ps.setInt(5, req.getDefinitionId());
			
			result = ps.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}	
		return result;
	}
}


