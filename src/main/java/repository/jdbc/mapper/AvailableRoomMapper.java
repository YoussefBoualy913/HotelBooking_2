package repository.jdbc.mapper;

import dto.AvailableRoomDTO;
import dto.RoomSearchCriteria;
import model.enums.RoomType;
import policy.pricing.PricingContext;
import service.PricingService;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class AvailableRoomMapper {

    public static AvailableRoomDTO map(ResultSet rs, RoomSearchCriteria roomSearchCriteria) throws SQLException {
        PricingContext pricingContext = new PricingContext(LocalDate.now(),
                roomSearchCriteria.getCheckIn(),
                roomSearchCriteria.getCheckOut(),
                rs.getBigDecimal("price"));
        BigDecimal totalprice = new PricingService().calculateTotalPrice(pricingContext);
        return new AvailableRoomDTO(
                rs.getObject("id", UUID.class),
                rs.getString("room_number"),
                RoomType.valueOf(rs.getString("type")),
                rs.getInt("capacity"),
                rs.getBigDecimal("price"),
                totalprice
        );
    }
}