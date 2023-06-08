package dictionary.dao;

import java.sql.Connection;

import org.springframework.stereotype.Service;

@Service("otpDao")
public class OtpDAO {
	
	static Connection con=null;
	
	static {
		con = MyConnection.getConnection();
	}
	
	public int storeOtp() {
		int result =0;
		return result;
	}
	
}
