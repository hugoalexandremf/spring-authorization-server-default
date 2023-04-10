package com.hfernandes.springauthorizationserverdefault.db.repository;

import com.hfernandes.springauthorizationserverdefault.db.model.AuthorizationConsent;
import com.hfernandes.springauthorizationserverdefault.db.model.AuthorizationConsentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository<AuthorizationConsent, AuthorizationConsentId> {

     Optional<AuthorizationConsent> findByAuthorizationConsentIdRegisteredClientIdAndAuthorizationConsentIdPrincipalName(String registeredClientId, String principalName);

     void deleteByAuthorizationConsentIdRegisteredClientIdAndAuthorizationConsentIdPrincipalName(String registeredClientId, String principalName);

}