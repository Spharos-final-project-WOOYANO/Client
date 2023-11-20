package spharos.client.service.application;

import spharos.client.service.vo.request.ClientModifyServiceRequest;
import spharos.client.service.vo.request.ClientRegisterServiceRequest;
import spharos.client.service.vo.request.ServiceAreaModifyRequest;
import spharos.client.service.vo.request.ServiceAreaRegisterRequest;
import spharos.client.service.vo.response.ClientServiceResponse;
import spharos.client.service.vo.response.ServiceAreaResponse;

public interface ClientMypageService {

    // 매장정보 조회
    ClientServiceResponse getClientService(Long serviceId);
    // 매장정보 등록
    Long registerService(String email, ClientRegisterServiceRequest request);
    // 매장정보 수정
    void modifyService(ClientModifyServiceRequest request);
    // 서비스 가능 지역 등록
    void registerServiceArea(ServiceAreaRegisterRequest request);
    // 서비스 가능 지역 조회
    ServiceAreaResponse getServiceArea(Long serviceId);
    // 서비스 가능 지역 수정
    void modifyService(ServiceAreaModifyRequest request);

}
