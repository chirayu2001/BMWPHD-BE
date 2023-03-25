package net.bmw.bmwphd.service;

import net.bmw.bmwphd.dao.ChangeRequestDao;
import net.bmw.bmwphd.domain.ChangeRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * <p>
 * The service for handling the functions in {@link net.bmw.bmwphd.controller.ChangeRequestController}.
 * It returns the object returned from {@link ChangeRequestDao} to {@link net.bmw.bmwphd.controller.ChangeRequestController}.
 * </p>
 *
 * @author Chirayu Jain
 */
@Service
@Transactional
public class ChangeRequestService {

    private ChangeRequestDao changeRequestDao;
    //  private IdWorker idWorker;

//    public ChangeRequestService(ChangeRequestDao changeRequestDao, IdWorker idWorker) {
//        this.changeRequestDao = changeRequestDao;
//        this.idWorker = idWorker;
//    }

    public ChangeRequestService(ChangeRequestDao changeRequestDao) {
        this.changeRequestDao = changeRequestDao;
    }

    /**
     * @return the list of all Change Requests.
     */
    public List<ChangeRequest> findAll() {
        return changeRequestDao.findAll();
    }

    /**
     * @param changeRequestId - id of the {@link ChangeRequest} needed to be retrieved.
     * @return {@link ChangeRequest} with the specified id.
     */
    public ChangeRequest findById(Integer changeRequestId) {
        return changeRequestDao.findById(changeRequestId).get();
    }

    /**
     * @param newChangeRequest - the new {@link ChangeRequest} object.
     */
    public void save(ChangeRequest newChangeRequest) {
        //newChangeRequest.setId((int) idWorker.nextId());
        newChangeRequest.setStatus("Pending");
        changeRequestDao.save(newChangeRequest);
    }

    /**
     * @param changeRequestId      - id of the {@link ChangeRequest} needed to be updated.
     * @param updatedChangeRequest - the new {@link ChangeRequest} object.
     */
    public void update(Integer changeRequestId, ChangeRequest updatedChangeRequest) {
        updatedChangeRequest.setId(changeRequestId);
        changeRequestDao.save(updatedChangeRequest);
    }

    /**
     * @param changeRequestId - id of the {@link ChangeRequest} needed to be deleted.
     */
    public void deleteById(Integer changeRequestId) {
        changeRequestDao.deleteById(changeRequestId);
    }

    /**
     * @return all pending Change Requests
     */
    public List<ChangeRequest> findAllPendingChangeRequests() {
        return changeRequestDao.findAllByStatus("Pending");
    }

    /**
     * @return all Open and Approved Change Requests
     */
    public List<ChangeRequest> findAllOpenApprovedChangeRequests() {
        return changeRequestDao.findAllByStatus("Approved");
    }

    /**
     * @param id - id of the owner of the change requests.
     * @return all Change Requests with owner with specified ownerId.
     */
    public List<ChangeRequest> findChangeRequestsByOwnerId(Integer id) {
        return changeRequestDao.findAllByOwnerId(id);
    }
}
