package io.github.tomaszpro99.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Embeddable //ta klasa jest "osadzalna" do osadzenia w innym miejscu
class Audit {
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    @PrePersist
    void prePersist() {createdOn = LocalDateTime.now();}
    @PreUpdate
    void preMerge() {updatedOn = LocalDateTime.now();}
}
