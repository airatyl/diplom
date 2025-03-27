package app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPage {

    @GetMapping("/home")
    public String getMainPage(Model model){
        //select min max a
       // model.addAttribute("name",att)

        return "mainPage2";
    }
}
