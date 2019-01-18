package com.auth.service;

import com.auth.entity.OAuthUser;
import com.auth.mapper.OAuthUserMapper;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author: Gu danpeng
 * @date: 2019-1-15
 * @version：1.0
 */
@Service
public class UserManageService implements UserDetailsService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private OAuthUserMapper oAuthUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuthUser user =
                oAuthUserMapper.queryByUserName(username);
//        user.get().setPermissions(this.getPermissions(user.get().getRoles()));
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        UserDetails userDetails = new User(user.getUserName(),
                user.getPassword(),
                authorities);
        return userDetails;
    }

//    public Collection<? extends GrantedAuthority> getAuthorities(UserInfo userInfo) {
//        return this.getGrantedAuthorities(getPermissions(userInfo.getRoles()));
//    }
//
//    private List<String> getPermissions(Collection<Role> roles) {
//        List<String> permissions = new ArrayList<>();
//        List<Permission> collection = new ArrayList<>();
//        for (Role role : roles) {
//            collection.addAll(role.getPermissions());
//        }
//        for (Permission item : collection) {
//            permissions.add(item.getName());
//        }
//        return permissions;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String permission : permissions) {
//            authorities.add(new SimpleGrantedAuthority(permission));
//        }
//        return authorities;
//    }
//
//    public UserDetails registerNewUser(String username, String password) {
//
//        UserInfo user = new UserInfo();
//
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//
//        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER").get()));
//        return userInfoRepository.save(user);
//    }
}
