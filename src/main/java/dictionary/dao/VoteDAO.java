package dictionary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



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
					"JOIN vote v ON u.id=v.user_id" +
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
}


