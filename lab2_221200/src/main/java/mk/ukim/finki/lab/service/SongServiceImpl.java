package mk.ukim.finki.lab.service;

import java.util.List;
import org.springframework.stereotype.Service;
import mk.ukim.finki.lab.model.Album;
import mk.ukim.finki.lab.model.Artist;
import mk.ukim.finki.lab.model.Song;
import mk.ukim.finki.lab.repository.jpa.SongRepository;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    // Конструкторот треба само SongRepository ако не користите artistRepository.
    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    // Ова е веќе метод кој е дел од JpaRepository
    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    // Ако овој метод не постои во SongRepository, ќе треба да го дефинирате или да го направите рачно
    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        song.getPerformers().add(artist); // Додаваме артист во листата на изведувачи
        songRepository.save(song); // Чуваме новата верзија на песната
        return artist;
    }

    // Ова е веќе метод кој веројатно не постои, па ќе го замениме со findById
    @Override
    public Song findByTrackId(Long trackId) {
        return songRepository.findById(trackId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid track ID: " + trackId));
    }

    // Методот createSong ќе користи save од JpaRepository
    @Override
    public Song createSong(String title, String genre, Integer releaseYear, List<Artist> performers, Album album) {
        Song newSong = new Song(title, genre, releaseYear, performers, album);
        return songRepository.save(newSong); // користиме save за да ја зачуваме новата песна
    }

    // Методот за бришење на песна ќе користи deleteById од JpaRepository
    @Override
    public boolean removeSong(Long id) {
        if(songRepository.existsById(id)) {
            songRepository.deleteById(id); // Користиме deleteById од JpaRepository
            return true;
        }
        return false;
    }

    // Методот за ажурирање ќе користи save од JpaRepository
    @Override
    public Song updateSong(Long id, String title, String genre, Integer releaseYear, Album album) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song ID: " + id));

        // Ажурираме атрибути на песната
        song.setTitle(title);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);

        return songRepository.save(song); // зачувуваме ажурирана верзија
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
