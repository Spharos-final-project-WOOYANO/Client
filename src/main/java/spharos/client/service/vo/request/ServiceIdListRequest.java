package spharos.client.service.vo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@NoArgsConstructor
@Getter
public class ServiceIdListRequest{

    private LocalDate date;
    private List<Long> serviceId;

    public ServiceIdListRequest(List<Long> serviceId, LocalDate date) {
        this.serviceId = serviceId;
        this.date = date;
    }
    public ServiceIdListRequest(List<Long> serviceId) {
        this.serviceId = serviceId;
    }
    public ServiceIdListRequest(LocalDate date) {
        this.date = date;
    }
}
