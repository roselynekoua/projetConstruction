/*package com.gestion.security;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.gestion.objetService.ObjectService;

@Transactional
//@Scope(value="session")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    ObjectService objectService;
	@Autowired
	UserService userService;

    public UserDetails loadUserByUsername(String userName)
            {
    	
    	TOperateur domainUser = objectService.getUser(userName);
    	
    	List listObject = objectService.getObjects("TOperateur");
    	System.out.println("Login taille Opérateur: "+listObject.size());
		
		for (Iterator it = listObject.iterator(); it.hasNext();) {
			TOperateur m = (TOperateur) it.next();
			
			System.out.println("Login taille: "+m.getOpeLogin().trim().length());
			if(m.getOpeLogin().equalsIgnoreCase(userName)){domainUser = m;}
		}
		
    	//TOperateur domainUser = objectService.getUser(userName);
    	if(domainUser!=null){
    	System.out.println("Login de connexion TOperateur : Login "+domainUser.getOpeLogin()+" Nom "+domainUser.getOpeNom());
    	userService.setOperateurs(domainUser);
    	userService.getFonction(domainUser);
    	userService.getMotPasse(domainUser);
    	userService.setDateCons(Calendar.getInstance().getTime());
    	
    	}else{System.out.println("Utilisateur introuvable ");}
       // Roles role = domainUser.getRoles();
    	
    	
    	
    	String role = "ROLE_"+userService.getFonctions().getTTypeFonction().getTyfCod();
    	userService.getAutorisation(role);//ROLE_USER
    	System.out.println("Role= "+role);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(userService.getOperateurs().getOpeLogin(),
        		userService.getMotdepasses().getMdpMotdepasse(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked,
                getAuthorities(userService.getRole(role)));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(String role) {

        List<String> roles = new ArrayList<String>();
        if ("ROLE_ADM".equals(role)) {
            roles.add("ROLE_ADM");
        } 
        else if ("ROLE_USER".equals(role)) {
            roles.add("ROLE_USER");
        }
        else if ("ROLE_CAB".equals(role)) {
            roles.add("ROLE_CAB");
        }
        else if ("ROLE_CPT".equals(role)) {
            roles.add("ROLE_CPT");
        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(
            List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}*/