package com.packt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import com.packt.model.User;
import java.net.URL;
import com.fasterxml.jackson.core.type.TypeReference;
import sun.reflect.Reflection;

public class Sample{
	public static void main (String [] args){
		try{
			System.out.println("Class: " + Reflection.getCallerClass(1).getName());
			ObjectMapper mapper = new ObjectMapper();
			List<User> users = mapper.readValue(new URL("http://jsonplaceholder.typicode.com/users"), 
				new TypeReference<List<User>>(){});

			for ( User user: users){
				System.out.println("Name: " + user.name + ", Company: " + user.company.name);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}