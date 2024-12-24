package mk.ukim.finki.lab.service;
import mk.ukim.finki.lab.model.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> listArtists();
    Artist findById(Long id);
}
