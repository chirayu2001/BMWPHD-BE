package net.bmw.bmwphd.controller;

import net.bmw.bmwphd.domain.ChangeRequest;
import net.bmw.bmwphd.domain.Result;
import net.bmw.bmwphd.domain.StatusCode;
import net.bmw.bmwphd.service.ChangeRequestService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/changeRequests")
public class ChangeRequestController {
    private ChangeRequestService service;

    public ChangeRequestController(ChangeRequestService service) {
        this.service = service;
    }

    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAll());
    }

    @GetMapping("/pending")
    public Result findAllPendingChangeRequests(){
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAllPendingChangeRequests());
    }

    @GetMapping("/OpenApproved")
    public Result findAllOpenApprovedChangeRequests(){
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAllOpenApprovedChangeRequests());
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findById(id));
    }

    @GetMapping("/owner/{id}")
    public Result findChangeRequestsByOwnerId(@PathVariable Integer id){
        return new Result(true, StatusCode.SUCCESS, "Find All By OwnerId Success", service.findChangeRequestsByOwnerId(id));
    }

    @PostMapping
    public Result save(@RequestBody ChangeRequest changeRequest){
        service.save(changeRequest);
        return new Result(true, StatusCode.SUCCESS, "Save Success");
    }

    @PutMapping("/{id}")
    public Result update(@RequestBody ChangeRequest changeRequest, @PathVariable Integer id){
        service.update(id, changeRequest);
        return new Result(true, StatusCode.SUCCESS, "Update Success");
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        service.deleteById(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}
