package net.bmw.bmwphd.controller;

import net.bmw.bmwphd.domain.Horse;
import net.bmw.bmwphd.domain.Result;
import net.bmw.bmwphd.domain.StatusCode;
import net.bmw.bmwphd.service.HorseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *     The entry point for api calls from the FrontEnd.
 *     It wraps the object returned from {@link HorseService} into {@link Result} and returns it to FrontEnd.
 *     <br>
 *     The base url for this api is: <strong>/horses</strong>
 * </p>
 * @author Chirayu Jain
 *
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/horses")
public class HorseController {
    private HorseService horseService;

    public HorseController(HorseService horseService) {
        this.horseService = horseService;
    }

    /**
     * @return the list of all horses wrapped in Result object
     */
    @GetMapping
    public Result findAll() {
        List<Horse> all = horseService.findAll();
        Result result = new Result(true, StatusCode.SUCCESS, "Find All Success", all);
        return result;
    }

    /**
     * @param searchMap - key-value pairs of criteria to look for
     * @return the list of all horses that match the criteria specified in searchMap. It is wrapped in Result object
     */
    @PostMapping(value = "/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.SUCCESS, "Query Successful", horseService.findSearch(searchMap));
    }
}
