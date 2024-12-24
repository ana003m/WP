package mk.ukim.finki.lab.service.implementation;

import java.util.List;

import mk.ukim.finki.lab.service.SongService;
import org.springframework.stereotype.Service;
import mk.ukim.finki.lab.model.Album;
import mk.ukim.finki.lab.model.Artist;
import mk.ukim.finki.lab.model.Song;
import mk.ukim.finki.lab.repository.jpa.SongRepository;


@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        song.getPerformers().add(artist);
        songRepository.save(song);
        return artist;
    }


    @Override
    public Song findByTrackId(Long trackId) {
        return songRepository.findById(trackId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid track ID: " + trackId));
    }


    @Override
    public Song createSong(String title, String genre, Integer releaseYear, List<Artist> performers, Album album) {
        Song newSong = new Song(title, genre, releaseYear, performers, album);
        return songRepository.save(newSong);
    }


    @Override
    public boolean removeSong(Long id) {
        if(songRepository.existsById(id)) {
            songRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public Song updateSong(Long id, String title, String genre, Integer releaseYear, Album album) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song ID: " + id));
        song.setTitle(title);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);

        return songRepository.save(song);
    }

    @Override
    public int counter(Long id) {

        Song song = songRepository.findById(id).orElse(null);

        if (song != null) {
            int count = song.getCounter() + 1;
            song.setCounter(count);
            songRepository.save(song);
            return count;
        }
        return 0;
    }

    @Override
    public void save(Song song) {
        songRepository.save(song);
    }

    @Override
    public void save(String title, String genre, Integer releaseYear, Album album) {

        Song song = new Song();
        song.setTitle(title);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);

        songRepository.save(song);
    }

    @Override
    public List<Song> findByTitle(String title) {
        return songRepository.findAll().stream().filter(s -> s.getTitle().toLowerCase().contains(title)).toList();
    }


}
