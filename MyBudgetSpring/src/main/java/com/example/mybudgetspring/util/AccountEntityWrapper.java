package com.example.mybudgetspring.util;

import com.example.mybudgetspring.model.entities.AccountEntity;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "Accounts")
public class AccountEntityWrapper {

    private List<AccountEntity> accounts;

    @XmlElement(name = "Account")
    public List<AccountEntity> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountEntity> accounts) {
        this.accounts = accounts;
    }
}