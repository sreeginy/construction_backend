package com.system.repository;

import com.system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Account findByWorkerName(String name);
}
