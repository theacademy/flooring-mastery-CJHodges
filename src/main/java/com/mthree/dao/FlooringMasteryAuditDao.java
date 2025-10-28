package com.mthree.dao;

public interface FlooringMasteryAuditDao {

    public void writeAuditEntry(String entry) throws FlooringMasteryPersistenceException;

}