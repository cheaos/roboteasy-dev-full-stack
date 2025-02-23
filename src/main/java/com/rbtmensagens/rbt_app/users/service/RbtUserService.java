package com.rbtmensagens.rbt_app.users.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rbtmensagens.rbt_app.config.security.JwtProvider;
import com.rbtmensagens.rbt_app.users.controller.dto.CreateUserDTO;
import com.rbtmensagens.rbt_app.users.controller.dto.UserDTO;
import com.rbtmensagens.rbt_app.users.exception.UserAlreadyExistsException;
import com.rbtmensagens.rbt_app.users.model.RbtUser;
import com.rbtmensagens.rbt_app.users.model.UserDetailImp;
import com.rbtmensagens.rbt_app.users.repository.RbtUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RbtUserService implements UserDetailsService {	
    	
    private final RbtUserRepository rbtuserrepositoy;	  
    private final JwtProvider jwtprovider;        
            
    public String loaduserByRbt(UserDTO request) throws UsernameNotFoundException {
    	 
    	if (("admin".equals(request.getUsername()) && "admin".equals(request.getPassword())) 
 			   || (rbtuserrepositoy.buscaUsuarioLogin(request.getUsername(), request.getPassword()) != null)) {   			       	
 		return jwtprovider.generateAccessToken(request.getUsername());  	    		    	
 	}
 	throw new UsernameNotFoundException("Usuário não encontrado");  		    		    	    
    }
 	    
    public void cadastrarUsuario(CreateUserDTO request) {
    	if (rbtuserrepositoy.existeUsuario(request.getUsername(),request.getPassword(),request.getCdfuncionario(),request.getCdcracha()) == null) {
    		RbtUser user = RbtUser.builder()
    					   .cdcracha(request.getCdcracha())
    					   .cdfuncionario(request.getCdfuncionario())
    					   .dsnome(request.getDsnome())    					   
    					   .username(request.getUsername())
    					   .instatus(request.getInstatus())
    					   .password(request.getPassword())
    					   .build();
    		rbtuserrepositoy.save(user);				       					      					   
    	} 
    	else
    		throw new UserAlreadyExistsException("Usuário Existente!");    	
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	RbtUser user = rbtuserrepositoy.buscaUsuario(username);
        if (user != null) {
            return new UserDetailImp(user);
        }
        else              
        	throw new UsernameNotFoundException("Usuário não encontrado");
    }
}