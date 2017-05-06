package br.com.compraki.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import br.com.compraki.service.NegocioException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        String url = "";
        NegocioException ex = (NegocioException) exception.getCause();
        if (ex != null && ex.getErro() == 1) {
            url = "/compraki/email";
        } else if (ex != null && ex.getErro() == 2) {
            url = "/compraki/invalido?email=" + ex.getAux();
        } else if (ex != null && ex.getErro() == 3) {
            url = "/compraki/limite?email=" + ex.getAux();
        } else if (ex != null && ex.getErro() == 4) {
            url = "/compraki/confere?email=" + ex.getAux();
        } else {
            url = "/compraki/error";
        }
        response.sendRedirect(url);
    }

}