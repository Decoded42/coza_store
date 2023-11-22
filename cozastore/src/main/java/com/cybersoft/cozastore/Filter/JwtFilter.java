package com.cybersoft.cozastore.Filter;

import com.cybersoft.cozastore.util.JwtHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtHelper jwtHelper;

    private Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String headerValue = request.getHeader("Authorization");
        System.out.println(headerValue);
        if (headerValue != null && headerValue.startsWith("Bearer ")) {

            String token = headerValue.substring(7);
            String data = jwtHelper.parseToken(token);
            System.out.println("Kiểm tra " + data);

            if(data != null && data.isEmpty()) {
                Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>(){}.getType();
                List<SimpleGrantedAuthority> roles = gson.fromJson(data, listType);


                UsernamePasswordAuthenticationToken user =
                        new UsernamePasswordAuthenticationToken("",roles);

                        SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(user);
            }
            System.out.println("Kiểm tra " + token);

        }else{

            System.out.println("Nội dung header ko hợp lệ");
        }

        filterChain.doFilter(request, response);
    }
}
