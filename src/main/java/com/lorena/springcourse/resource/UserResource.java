package com.lorena.springcourse.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.lorena.springcourse.domain.Request;
import com.lorena.springcourse.domain.User;
import com.lorena.springcourse.dto.UserLoginResponsedto;
import com.lorena.springcourse.dto.UserLogindto;
import com.lorena.springcourse.dto.UserSavedto;
import com.lorena.springcourse.dto.UserUpdateRoledto;
import com.lorena.springcourse.dto.UserUpdatedto;
import com.lorena.springcourse.model.PageModel;
import com.lorena.springcourse.model.PageRequestModel;
import com.lorena.springcourse.security.JwtManager;
import com.lorena.springcourse.service.RequestService;
import com.lorena.springcourse.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //Controlador Rest
@RequestMapping(value = "users")
public class UserResource {
    @Autowired private UserService userService;
    @Autowired private RequestService requestService;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtManager jwtManager;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSavedto userdto){
        User user = userdto.transformToUser();
        User createdUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdatedto userdto){
        User user = userdto.transformToUser();
        user.setId(id);
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id){
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<PageModel<User>> listaAll(
        @RequestParam(value = "page",  defaultValue = "0") int page, 
        @RequestParam(value = "size",  defaultValue = "10") int size){
        PageRequestModel pr = new PageRequestModel(page,size);
        PageModel<User> pm = userService.listAllOnLazyMode(pr);

        return ResponseEntity.ok(pm);
    }

    @PostMapping("/login")
	public ResponseEntity<UserLoginResponsedto> login(@RequestBody @Valid UserLogindto user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		Authentication auth = authManager.authenticate(token);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		org.springframework.security.core.userdetails.User userSpring =
				(org.springframework.security.core.userdetails.User) auth.getPrincipal();
		
		String email = userSpring.getUsername();
		List<String> roles = userSpring.getAuthorities()
										.stream()
										.map(authority -> authority.getAuthority())
										.collect(Collectors.toList());
				
		return ResponseEntity.ok(jwtManager.createToken(email, roles));
	}
	

    @GetMapping("/{id}/requests")
    public ResponseEntity<PageModel<Request>> listAllRequestsById(
        @PathVariable(name="id") Long id, 
        @RequestParam(value = "page", defaultValue = "0") int page, 
        @RequestParam(value = "size",  defaultValue = "10") int size){
        
        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<Request> pm = requestService.listAllByOwnerIdOnLazyMode(id, pr);
        return ResponseEntity.ok(pm);    
    }

    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(value = "id") Long id,
        @RequestBody @Valid UserUpdateRoledto userUpdateRole){
        User user = new User();
        user.setId(id);
        user.setRole(userUpdateRole.getRole());
        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }
}
