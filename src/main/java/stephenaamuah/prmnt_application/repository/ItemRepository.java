package stephenaamuah.prmnt_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stephenaamuah.prmnt_application.model.Item;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(value = "SELECT i FROM Item i WHERE i.maintenanceDate BETWEEN ?1 AND ?2 ORDER BY i.maintenanceDate ASC")
    List<Item> findUsingMaintenanceDate(LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT i FROM Item i WHERE i.created BETWEEN ?1 AND ?2 ORDER BY i.created ASC")
    List<Item> findUsingDatePurchased(LocalDate startDate, LocalDate endDate);

}
