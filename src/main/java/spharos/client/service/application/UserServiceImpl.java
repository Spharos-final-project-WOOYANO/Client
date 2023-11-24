package spharos.client.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spharos.client.global.common.response.ResponseCode;
import spharos.client.global.exception.CustomException;
import spharos.client.service.domain.services.Services;
import spharos.client.service.infrastructure.ServicesRepository;
import spharos.client.worker.domain.Worker;
import spharos.client.worker.infrastructure.WorkerRepository;
import spharos.client.service.vo.response.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final ServicesRepository servicesRepository;
    private final WorkerRepository workerRepository;

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

    // 리뷰의 서비스명과 기사명 조회
    @Override
    public ServiceDetailForReviewResponse getServiceDetailForReview(Long serviceId, Long workerId) {

        // 서비스정보 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 기사정보 조회
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_WORKER));

        return ServiceDetailForReviewResponse.builder()
                .serviceName(services.getName())
                .workerName(worker.getName())
                .build();
    }

    // 찜리스트의 서비스명과 로고 조회
    @Override
    public ServiceDetailForBookmarkResponse getServiceDetailForBookmark(Long serviceId) {

        // 서비스정보 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        return ServiceDetailForBookmarkResponse.builder()
                .logoUrl(services.getLogoUrl())
                .name(services.getName())
                .build();
    }

    // 서비스내역 업체와 기사명 조회
    @Override
    public ServiceDetailForServiceHistoryListResponse getServiceDetailForServiceHistoryList(Long serviceId, Long workerId) {

        // 서비스정보 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 기사정보 조회
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_WORKER));

        return ServiceDetailForServiceHistoryListResponse.builder()
                .logoUrl(services.getLogoUrl())
                .serviceName(services.getName())
                .workerName(worker.getName())
                .imgUrl(worker.getImgUrl())
                .build();
    }

    // 서비스 상세 내역의 업체정보 조회
    @Override
    public ServiceDetailForServiceHistoryResponse getServiceDetailForServiceHistory(Long serviceId, Long workerId) {

        // 서비스정보 조회
        Services services = servicesRepository.findById(serviceId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_SERVICE));

        // 기사정보 조회
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new CustomException(ResponseCode.CANNOT_FIND_WORKER));

        return ServiceDetailForServiceHistoryResponse.builder()
                .logoUrl(services.getLogoUrl())
                .serviceName(services.getName())
                .address(services.getAddress())
                .workerName(worker.getName())
                .build();
    }


}
