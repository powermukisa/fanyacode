package com.fanyacode.fanyacode.model;

import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware {
  @Override
  public Optional getCurrentAuditor() {
    return Optional.of("FanyaTestUser");
  }
}
