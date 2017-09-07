package com.xmen.mutants.service;

import com.xmen.mutants.dao.RequestDao;
import com.xmen.mutants.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.xmen.mutants.controller.RequesterController.HUMANS;
import static com.xmen.mutants.controller.RequesterController.MUTANTS;

@Service
public class RequestService {

    @Autowired
    private RequestDao requestDao;

    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public void addRequest(Request request) {
        requestDao.create(request);
    }

    public Map<String, Double> getMutantAndHumanRequests() {
        Map<String, Double> mutantHumanRequests = new ConcurrentHashMap<>();
        double humans = 0;
        double mutants = 0;
        List<Request> requests = requestDao.findAll();
        for(Request request : requests) {
            if(request.getIsMutant()) {
                mutants++;
            } else {
                humans++;
            }
        }
        mutantHumanRequests.put(HUMANS, humans);
        mutantHumanRequests.put(MUTANTS, mutants);
        return mutantHumanRequests;
    }
}
