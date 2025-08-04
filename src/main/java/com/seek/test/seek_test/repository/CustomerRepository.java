package com.seek.test.seek_test.repository;

import com.seek.test.seek_test.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Busca todos los clientes que no han sido eliminados
     */
    List<Customer> findByIsDeletedFalse();

    /**
     * Busca todos los clientes que no han sido eliminados con paginación
     */
    Page<Customer> findByIsDeletedFalse(Pageable pageable);

    /**
     * Busca un cliente por ID que no haya sido eliminado
     */
    Optional<Customer> findByIdAndIsDeletedFalse(Long id);



    /**
     * Cuenta el total de clientes activos
     */
    long countByIsDeletedFalse();

    /**
     * Calcula el promedio de edad de todos los clientes activos
     */
    @Query("SELECT AVG(c.age) FROM Customer c WHERE c.isDeleted = false")
    Double getAverageAge();

    /**
     * Calcula la desviación estándar de las edades
     */
    @Query("SELECT SQRT(AVG(POWER(c.age - (SELECT AVG(c2.age) FROM Customer c2 WHERE c2.isDeleted = false), 2))) " +
           "FROM Customer c WHERE c.isDeleted = false")
    Double getStandardDeviation();

    /**
     * Obtiene la edad mínima
     */
    @Query("SELECT MIN(c.age) FROM Customer c WHERE c.isDeleted = false")
    Integer getMinAge();

    /**
     * Obtiene la edad máxima
     */
    @Query("SELECT MAX(c.age) FROM Customer c WHERE c.isDeleted = false")
    Integer getMaxAge();

    /**
     * Calcula la mediana de edad usando una subconsulta
     */
    @Query("SELECT c.age FROM Customer c WHERE c.isDeleted = false " +
           "ORDER BY c.age ASC")
    List<Integer> getAgesOrdered();


} 