package spharos.client.service.application;

import spharos.client.service.vo.response.*;

public interface UserService {

    // 최근 받은 서비스 상세 내용 조회
    UserRecentServiceResponse getUserRecentService(Long serviceId);
    // 리뷰의 서비스명과 기사명 조회
    ServiceDetailForReviewResponse getServiceDetailForReview(Long serviceId, Long workerId);
    // 찜리스트의 서비스명과 로고 조회
    ServiceDetailForBookmarkResponse getServiceDetailForBookmark(Long serviceId);
    // 서비스내역 업체와 기사명 조회
    ServiceDetailForServiceHistoryListResponse getServiceDetailForServiceHistoryList(Long serviceId, Long workerId);
    // 서비스 상세 내역의 업체정보 조회
    ServiceDetailForServiceHistoryResponse getServiceDetailForServiceHistory(Long serviceId, Long workerId);
}
