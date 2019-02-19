package org.jain.sarthak.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jain.sarthak.messenger.model.Profile;
import org.jain.sarthak.messenger.service.ProfileService;

@Path("profiles")
public class ProfileResource {
	
	ProfileService ps=new ProfileService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON )
	public List<Profile> getProfiles()
	{
		return ps.getAllProfiles();
	}
	
	@GET
	@Path("/{profileName}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Profile getProfile(@PathParam("profileName") String profileName)
	{
		return ps.getProfile(profileName);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile addProfile(Profile profile)
	{
		return ps.addProfile(profile);
	}
	
	@PUT
	@Path("/{profileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile updateProfile(@PathParam("profileName") String profileName, Profile profile)
	{
		profile.setProfileName(profileName);
		return ps.updateProfile(profile);
	}
	
	@DELETE
	@Path("/{profileName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Profile deleteProfile(@PathParam("profileName") String profileName)
	{
		return ps.removeProfile(profileName);
	}

}
