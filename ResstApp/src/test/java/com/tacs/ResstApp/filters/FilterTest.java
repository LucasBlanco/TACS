package com.tacs.ResstApp.filters;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SpringBootTest
@AutoConfigureMockMvc
class FilterTest {

	@Autowired
    private MockMvc mvc;
		
	@BeforeEach
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void userFilter() throws Exception {
		
		mvc.perform(get("/users/1"))
		.andExpect(status().isUnauthorized());
		
		String tokenJson = mvc.perform(post("/login").content("{ \"username\": \"tacs1\", \"password\": \"1234\" }").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		JsonObject obj = new JsonParser().parse(tokenJson).getAsJsonObject();
		String token = obj.get("token").getAsString();
		mvc.perform(get("/users/1").header("Authorization", token))
		.andExpect(status().isOk());
	}
	
	@Test
	public void adminFilter() throws Exception {
		
		mvc.perform(get("/comparison/favourites?id1=1&id2=2"))
		.andExpect(status().isUnauthorized());
	
		String tokenJson = mvc.perform(post("/login").content("{ \"username\": \"tacs1\", \"password\": \"1234\" }").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		JsonObject obj = new JsonParser().parse(tokenJson).getAsJsonObject();
		String token = obj.get("token").getAsString();
		mvc.perform(get("/comparison/favourites?id1=1&id2=2").header("Authorization", token))
		.andExpect(status().isUnauthorized());//No es admin
		
		String tokenJson2 = mvc.perform(post("/login").content("{ \"username\": \"admin1\", \"password\": \"1234\" }").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		JsonObject obj2 = new JsonParser().parse(tokenJson2).getAsJsonObject();
		String token2 = obj2.get("token").getAsString();
		mvc.perform(get("/comparison/favourites?id1=1&id2=2").header("Authorization", token2))
		.andExpect(status().isOk());
	}
}
