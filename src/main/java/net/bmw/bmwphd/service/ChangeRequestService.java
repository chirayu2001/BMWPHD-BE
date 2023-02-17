package net.bmw.bmwphd.service;

import net.bmw.bmwphd.dao.ChangeRequestDao;
import net.bmw.bmwphd.domain.ChangeRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ChangeRequestService {

    private ChangeRequestDao changeRequestDao;
  //  private IdWorker idWorker;

//    public ChangeRequestService(ChangeRequestDao changeRequestDao, IdWorker idWorker) {
//        this.changeRequestDao = changeRequestDao;
//        this.idWorker = idWorker;
//    }

    public ChangeRequestService(ChangeRequestDao changeRequestDao){
        this.changeRequestDao = changeRequestDao;
    }

    public List<ChangeRequest> findAll(){
        return changeRequestDao.findAll();
    }

    public ChangeRequest findById(Integer changeRequestId) {
        return changeRequestDao.findById(changeRequestId).get();
    }

    public void save(ChangeRequest newChangeRequest) {
        //newChangeRequest.setId((int) idWorker.nextId());
        newChangeRequest.setStatus("Pending");
        changeRequestDao.save(newChangeRequest);
    }

    public void update(Integer changeRequestId, ChangeRequest updatedChangeRequest) {
        updatedChangeRequest.setId(changeRequestId);
        changeRequestDao.save(updatedChangeRequest);
    }

    public void deleteById(Integer changeRequestId) {
        changeRequestDao.deleteById(changeRequestId);
    }

    //for spirit director changeRequest management
    public List<ChangeRequest> findAllPendingChangeRequests(){
        return changeRequestDao.findAllByStatus("Pending");
    }

    public List<ChangeRequest> findAllOpenApprovedChangeRequests() {
        return changeRequestDao.findAllByStatus("Approved");
    }

    public List<ChangeRequest> findChangeRequestsByOwnerId(String id) {
        return changeRequestDao.findAllByOwnerId(id);
    }
}
