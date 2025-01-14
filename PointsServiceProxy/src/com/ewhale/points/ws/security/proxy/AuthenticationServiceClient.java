package com.ewhale.points.ws.security.proxy;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * @author Ayman Yosry
 *
 */
@Path("/Profile")
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthenticationServiceClient
{
	@POST
	@Path("/login")
	public Map<String, Object> login(Map<String, Object> loginData);

	@POST
	@Path("/changePassword")
	public Map<String, Object> changePassword(Map<String, Object> loginData);

	@POST
	@Path("/resetPassword")
	public void resetPassword(String mobile);

	@POST
	@Path("/validateUser")
	public Map<String, Object> validateUser(Map<String, Object> userData); 
	
	@POST
	@Path("/logout")
	public void logout(String token);
}