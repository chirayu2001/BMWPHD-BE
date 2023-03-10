package net.bmw.bmwphd.controller;

import net.bmw.bmwphd.domain.ChangeRequest;
import net.bmw.bmwphd.domain.Result;
import net.bmw.bmwphd.domain.StatusCode;
import net.bmw.bmwphd.service.ChangeRequestService;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * The entry point for api calls from the FrontEnd for Change Request Queries.
 * It wraps the object returned from {@link ChangeRequestService} into {@link Result} and returns it to FrontEnd.
 * <br>
 * The base url for this api is: <strong>/changeRequests</strong>
 * </p>
 *
 * @author Chirayu Jain
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/changeRequests")
public class ChangeRequestController {
    private ChangeRequestService service;

    public ChangeRequestController(ChangeRequestService service) {
        this.service = service;
    }

    /**
     * @return the list of all change requests wrapped in Result object.
     */
    @GetMapping
    public Result findAll() {
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAll());
    }

    /**
     * @return the list of all pending change requests wrapped in Result object.
     */
    @GetMapping("/pending")
    public Result findAllPendingChangeRequests() {
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAllPendingChangeRequests());
    }

    /**
     * @return the list of all open and approved change requests wrapped in Result object.
     */
    @GetMapping("/OpenApproved")
    public Result findAllOpenApprovedChangeRequests() {
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findAllOpenApprovedChangeRequests());
    }

    /**
     * @param id - id of the change request being searched
     * @return change request with the specified id. It is wrapped in Result object.
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        return new Result(true, StatusCode.SUCCESS, "Find All Success", service.findById(id));
    }

    /**
     * @param id - id of the owner of change request.
     * @return change request with owner with specified id. It is wrapped in Result object.
     */
    @GetMapping("/owner/{id}")
    public Result findChangeRequestsByOwnerId(@PathVariable Integer id) {
        return new Result(true, StatusCode.SUCCESS, "Find All By OwnerId Success", service.findChangeRequestsByOwnerId(id));
    }

    /**
     * @param changeRequest - new ChangeRequest object that needs to be saved into database
     * @return Result object specifying whether the save was successful or not.
     */
    @PostMapping
    public Result save(@RequestBody ChangeRequest changeRequest) {
        service.save(changeRequest);
        return new Result(true, StatusCode.SUCCESS, "Save Success");
    }

    /**
     * @param id - id of the change request that needs to be updated.
     * @return Result object specifying whether the update was successful or not.
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody ChangeRequest changeRequest, @PathVariable Integer id) {
        service.update(id, changeRequest);
        return new Result(true, StatusCode.SUCCESS, "Update Success");
    }

    /**
     * @param id - id of change request that needs to be deleted.
     * @return Result object specifying whether the deletion was succesful or not.
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        service.deleteById(id);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}
