package spharos.client.service.application;

import spharos.client.service.dto.ServiceDetailDto;
public interface RetrieveServiceDetailService {

    ServiceDetailDto retrieveServiceDetail(Long serviceId);
}
