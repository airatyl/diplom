package app.controller;

import app.data.*;
import app.service.ChangeService;
import app.service.GraphicsService;
import app.service.HistoryService;
import app.service.SaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class ControllerFromUser {

    private final ChangeService changeService;
    private final HistoryService historyService;
    private final GraphicsService graphicsService;
    private final SaveService saveService;

    @PostMapping(path = "/change",
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody String changePage(@RequestBody DataFromUserChange data){
        return changeService.changeMinMax(data);
    }
    @PostMapping(path = "/history",
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody List<DataToUserHistory> operationhistories(@RequestBody DataFromUserFromToDate data){
        return historyService.operationhistories(data);

    }
    @PostMapping(path = "/graphics",
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody List<DataToWebSocketGraphics> graphics(@RequestBody DataFromUserFromToDate data){
        return graphicsService.getGraphics(data);

    }

    @PostMapping(path = "/save",
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody String saveOperationHistory(@RequestBody LoginLogoutData data){
        saveService.saveOperationHistory(data.getOperationName(), data.getLogin());
        return "OK";
    }
}
