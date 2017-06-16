package com.belatrix.legal.mailintegrator.facade;

import com.belatrix.legal.mailintegrator.constant.EIssueProcess;
import com.belatrix.legal.mailintegrator.dto.MailDTO;

public interface IProcessIssueService {

	void processIssueJira(MailDTO issue, EIssueProcess process);

	void processIssueTrello(MailDTO issue, EIssueProcess process);

	void processIssueSalesForce(MailDTO issue, EIssueProcess process);
}
