package com.yagrrusso.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yagrrusso.todolist.user.IUserRepository;
import com.yagrrusso.todolist.user.UserModel;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired()
    private IUserRepository userRepository;

    String[] publicPaths = { "/users" };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String servletPath = request.getServletPath();
        boolean isPublic = false;

        for (String item : publicPaths) {
            if (servletPath.startsWith(item)) {
                isPublic = true;
                break;
            }
        }

        if (isPublic) {
            filterChain.doFilter(request, response);
        } else {
            String authorization = request.getHeader("authorization");
            String encodedToken = authorization.substring("Basic".length()).trim();
            byte[] decodedToken = Base64.getDecoder().decode(encodedToken);
            String token = new String(decodedToken);
            String[] credentials = token.split(":");

            String username = credentials[0];
            String password = credentials[1];

            UserModel user = this.userRepository.findByUsername(username);

            if (user == null) {
                response.sendError(401);
            } else {
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                if (result.verified) {
                    request.setAttribute("userId", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        }
    }
}
