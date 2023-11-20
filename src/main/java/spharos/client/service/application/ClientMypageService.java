package spharos.client.service.application;

import spharos.client.service.vo.request.ClientModifyServiceRequest;
import spharos.client.service.vo.request.ClientRegisterServiceRequest;
import spharos.client.service.vo.response.ClientServiceResponse;

public interface ClientMypageService {

    // 매장정보 조회
    ClientServiceResponse getClientService(Long serviceId);
    // 매장정보 등록
    Long registerService(String email, ClientRegisterServiceRequest request);
    // 매장정보 수정
    void modifyService(ClientModifyServiceRequest request);

}
