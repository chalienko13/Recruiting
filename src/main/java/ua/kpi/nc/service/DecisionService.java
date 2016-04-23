package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.Decision;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface DecisionService {
	
	Decision getByMarks(int softMark, int techMark);

    Long insertDecision(Decision decision);

    int updateDecision(Decision decision);

    int deleteDecision(Decision decision);
	
}