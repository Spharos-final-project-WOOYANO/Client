package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.exception.CustomException;
import spharos.client.service.domain.services.Services;
import spharos.client.service.infrastructure.ServicesRepository;
import spharos.client.service.vo.response.UserRecentServiceResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final ServicesRepository servicesRepository;

    // 최근 받은 서비스 상세 내용 조회
    @Override
    public UserRecentServiceResponse getUserRecentService(Long serviceId) {

        // 서비스 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        return UserRecentServiceResponse.builder()
                .logoUrl(services.getLogoUrl())
                .name(services.getName())
                .build();
    }


}
