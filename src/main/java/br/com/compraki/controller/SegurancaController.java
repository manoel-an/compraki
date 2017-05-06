package br.com.compraki.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SegurancaController {

    @GetMapping("/login")
    public ModelAndView login(@AuthenticationPrincipal User user) {
        ModelAndView modelAndView = getDefaultObjectsModelAndView(user);
        return modelAndView;
    }

    @GetMapping("/limite")
    public String errorLimite(RedirectAttributes redirectAttributes, String email) {
        redirectAttributes.addFlashAttribute("limite", Boolean.TRUE);
        redirectAttributes.addFlashAttribute("aux", email);
        return "redirect:/login";
    }

    @GetMapping("/email")
    public String errorEmail(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("email", Boolean.TRUE);
        return "redirect:/login";
    }

    @GetMapping("/invalido")
    public String errorEmailInvalido(RedirectAttributes redirectAttributes, String email) {
        redirectAttributes.addFlashAttribute("invalido", Boolean.TRUE);
        redirectAttributes.addFlashAttribute("aux", email);
        return "redirect:/login";
    }

    @GetMapping("/confere")
    public String errorConfirmaEmailInvalido(RedirectAttributes redirectAttributes, String email) {
        redirectAttributes.addFlashAttribute("confere", Boolean.TRUE);
        redirectAttributes.addFlashAttribute("aux", email);
        return "redirect:/login";
    }

    @GetMapping("/error")
    public String errorUser(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("senha", Boolean.TRUE);
        return "redirect:/login";
    }

    @GetMapping("/403")
    public String acessoNegado() {
        return "403";
    }

    private ModelAndView getDefaultObjectsModelAndView(User user) {
        ModelAndView modelAndView = null;
        if (user != null) {
            modelAndView = new ModelAndView("redirect:/");
        } else {
            modelAndView = new ModelAndView("Login");
        }
        return modelAndView;
    }
}
