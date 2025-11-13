package com.sericulture.masterdata.repository;

import com.sericulture.masterdata.model.entity.SilkExchange;
import com.sericulture.masterdata.model.entity.SilkExchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface SilkExchangeRepository extends PagingAndSortingRepository<SilkExchange, Long> {
    public List<SilkExchange> findBySilkExchangeNameAndSilkExchNameInKannada(String silkExchangeName, String silkExchNameInKannada);

    public List<SilkExchange> findByActiveAndSilkExchangeNameAndSilkExchNameInKannada(boolean a,String silkExchangeName,String silkExchNameInKannada);

    public SilkExchange findBySilkExchangeNameAndActive(String silkExchangeName,boolean isActive);

    public Page<SilkExchange> findByActiveOrderBySilkExchangeNameAsc(boolean isActive, final Pageable pageable);

    public SilkExchange save(SilkExchange silkExchange);

    public SilkExchange findBySilkExchangeIdAndActive(long id, boolean isActive);

    public SilkExchange findBySilkExchangeIdAndActiveIn(@Param("silkExchangeId") long silkExchangeId, @Param("active") Set<Boolean> active);

    public List<SilkExchange> findByActiveOrderBySilkExchangeNameAsc(boolean isActive);


}
