package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.EmailTemplateDao;
import ua.kpi.nc.persistence.model.EmailTemplate;
import ua.kpi.nc.persistence.model.NotificationType;
import ua.kpi.nc.persistence.model.ScheduleTimePoint;
import ua.kpi.nc.persistence.model.User;
import ua.kpi.nc.service.EmailTemplateService;
import ua.kpi.nc.service.ScheduleTimePointService;
import ua.kpi.nc.service.ServiceFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailTemplateServiceImpl implements EmailTemplateService {

	private EmailTemplateDao emailTemplateDao;
	private ScheduleTimePointService scheduleTimePointService = ServiceFactory.getScheduleTimePointService();

	public EmailTemplateServiceImpl(EmailTemplateDao emailTemplateDao) {
		this.emailTemplateDao = emailTemplateDao;
	}

	@Override
	public EmailTemplate getById(Long id) {
		return emailTemplateDao.getById(id);
	}

	@Override
	public EmailTemplate getByTitle(String title) {
		return emailTemplateDao.getByTitle(title);
	}

	@Override
	public EmailTemplate getByNotificationType(NotificationType notificationType) {
		return emailTemplateDao.getByNotificationType(notificationType);
	}

	@Override
	public int updateEmailTemplate(EmailTemplate emailTemplate) {
		return emailTemplateDao.updateEmailTemplate(emailTemplate);
	}

	@Override
	public int deleteEmailTemplate(EmailTemplate emailTemplate) {
		return emailTemplateDao.deleteEmailTemplate(emailTemplate);
	}

	@Override
	public String showTemplateParams(String inputText, User user) {
		List<ScheduleTimePoint> finalTimePoints = scheduleTimePointService.getFinalTimePointByUserId(user.getId());
		StringBuilder finalTimePoint = new StringBuilder();
		for (ScheduleTimePoint s: finalTimePoints) {
			finalTimePoint.append(s);
		}
		Map<String, String> varMap = new HashMap<>();
		varMap.put("firstName", user.getFirstName());
		varMap.put("secondName", user.getSecondName());
		varMap.put("lastName", user.getLastName());
		varMap.put("email", user.getEmail());
		varMap.put("id", String.valueOf(user.getId()));
		varMap.put("password", user.getPassword());
		varMap.put("userInterviewTime", String.valueOf(finalTimePoint));
		for (Map.Entry<String, String> entry : varMap.entrySet()) {
			inputText = inputText.replace('%' + entry.getKey() + '%', entry.getValue());
		}
		return inputText;
	}
}
