package app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPage {

    @GetMapping("/home")
    public String getMainPage(){
        //select min max a
       // model.addAttribute("name",att)

        return "mainPage2";
    }
    @GetMapping("/home2")
    public String Page(){
        //select min max a
        // model.addAttribute("name",att)

        return "pageWithGraphics";
    }
}
