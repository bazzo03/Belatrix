package com.belatrix.legal.mailintegrator.facade;

import com.belatrix.legal.mailintegrator.constant.EIssueProcess;
import com.belatrix.legal.mailintegrator.dto.GeneralIssueDTO;

public interface IProcessIssueService {

	void processIssueJira(GeneralIssueDTO issue, EIssueProcess process);

	void processIssueTrello(GeneralIssueDTO issue, EIssueProcess process);

	void processIssueSalesForce(GeneralIssueDTO issue, EIssueProcess process);
}
