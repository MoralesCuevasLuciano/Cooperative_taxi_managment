package com.pepotec.cooperative_taxi_managment.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "subscribers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class SubscriberEntity extends PersonEntity {

    @ElementCollection
    @CollectionTable(
        name = "subscriber_licence_numbers", 
        joinColumns = @JoinColumn(name = "id_subscriber")
    )
    @Column(name = "licence_number")
    @Builder.Default
    private List<String> licenceNumbers = new ArrayList<>();
}
