package leets.weeth.domain.account.application.mapper;

import leets.weeth.domain.account.application.dto.AccountDTO;
import leets.weeth.domain.account.application.dto.ReceiptDTO;
import leets.weeth.domain.account.domain.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "receipts", source = "receipts")
    AccountDTO.Response to(Account account, List<ReceiptDTO.Response> receipts);
}
