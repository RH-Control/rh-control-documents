package br.com.ifpe.rhcontroldocuments.repository;

import br.com.ifpe.rhcontroldocuments.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
