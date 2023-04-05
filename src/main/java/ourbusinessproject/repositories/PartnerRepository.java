package ourbusinessproject.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;
import ourbusinessproject.Partnership;

@Repository
public interface PartnerRepository extends PagingAndSortingRepository<Partnership,Long>, QueryByExampleExecutor<Partnership> {}
