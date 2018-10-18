package com.nicolas.sasapi.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(generator = "LOCATION_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "location_sequence_name", name = "LOCATION_SEQ_GEN", initialValue = 4)

    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_favorite",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "favorite_id", referencedColumnName = "id"))
    private List<FavoriteMovie> favorites = new ArrayList<>();

    public void addFavorite(FavoriteMovie favoriteMovie) {
        favorites.add(favoriteMovie);
    }

    @Override
    public String toString() {
        return id + "|" + name;
    }
}
