package br.com.nszandrew.repository;

import br.com.nszandrew.model.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditRepository extends MongoRepository<Audit, Long> {
}
