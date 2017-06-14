package com.flexink.config.web.security.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.flexink.security.SessionUserDetail;

import lombok.extern.slf4j.Slf4j;

/**************************************************************
 * @FileName	: UserDetailsHelper.java
 * @Project		: fxBoot
 * @Package_Name: com.flexink.config.web.security.user
 * @Date		: 2017. 3. 23. 
 * @작성자		: KIMSEOKHOON
 * @변경이력		:
 * @프로그램 설명 	: Security 인증정보 Helper
 **************************************************************/
@Slf4j
public class UserDetailsHelper {

	public static SessionUserDetail getLoginUserDetails() {
		Object obj = getAuthenticatedUser();
		if(obj == null) {
			return null;
		} else if (obj instanceof SessionUserDetail) {
			return (SessionUserDetail) obj;
		} else {
			return null;
		}
	}
    /********************************************************************
     * @메소드명	: getAuthenticatedUser
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 인증된 사용자객체를 VO형식으로 가져온다.
     * @return	: 사용자 ValueObject
     ********************************************************************/
    public static Object getAuthenticatedUser() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
        	log.debug("## authentication object is null!!");
            return null;
        }

        if (authentication.getPrincipal() instanceof SessionUserDetail) {
        	SessionUserDetail details = (SessionUserDetail) authentication.getPrincipal();
	        return details;
        } else {
        	log.debug("## LoginUserDetailsHelper.getAuthenticatedUser : AuthenticatedUser is Not SessionUser");
        	return authentication.getPrincipal();
        }
    }
    
    /********************************************************************
     * @메소드명	: getAuthorities
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 인증된 사용자의 권한 정보 반환
     * @return	: 사용자 권한정보 목록
     * 예) [ROLE_ADMIN, ROLE_USER,
     * ROLE_A, ROLE_B, ROLE_RESTRICTED,
     * IS_AUTHENTICATED_FULLY,
     * IS_AUTHENTICATED_REMEMBERED,
     * IS_AUTHENTICATED_ANONYMOUSLY]
     ********************************************************************/
    public static List<String> getAuthorities() {
        List<String> listAuth = new ArrayList<String>();

        Authentication authentication = getAuthentication();

        if (authentication == null) {
        	log.debug("## authentication object is null!!");
            return null;
        }

        Iterator<? extends GrantedAuthority> iter = authentication.getAuthorities().iterator();

        while(iter.hasNext()) {
        	GrantedAuthority auth = iter.next();
        	listAuth.add(auth.getAuthority());

        	log.debug("## LoginUserDetailsHelper.getAuthorities : Authority is {}", auth.getAuthority());
        }

        return listAuth;
    }
    
    /********************************************************************
     * @메소드명	: containsAuthority
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 입력한 권한을 가지고 있는지 확인
     ********************************************************************/
    public static boolean containsAuthority(String ...roles) {
        List<String> listAuth = getAuthorities();
 
        for(String role : roles) {
        	if(listAuth.contains(role)) {
        		return true;
        	}
        }
        return false;
    }
    
    /********************************************************************
     * @메소드명	: getRoleType
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 최상위 Role 타입 반환
     ********************************************************************/
    /*public static String getRoleType() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
        	log.debug("## authentication object is null!!");
            return null;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        Object[] arr = authorities.toArray();
        GrantedAuthority auth = (GrantedAuthority) arr[0];
        
		return auth.getAuthority();
    }*/

    /********************************************************************
     * @메소드명	: isAuthenticated
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 사용자 인증 여부 (로그인 여부)
     ********************************************************************/
    public static Boolean isAuthenticated() {
        Authentication authentication = getAuthentication();

        if (authentication == null) {
        	log.debug("## authentication object is null!!");
            return Boolean.FALSE;
        }

        String username = authentication.getName();
        if (username.equals("anonymousUser")) {
        	log.debug("## username is {}", username);
            return Boolean.FALSE;
        }

        Object principal = authentication.getPrincipal();

        return (Boolean.valueOf(principal != null));
    }

    /********************************************************************
     * @메소드명	: getAuthentication
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: Authentication 객체 조회
     * @return Authentication
     ********************************************************************/
    private static Authentication getAuthentication() {
    	SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }
    
    /********************************************************************
     * @메소드명	: getEncodedPassword
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 패스워드 BCrypt 인코딩
     * @param Original Password
     * @return Encoded Password
     ********************************************************************/
    public static String getEncodedPassword(String password) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashed = encoder.encode(password);

		return hashed;
    }
}
