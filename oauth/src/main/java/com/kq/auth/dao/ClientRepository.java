/*
package com.kq.auth.dao;

import com.kq.auth.DTO.ClientInfo;
import com.kq.auth.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer>{
    */
/**
     * 通过clientID和serret 来派发服务内部的token
     * @param clientID
     * @param
     * @return
     *//*

    public Client findByCode(String clientID);


    @Query(nativeQuery = true,value = "select code from T_GATE_CLIENT client" +
            " INNER JOIN" +
            " T_GATE_CLIENT_SERVICE TGCS ON TGCS.CLIENT_ID = client.id" +
            " where" +
            " TGCS.SERVICE_ID= ?1"

    )
    public List<String> getAllowClientId(String serviceId);
}
*/
