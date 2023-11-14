package spharos.client.service.application;

import spharos.client.service.vo.response.UserRecentServiceResponse;

public interface UserService {

    // 최근 받은 서비스 상세 내용 조회
    UserRecentServiceResponse getUserRecentService(Long serviceId);

}
