package module6.backend.service;

import java.util.List;
import org.springframework.data.repository.query.Param;


public interface IStatisticService {
    //HoangTND - Statistic Material
    List<String> findAllStatisticMaterial();
    List<String> searchStatisticMaterial(String fromDate, String toDate);


//    KinPBH - Thong ke tai chinh

    Integer displayHuy();

    Integer searchHuy(@Param("month") String month , @Param("year") String year);

    Integer displayTra();

    Integer searchTra(@Param("month") String month , @Param("year") String year);

    Integer displayBan();

    Integer searchBan(@Param("month") String month , @Param("year") String year);

    Integer displayNhap();

    Integer searchNhap(@Param("month") String month , @Param("year") String year);

//    Integer displayLuong();

    // HuyenNTD - Thong ke khach hang tiem nang

    List<String> findAllStatisticCustomer();
    List<String> searchForPotentialCustomers(String fromMonth, String toMonth, String year);
}
