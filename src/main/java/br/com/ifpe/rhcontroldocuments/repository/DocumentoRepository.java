package br.com.ifpe.rhcontroldocuments.repository;

import br.com.ifpe.rhcontroldocuments.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, String> {
}
