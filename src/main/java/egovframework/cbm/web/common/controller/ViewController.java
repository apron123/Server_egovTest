package egovframework.cbm.web.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import egovframework.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("view")
public class ViewController {
    @RequestMapping(value = "**")
    public ModelAndView pageView(Model model, HttpServletRequest request) {

        String v = request.getRequestURI().substring(request.getContextPath().length());
        v = v.replace("/view/", "");

        ModelAndView mv = new ModelAndView();
        mv.setViewName(v);
        log.info("view name : {}", v);
        log.info("connect IP info >>>>> " + Utils.getClientIP(request));
        return mv;
    }
}
